package com.surat.surate_app.Model;

public class Message {
    String to;
    NotifyData data;

    public Message(String to, NotifyData data) {
        this.to = to;
        this.data = data;
    }
}
