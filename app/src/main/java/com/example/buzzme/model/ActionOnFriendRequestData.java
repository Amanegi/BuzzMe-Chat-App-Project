package com.example.buzzme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActionOnFriendRequestData {

    @SerializedName("ApplicationFriendAssociationId")
    @Expose
    private String applicationFriendAssociationId;
    @SerializedName("Status")
    @Expose
    private String status;

    public ActionOnFriendRequestData(String applicationFriendAssociationId, String status) {
        this.applicationFriendAssociationId = applicationFriendAssociationId;
        this.status = status;
    }

    public String getApplicationFriendAssociationId() {
        return applicationFriendAssociationId;
    }

    public void setApplicationFriendAssociationId(String applicationFriendAssociationId) {
        this.applicationFriendAssociationId = applicationFriendAssociationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
