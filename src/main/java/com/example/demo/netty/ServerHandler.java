package com.example.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 读取到客户端的消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String request) {
        if (!request.isEmpty()) {
            System.out.println("接到客户端的消息:" + request);
        }
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 异常处理，打印异常并关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
