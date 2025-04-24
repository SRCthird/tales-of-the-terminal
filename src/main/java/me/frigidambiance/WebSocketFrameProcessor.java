package me.frigidambiance;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

interface WebSocketFrameProcessor {
  void process(ChannelHandlerContext ctx, WebSocketFrame frame);
}
