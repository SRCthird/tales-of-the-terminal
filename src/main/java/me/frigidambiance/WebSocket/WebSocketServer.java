package me.frigidambiance.WebSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer {

  private final int port;
  private final WebSocketFrameProcessor frameProcessor;

  public WebSocketServer(int port, WebSocketFrameProcessor frameProcessor) {
    this.port = port;
    this.frameProcessor = frameProcessor;
  }

  public void run() throws InterruptedException {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();

    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(boss, worker)
          .channel(NioServerSocketChannel.class)
          .childHandler(new WebSocketServerInitializer(frameProcessor));

      Channel ch = bootstrap.bind(port).sync().channel();
      System.out.println("WebSocket server started at ws://localhost:" + port);
      ch.closeFuture().sync();
    } finally {
      boss.shutdownGracefully();
      worker.shutdownGracefully();
    }
  }

}
