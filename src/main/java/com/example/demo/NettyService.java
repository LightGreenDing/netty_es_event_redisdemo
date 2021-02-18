package com.example.demo;

import com.example.demo.netty.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyService implements CommandLineRunner {
    // 定义服务器的端口号
    static final int PORT = 8007;



    /**
     * 初始化服务,创建Netty服务端
     */
    private void nettyInit() {
        // 创建一个线程组,用来负责接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 创建另一个线程组,用来负责 I/O 的读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建一个 Server 实例(可理解为 Netty 的入门类)
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 将两个线程池设置到 Server 实例
            bootstrap.group(bossGroup, workerGroup)
                    // 设置 Netty 通道的类型为 NioServerSocket(非阻塞 I/O Socket 服务器)
                    .channel(NioServerSocketChannel.class)
                    // 设置建立连接之后的执行器(ServerInitializer 是我创建的一个自定义类)
                    .childHandler(new ServerInitializer());
            // 绑定端口并且进行同步
            ChannelFuture future = bootstrap.bind(PORT).sync();
            // 对关闭通道进行监听
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 资源关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    @Override
    public void run(String... args) throws Exception {

    }
}
