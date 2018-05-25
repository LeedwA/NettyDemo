package com.example.user.nettydemo;

import com.example.user.nettydemo.bean.BasicDTO;

/*************************************
 功能：
 *************************************/

public class Message  extends BasicDTO{
    public String Head;

    public String title;
    public String id;
    public String content;

    public Message() {
    }

    public Message(String id, String title, String content) {
        this.title = title;
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
