package com.example.buzzme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleFriendResponse {

    @SerializedName("ApplicationFriendAssociationId")
    @Expose
    private Integer applicationFriendAssociationId;
    @SerializedName("MemberId")
    @Expose
    private Integer memberId;
    @SerializedName("FriendId")
    @Expose
    private Integer friendId;
    @SerializedName("RequestBy")
    @Expose
    private Integer requestBy;
    @SerializedName("IsConfirm")
    @Expose
    private Integer isConfirm;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedBy")
    @Expose
    private Object createdBy;
    @SerializedName("ModifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("ModifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("MemberName")
    @Expose
    private String memberName;
    @SerializedName("FriendName")
    @Expose
    private String friendName;
    @SerializedName("RequestByName")
    @Expose
    private String requestByName;
    @SerializedName("EmailId")
    @Expose
    private Object emailId;
    @SerializedName("MobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("Status")
    @Expose
    private Object status;

    public Integer getApplicationFriendAssociationId() {
        return applicationFriendAssociationId;
    }

    public void setApplicationFriendAssociationId(Integer applicationFriendAssociationId) {
        this.applicationFriendAssociationId = applicationFriendAssociationId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(Integer requestBy) {
        this.requestBy = requestBy;
    }

    public Integer getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Integer isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getRequestByName() {
        return requestByName;
    }

    public void setRequestByName(String requestByName) {
        this.requestByName = requestByName;
    }

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

}
