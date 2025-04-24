package me.frigidambiance.WebSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

  private final WebSocketFrameProcessor frameProcessor;

  public WebSocketFrameHandler(WebSocketFrameProcessor frameProcessor) {
    this.frameProcessor = frameProcessor;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
    frameProcessor.process(ctx, frame);
  }
}
