package com.example.user.nettydemo.protosuff;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/*************************************
 功能：
 *************************************/
public class MyClient {
    static final String HOST = System.getProperty("host", "47.97.189.126");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8099"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8080;
//        }
//        new MyServer(port).start();
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            ch.pipeline().addLast(new MyEncoder(MyRequest.class));
                            ch.pipeline().addLast(new MyDecoder(MyRequest.class));
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(HOST, PORT).sync();
            /*future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            future.channel().closeFuture().sync();*/
            MyRequest myRequest = new MyRequest();
            myRequest.setRequestId(12345l);
            myRequest.setRequestMethod("doMethod123");
            myRequest.setRequestTime(new Date());
            future.channel().writeAndFlush(myRequest);
//            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}