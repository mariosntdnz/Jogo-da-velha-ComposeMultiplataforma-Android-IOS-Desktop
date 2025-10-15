import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class WebSocketTest {

    @Test
    fun testWebSocket() = testApplication {

        val realClient = HttpClient(CIO) {
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
}