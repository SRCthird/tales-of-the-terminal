package me.frigidambiance;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

class EchoFrameProcessor implements WebSocketFrameProcessor {

  @Override
  public void process(ChannelHandlerContext ctx, WebSocketFrame frame) {
    if (frame instanceof TextWebSocketFrame) {
      TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
      System.out.println(textFrame.text());
      ctx.channel().writeAndFlush(new TextWebSocketFrame("Received"));
    } else if (frame instanceof CloseWebSocketFrame) {
      ctx.channel().close();
    } else {
      throw new UnsupportedOperationException("Only text frames are supported");
    }
  }
}
