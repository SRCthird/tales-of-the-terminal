package me.frigidambiance.WebSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public interface WebSocketFrameProcessor {
  void process(ChannelHandlerContext ctx, WebSocketFrame frame);
}
