package com.example.user.nettydemo;

import com.example.user.nettydemo.protosuff.ProtostuffUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PingClientHandler extends SimpleChannelInboundHandler<Message> {
    public static String DEFAULT_ENCRYPT_KEY = "1234";
    private static NettyClient1 nettyClient;

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                nettyClient = new NettyClient1("47.97.189.126");
                // client = new NettyClient("47.97.97.96");
                nettyClient.run(new PingClientHandler());
            }
        });

    }

    /**
     * 处理断开重连
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        nettyClient.doConnect();
    }

    private ThreadLocal<Integer> index = new ThreadLocal<>();

    // 连接成功后，向server发送登录消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        index.set(0);
        ctx.writeAndFlush(createLoginRequest());
    }


    public static Message createLoginRequest() {
        //Integer id=new Random().nextInt(10000)+1000000;
        Message builder = new Message();
        builder.flag = 0xA0;
        builder.token = "10400082";
        builder.msgId = (new Random().nextInt(10000) + 1000000);
        builder.status = 0;
        builder.opCode = OpCodeEnum.LOGIN_REQUEST.getCode();
        builder.ver = 1;
        builder.param = 0x00;
        builder.dataLen = 20;
        builder.userName = "1234";
        builder.password = "9e1f505e74ca9864c9132ba05e0670d85ff56fe5";
//

        return encode(builder, DEFAULT_ENCRYPT_KEY);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        System.out.println((message).toString());

    }

    public static Message encode(Message builder, String key) {
        byte[] bytes = ProtostuffUtil.serializer(builder);
        //RC4 encode
        bytes = RC4EncryptUtil.rc4(key, bytes);

        builder.totalLen = (bytes.length);
        builder.mac = (MacUtils.MACString(bytes));

        return builder;
    }

    public Message decode(Message head, String key) throws InvalidProtocolBufferException {
        byte[] bytes = ProtostuffUtil.serializer(head);
        bytes = RC4EncryptUtil.rc4(key, bytes);

        return ProtostuffUtil.deserializer(bytes, Message.class);
    }
}
