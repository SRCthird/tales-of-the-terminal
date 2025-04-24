package me.frigidambiance.WebSocket;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class WebSocketServerInitializerTest {

    private SocketChannel channel;
    private ChannelPipeline pipeline;
    private WebSocketFrameProcessor processor;

    @BeforeEach
    void setUp() {
        channel = mock(SocketChannel.class);
        pipeline = mock(ChannelPipeline.class);
        processor = mock(WebSocketFrameProcessor.class);

        when(channel.pipeline()).thenReturn(pipeline);
        // Make pipeline.addLast(...) fluent by default
        when(pipeline.addLast(any())).thenReturn(pipeline);
    }

    @Test
    void testPipelineConfiguration() throws Exception {
        WebSocketServerInitializer initializer = new WebSocketServerInitializer(processor);
        initializer.initChannel(channel);

        verify(pipeline).addLast(any(HttpServerCodec.class));
        verify(pipeline).addLast(any(HttpObjectAggregator.class));
        verify(pipeline).addLast(any(ChunkedWriteHandler.class));
        verify(pipeline).addLast(any(WebSocketServerProtocolHandler.class));
        verify(pipeline).addLast(any(WebSocketFrameHandler.class));
    }
}
