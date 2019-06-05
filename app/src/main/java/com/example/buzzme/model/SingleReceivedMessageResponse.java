package com.example.buzzme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleReceivedMessageResponse {

    @SerializedName("MessageRecipientId")
    @Expose
    private Integer messageRecipientId;
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("RecipientId")
    @Expose
    private Integer recipientId;
    @SerializedName("SenderId")
    @Expose
    private Integer senderId;
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
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getMessageRecipientId() {
        return messageRecipientId;
    }

    public void setMessageRecipientId(Integer messageRecipientId) {
        this.messageRecipientId = messageRecipientId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

