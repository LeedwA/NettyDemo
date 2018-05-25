package com.example.user.nettydemo.protosuffTest;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*************************************
 功能：
 *************************************/

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收到的对象的类型为EchoRequest
        EchoRequest req = (EchoRequest) msg;
        System.out.println(req.getRequestId() + " : " + req.getRequestObj());
        // 创建需要传输的user对象
        User user = new User();
        user.setName("server");
        user.setAge(10);
        // 创建传输的user对象载体EchoRequest对象
        EchoResponse resp = new EchoResponse();
        // 设置responseId
        resp.setResponseId(UUID.randomUUID().toString());
        // 设置需要传输的对象
        resp.setResponseObj(user);
        // 设置需要传输的对象的类型
        resp.setResponseObjClass(resp.getResponseObj().getClass());
        // 调用writeAndFlush将数据发送到socketChannel
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
