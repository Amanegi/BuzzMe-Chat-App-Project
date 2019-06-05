package com.example.buzzme.model;

import java.util.Date;

public class SingleMessage implements Comparable<SingleMessage> {
    public static final int SENT_MESSAGE = 0;
    public static final int RECEIVED_MESSAGE = 1;
    private int messageType;
    private String messageBody;
    private Date messageTime;

    public SingleMessage(int messageType, String messageBody, Date messageTime) {
        this.messageType = messageType;
        this.messageBody = messageBody;
        this.messageTime = messageTime;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    @Override
    public int compareTo(SingleMessage o) {
        return getMessageTime().compareTo(o.getMessageTime());
    }
}
