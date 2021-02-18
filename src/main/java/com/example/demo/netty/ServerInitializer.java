package com.example.demo.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 服务端通道初始化
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    // 字符串编码器和解码器
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    // 服务器端连接之后的执行器(自定义的类)
    private static final ServerHandler SERVER_HANDLER = new ServerHandler();


    /**
     * 初始化通道的具体执行方法
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 通道 Channel 设置
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 19 行:设置结尾分隔符【核心代码】（参数1：为消息的最大长度，可自定义；参数2：分隔符[此处以换行符为分隔符]）
//        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
        // 设置(字符串)编码器和解码器
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);
        // 服务器端连接之后的执行器,接收到消息之后的业务处理
        pipeline.addLast(SERVER_HANDLER);
    }
}
