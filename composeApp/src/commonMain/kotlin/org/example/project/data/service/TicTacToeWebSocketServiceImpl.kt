package org.example.project.data.service

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.repository.currentGame.DEFAULT_ROOM
import org.example.project.data.repository.currentGame.UPSERT_ERROR
import org.example.project.domain.models.GameRoom
import org.example.project.getPlatform

class TicTacToeWebSocketServiceImpl(): TicTacToeWebSocketService {

    val host = if (getPlatform().name.contains("Android")) "10.0.2.2" else "0.0.0.0"
    private val route = "ws://$host:8080/ticTacToeGameRoute"

    private var session: DefaultClientWebSocketSession? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val realClient: HttpClient = HttpClient {
        install(WebSockets) {
            pingIntervalMillis = 20_000
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private val _messages = MutableSharedFlow<GameRoom>(replay = 1)

    override suspend fun connect() {
        if (session != null) return
        session = realClient.webSocketSession(urlString = route)
        scope.launch {
            try {
                session?.incoming
                    ?.consumeAsFlow()
                    ?.collect { frame ->
                        if (frame is Frame.Text) {
                            try {
                                val game = Json.decodeFromString<GameRoom>(frame.readText())
                                _messages.emit(game)
                                println("WebSocket - Sucesso ao decodificar mensagem: ${game}")
                            } catch (e: Exception) {
                                println("WebSocket - Erro ao decodificar mensagem: ${e.message}")
                            }
                        }
                    }
            } catch (e: Exception) {
                println("WebSocket - Conexão encerrada por error: ${e.message}")
            }
        }
    }

    override suspend fun sendMsg(msg: GameRoom): Long {
        connect()
        val session = session ?: run {
            println("WebSocket - Error, não conectado ao WebSocket")
            return UPSERT_ERROR
        }
        try {
            session.send(Json.encodeToString(msg))
            println("WebSocket - Sucesso ao enviar a mensagem $msg")
            return DEFAULT_ROOM
        } catch (e: Exception) {
            println("WebSocket - Erro ao enviar a mensagem $msg, erro: ${e.message}")
            return UPSERT_ERROR
        }
    }

    override suspend fun disconnect() {
        session?.close(CloseReason(CloseReason.Codes.NORMAL, "Desconectado"))
        session = null
        println("WebSocket - Desconectado do WebSocket - CloseReason.Codes.NORMAL")
    }

    override fun onMsgReceived(): Flow<GameRoom> = _messages.asSharedFlow()
}