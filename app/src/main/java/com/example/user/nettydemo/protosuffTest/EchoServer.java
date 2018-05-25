package com.example.user.nettydemo.protosuffTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*************************************
 功能：
 *************************************/

public class EchoServer {
    public void bind(int port) throws Exception {
        // 配置服务端NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加编码器
                            ch.pipeline().addLast(new EchoDecoder(EchoRequest.class));
                            // 添加解码器
                            ch.pipeline().addLast(new EchoEncoder());
                            // 添加业务处理handler
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            // 绑定端口，同步等待成功，该方法是同步阻塞的，绑定成功后返回一个ChannelFuture
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端监听端口关闭，阻塞，等待服务端链路关闭之后main函数才退出
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 6000;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(port);
            } catch (NumberFormatException e) {
                // TODO: handle exception
            }
        }
        new EchoServer().bind(port);
    }
}
