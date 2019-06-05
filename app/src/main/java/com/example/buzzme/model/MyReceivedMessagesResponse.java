package com.example.buzzme.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyReceivedMessagesResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("responseData")
    @Expose
    private List<SingleReceivedMessageResponse> responseData = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SingleReceivedMessageResponse> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<SingleReceivedMessageResponse> responseData) {
        this.responseData = responseData;
    }

}
