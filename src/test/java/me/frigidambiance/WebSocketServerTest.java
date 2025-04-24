package me.frigidambiance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketServerTest {

    private static ExecutorService serverExecutor;
    private static final int TEST_PORT = 8765;

    @BeforeAll
    public static void startServer() {
        serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.submit(() -> {
            try {
                WebSocketServer server = new WebSocketServer(TEST_PORT, new EchoFrameProcessor());
                server.run(); // This blocks, so we run it in a thread
            } catch (InterruptedException ignored) {
            }
        });

        // Give the server time to bind
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
        }
    }

    @AfterAll
    public static void stopServer() {
        serverExecutor.shutdownNow();
    }

    @Test
    public void testWebSocketEcho() throws Exception {
        CompletableFuture<String> messageFuture = new CompletableFuture<>();

        WebSocket webSocket = HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .buildAsync(URI.create("ws://localhost:" + TEST_PORT + "/"), new WebSocket.Listener() {
                    @Override
                    public CompletionStage<?> onText(WebSocket ws, CharSequence data, boolean last) {
                        messageFuture.complete(data.toString());
                        return null;
                    }
                }).join();

        webSocket.sendText("Hello", true);

        String received = messageFuture.get(5, TimeUnit.SECONDS);
        assertEquals("Received", received);

        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Test complete").join();
    }
}

