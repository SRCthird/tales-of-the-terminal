package me.frigidambiance.WebSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

  private final WebSocketFrameProcessor frameProcessor;

  public WebSocketServerInitializer(WebSocketFrameProcessor frameProcessor) {
    this.frameProcessor = frameProcessor;
  }

  @Override
  protected void initChannel(SocketChannel ch) {
    ChannelPipeline p = ch.pipeline();
    p.addLast(new HttpServerCodec());
    p.addLast(new HttpObjectAggregator(65536));
    p.addLast(new ChunkedWriteHandler());
    p.addLast(new WebSocketServerProtocolHandler("/"));
    p.addLast(new WebSocketFrameHandler(frameProcessor));
  }
}
