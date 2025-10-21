import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.example.project.domain.models.EMPTY_GAME_STATE
import org.example.project.domain.models.GameRoom
import org.example.project.domain.models.Player
import kotlin.test.Test
import kotlin.test.assertEquals

class WebSocketTest {

    @Test
    fun testWebSocket() = runTest {
        val realClient = HttpClient(MockEngine) {
            install(WebSockets) {
                pingIntervalMillis = 20_000
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
        realClient.webSocket(
            urlString = "ws://0.0.0.0:8080/ws"
        ) {
            assertEquals(receiveDeserialized(), "Open")
            assertEquals(receiveDeserialized(), "Open1")
            assertEquals(receiveDeserialized(), "Open2")
        }
    }

    @Test
    fun testSendWebSocket() = runTest {

        val realClient = HttpClient(CIO) {
            install(WebSockets) {
                pingIntervalMillis = 20_000
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
        realClient.webSocket(
            urlString = "ws://0.0.0.0:8080/ticTacToeGameRoute"
        ) {
            sendSerialized(
                GameRoom(
                    gameState = EMPTY_GAME_STATE.copy(
                        id = 9L,
                        gridLength = 5,
                        firstPlayer = Player(id = 9L, name = "Mario")
                    ),
                    createRoom = true,
                    roomId = 8L
                )
            )
        }
    }
}