package com.example.user.nettydemo.bean;


import com.example.user.nettydemo.protosuffTest.EchoRequest;
import com.example.user.nettydemo.protosuffTest.User;

public class HeadDTO  extends EchoRequest {

    public HeadDTO(){}

    public HeadDTO(int flag, String token, int msgId){
        this.flag=flag;
        this.token=token;
        this.msgId=msgId;
    }

    public int flag;//起始标记
    public String token;//停车场/token
    public int msgId;//帧消息id
    public String mac;//校验码
    public int status;
    public int totalLen;//数据长度

    public String userName;
    public String password;

}
