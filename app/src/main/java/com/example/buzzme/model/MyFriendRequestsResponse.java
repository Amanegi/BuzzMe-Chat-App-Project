package com.example.buzzme.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFriendRequestsResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("responseData")
    @Expose
    private List<SingleFriendRequestResponse> responseData = null;

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

    public List<SingleFriendRequestResponse> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<SingleFriendRequestResponse> responseData) {
        this.responseData = responseData;
    }

}