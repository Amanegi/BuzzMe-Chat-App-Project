package com.example.buzzme.network;

import com.example.buzzme.model.ActionOnFriendRequestData;
import com.example.buzzme.model.ActionOnFriendRequestResponse;
import com.example.buzzme.model.LoginData;
import com.example.buzzme.model.LoginResponse;
import com.example.buzzme.model.MemberListResponse;
import com.example.buzzme.model.MyFriendListResponse;
import com.example.buzzme.model.MyFriendRequestsResponse;
import com.example.buzzme.model.MyReceivedMessagesData;
import com.example.buzzme.model.MyReceivedMessagesResponse;
import com.example.buzzme.model.MySentMessagesData;
import com.example.buzzme.model.MySentMessagesResponse;
import com.example.buzzme.model.RegisterData;
import com.example.buzzme.model.RegisterResponse;
import com.example.buzzme.model.SendFriendRequestData;
import com.example.buzzme.model.SendFriendRequestResponse;
import com.example.buzzme.model.SubmitMessageData;
import com.example.buzzme.model.SubmitMessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    //login
    @POST("AccountAPI/GetLoginUser")
    Call<LoginResponse> sendLoginData(@Body LoginData loginData);

    //registration
    @POST("AccountAPI/SaveApplicationUser")
    Call<RegisterResponse> sendRegisterData(@Body RegisterData registerData);

    //get application member list
    @GET("ApplicationFriendAPI/GetApplicationMemberList")
    Call<MemberListResponse> getApplicationMemberList();

    //add friend
    @POST("ApplicationFriendAPI/AddFriendRequest")
    Call<SendFriendRequestResponse> sendFriendRequest(@Body SendFriendRequestData sendFriendRequestData);

    //my friend requests
    @GET("ApplicationFriendAPI/MyFriendRequest/{MemberId}")
    Call<MyFriendRequestsResponse> getMyFriendRequests(@Path("MemberId") int memberId);

    //my friend list
    @GET("ApplicationFriendAPI/MyFriendList/{MemberId}")
    Call<MyFriendListResponse> getMyFriendList(@Path("MemberId") int memberId);

    //action on friend request
    @POST("ApplicationFriendAPI/ActionOnFriendRequest")
    Call<ActionOnFriendRequestResponse> doActionOnFriendRequest(@Body ActionOnFriendRequestData actionOnFriendRequestData);

    //submit message
    @POST("MessageAPI/SubmitMessage")
    Call<SubmitMessageResponse> sendMessage(@Body SubmitMessageData submitMessageData);

    //received messages
    @POST("MessageAPI/GetMyReciviedMessage")
    Call<MyReceivedMessagesResponse> getReceivedMessages(@Body MyReceivedMessagesData myReceivedMessagesData);

    //sent messages
    @POST("MessageAPI/GetMySentMessage")
    Call<MySentMessagesResponse> getSentMessages(@Body MySentMessagesData mySentMessagesData);


}
