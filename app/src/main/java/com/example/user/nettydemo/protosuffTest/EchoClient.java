package com.example.user.nettydemo.protosuffTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/*************************************
 功能：
 *************************************/

public class EchoClient {
    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 设置TCP连接超时时间
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加解码器
                            ch.pipeline().addLast(new EchoDecoder(EchoResponse.class));
                            // 添加编码器
                            ch.pipeline().addLast(new EchoEncoder());
                            // 添加业务处理handler
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 发起异步连接操作（注意服务端是bind，客户端则需要connect）
            ChannelFuture f = b.connect(host, port).sync();

            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8099;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(port);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
//        new EchoClient().connect(port, "47.97.189.126");

        port=6000;
        new EchoClient().connect(port, "localhost");
    }
}
