package com.example.user.nettydemo;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.Arrays;

/*************************************
 功能：
 *************************************/

public class Test1 {
    static RuntimeSchema<Message> poSchema = RuntimeSchema.createFrom(Message.class);

    private static byte[] decode(Message po){
        return ProtostuffIOUtil.toByteArray(po, poSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }
    public static void main(String[] str){
        Message message=new Message("1","2","3");
        byte[] bytes = decode(message);
        System.out.println(bytes.length+ Arrays.toString(bytes));
        Message newPo = ecode(bytes);
        System.out.println(newPo);
    }
    private static Message ecode(byte[] bytes){
        Message po = poSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, po, poSchema);
        return po;
    }
}
