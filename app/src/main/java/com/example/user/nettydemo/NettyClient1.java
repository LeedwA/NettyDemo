package com.example.user.nettydemo;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * netty客户端
 *
 * @author lenovo
 */
public class NettyClient1 {


    private String HOST = "47.97.189.126";
    private final static int PORT = 8099;

    private NioEventLoopGroup workerGroup = new NioEventLoopGroup(4);
    private Channel channel;
    private Bootstrap b;
    private int tryTimes = 0;

    public NettyClient1() {
    }

    public NettyClient1(String host) {
        this.HOST = host;
    }

    /**                         `
     * 重连，连接失败10秒后重试连接
     */
    public void doConnect() {

        if (channel != null && channel.isActive()) {
            return;
        }
        tryTimes++;
        ChannelFuture future = b.connect(HOST, PORT);

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {

                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    channel.closeFuture();
                    workerGroup.shutdownGracefully();
                    System.out.println("Connect to server successfully!");
                } else {
                    System.out.println("Failed to connect to server, try connect after 10s");
                    futureListener.channel().eventLoop().schedule(new Callable() {
                        @Override
                        public Object call() throws Exception {
                            doConnect();
                            return null;
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }

    public void run(final ChannelHandler handler) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //socketChannel.pipeline().addLast(new ProtobufDecoder(MessageBase.getDefaultInstance()));
//                            socketChannel.pipeline().addLast(new ProtobufDecoder(Message.Head.getDefaultInstance()));
                            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            socketChannel.pipeline().addLast(new ProtobufEncoder());
                            //socketChannel.pipeline().addLast(new LogicClientHandler());
                            //socketChannel.pipeline().addLast(new ParkingBizHandler());
                            socketChannel.pipeline().addLast(handler);
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
