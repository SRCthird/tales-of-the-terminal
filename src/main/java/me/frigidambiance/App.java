package me.frigidambiance;

import me.frigidambiance.WebSocket.WebSocketServer;
import me.frigidambiance.WebSocket.WebSocketFrameProcessor;
import me.frigidambiance.WebSocket.EchoFrameProcessor;

public class App {
  public static void main(String[] args) throws InterruptedException {
    WebSocketFrameProcessor processor = new EchoFrameProcessor();
    new WebSocketServer(8080, processor).run();
  }
}
