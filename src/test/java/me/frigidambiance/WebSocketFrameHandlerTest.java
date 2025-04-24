package me.frigidambiance;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class WebSocketFrameHandlerTest {

  private WebSocketFrameProcessor processor;
  private WebSocketFrameHandler handler;
  private ChannelHandlerContext ctx;

  @BeforeEach
  void setup() {
    processor = mock(WebSocketFrameProcessor.class);
    handler = new WebSocketFrameHandler(processor);
    ctx = mock(ChannelHandlerContext.class);
  }

  @Test
  void testDelegatesFrameToProcessor() throws Exception {
    WebSocketFrame frame = new TextWebSocketFrame("test");

    handler.channelRead0(ctx, frame);

    verify(processor).process(ctx, frame);
  }
}
