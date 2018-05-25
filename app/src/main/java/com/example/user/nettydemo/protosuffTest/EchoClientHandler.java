package com.example.user.nettydemo.protosuffTest;

import com.example.user.nettydemo.PingClientHandler;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*************************************
 功能：
 *************************************/

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 创建需要传输的user对象
        User user = new User();
        user.setName("client");
        user.setAge(10);
        // 创建传输的user对象载体EchoRequest对象
        EchoRequest req = new EchoRequest();
        // 设置requestId
        req.setRequestId(UUID.randomUUID().toString());
        // 设置需要传输的对象
        req.setRequestObj(user);
        // 设置需要传输的对象的类型
        req.setRequestObjClass(req.getRequestObj().getClass());
        // 调用writeAndFlush将数据发送到socketChannel
//        ctx.writeAndFlush(req);

        ctx.writeAndFlush(PingClientHandler.createLoginRequest());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收到的对象的类型为EchoResponse
        EchoResponse resp = (EchoResponse) msg;
        System.out.println(resp.getResponseId() + " : " + resp.getResponseObj());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
