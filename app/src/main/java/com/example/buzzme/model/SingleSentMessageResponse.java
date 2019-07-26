package com.example.buzzme.model;

import com.example.buzzme.utility.MsgCrypto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleSentMessageResponse {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("SenderId")
    @Expose
    private Integer senderId;
    @SerializedName("ReceiverId")
    @Expose
    private Object receiverId;
    @SerializedName("Subject")
    @Expose
    private Object subject;
    @SerializedName("MessageBody")
    @Expose
    private String messageBody;
    @SerializedName("ParentMessageId")
    @Expose
    private Object parentMessageId;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Object getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Object receiverId) {
        this.receiverId = receiverId;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return MsgCrypto.decryptMessage(messageBody);
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Object getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Object parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

}

