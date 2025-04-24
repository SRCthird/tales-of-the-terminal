package me.frigidambiance.WebSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EchoFrameProcessorTest {

    private EchoFrameProcessor processor;
    private ChannelHandlerContext ctx;
    private Channel channel;

    @BeforeEach
    void setUp() {
        processor = new EchoFrameProcessor();
        ctx = mock(ChannelHandlerContext.class);
        channel = mock(Channel.class);
        when(ctx.channel()).thenReturn(channel);
    }

    @Test
    void testTextFrameIsEchoedBack() {
        TextWebSocketFrame frame = new TextWebSocketFrame("Hello");

        processor.process(ctx, frame);

        ArgumentCaptor<TextWebSocketFrame> captor = ArgumentCaptor.forClass(TextWebSocketFrame.class);
        verify(channel).writeAndFlush(captor.capture());

        assertEquals("Received", captor.getValue().text());
    }

    @Test
    void testCloseFrameClosesChannel() {
        CloseWebSocketFrame frame = new CloseWebSocketFrame();

        processor.process(ctx, frame);

        verify(channel).close();
    }

    @Test
    void testUnsupportedFrameThrowsException() {
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame();

        assertThrows(UnsupportedOperationException.class, () -> {
            processor.process(ctx, frame);
        });
    }
}

