package com.example.buzzme.model;

import com.example.buzzme.utility.MsgCrypto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitMessageData {

    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("MessageBody")
    @Expose
    private String messageBody;
    @SerializedName("ReceiverId")
    @Expose
    private String receiverId;

    public SubmitMessageData(String senderId, String receiverId, String subject, String messageBody) {
        this.senderId = senderId;
        this.subject = subject;
        this.messageBody = MsgCrypto.encryptMessage(messageBody); //encrypt message
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

}
