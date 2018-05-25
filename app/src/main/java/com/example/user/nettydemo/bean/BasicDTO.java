package com.example.user.nettydemo.bean;


import java.io.Serializable;


/**
 *
 * 通讯格式
 */
public class BasicDTO extends HeadDTO implements Serializable {

    public static final long serialVersionUID = 1L;


    public int opCode;//操作码
    public int ver;//版本号
    public int param;//业务参数
    public int dataLen; //业务数据长度

    public BasicDTO(){
        super();
    }

    public BasicDTO(int flag, String token, int msgId, int opCode, int ver, int param) {
        super(flag,token,msgId);
        this.opCode = opCode;
        this.ver = ver;
        this.param = param;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
