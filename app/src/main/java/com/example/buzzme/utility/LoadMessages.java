package com.example.buzzme.utility;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buzzme.model.MyReceivedMessagesData;
import com.example.buzzme.model.MyReceivedMessagesResponse;
import com.example.buzzme.model.MySentMessagesData;
import com.example.buzzme.model.MySentMessagesResponse;
import com.example.buzzme.model.SingleMessage;
import com.example.buzzme.model.SingleReceivedMessageResponse;
import com.example.buzzme.model.SingleSentMessageResponse;
import com.example.buzzme.network.API;
import com.example.buzzme.network.ConnectionCheck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMessages extends ViewModel {
    private String userId, friendId;
    private Context context;
    private List<SingleReceivedMessageResponse> listReceivedMessages;
    private List<SingleSentMessageResponse> listSentMessages;
    private MutableLiveData<ArrayList<SingleMessage>> messagesLiveData;
    private boolean chatActivityNotDestroyed = true, receiveCallExecuted = false, sentCallExecuted = false;

    public void init(Context context, String userId, String friendId) {
        this.context = context;
        this.userId = userId;
        this.friendId = friendId;
    }

    public MutableLiveData<ArrayList<SingleMessage>> getMessages() {
        if (messagesLiveData == null) {
            messagesLiveData = new MutableLiveData<>();
            getMyReceivedMessages();
            getMySentMessages();
        }
        return messagesLiveData;
    }

    private void prepareMessageListLiveData(List<SingleReceivedMessageResponse> listReceived, List<SingleSentMessageResponse> listSent) {
        ArrayList<SingleMessage> messageArrayList = new ArrayList<>();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT +05:30"));

        for (SingleReceivedMessageResponse r : listReceived) {
            try {
                Date timestamp = timeFormat.parse(r.getCreatedDate());
                messageArrayList.add(new SingleMessage(SingleMessage.RECEIVED_MESSAGE, r.getMessage(), timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (SingleSentMessageResponse s : listSent) {
            try {
                Date timestamp = timeFormat.parse(s.getCreatedDate());
                messageArrayList.add(new SingleMessage(SingleMessage.SENT_MESSAGE, s.getMessageBody(), timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //sorting message list
        Collections.sort(messageArrayList);
        messagesLiveData.setValue(messageArrayList);
    }

    private void getMyReceivedMessages() {
        receiveCallExecuted = false;
        Call<MyReceivedMessagesResponse> call = API.getApiInterface().getReceivedMessages(new MyReceivedMessagesData(friendId, userId));
        call.enqueue(new Callback<MyReceivedMessagesResponse>() {
            @Override
            public void onResponse(Call<MyReceivedMessagesResponse> call, Response<MyReceivedMessagesResponse> response) {
                receiveCallExecuted = true;
                MyReceivedMessagesResponse myReceivedMessagesResponse = response.body();
                if (myReceivedMessagesResponse.getSuccess()) {
                    listReceivedMessages = myReceivedMessagesResponse.getResponseData();
                    setMessageValue();
                }
            }

            @Override
            public void onFailure(Call<MyReceivedMessagesResponse> call, Throwable t) {
                Toast.makeText(context, "Received Messages Failed :\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMySentMessages() {
        sentCallExecuted = false;
        Call<MySentMessagesResponse> call = API.getApiInterface().getSentMessages(new MySentMessagesData(userId, friendId));
        call.enqueue(new Callback<MySentMessagesResponse>() {
            @Override
            public void onResponse(Call<MySentMessagesResponse> call, Response<MySentMessagesResponse> response) {
                sentCallExecuted = true;
                MySentMessagesResponse mySentMessagesResponse = response.body();
                if (mySentMessagesResponse.getSuccess()) {
                    listSentMessages = mySentMessagesResponse.getResponseData();
                    setMessageValue();
                }
            }

            @Override
            public void onFailure(Call<MySentMessagesResponse> call, Throwable t) {
                Toast.makeText(context, "Sent Messages Failed :\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMessageValue() {
        if (receiveCallExecuted && sentCallExecuted) {
            prepareMessageListLiveData(listReceivedMessages, listSentMessages);
            fetchMessagesAgain();
        }
    }

    private void fetchMessagesAgain() {
        if (chatActivityNotDestroyed && ConnectionCheck.getConnectionStatus(context)) {
            getMySentMessages();
            getMyReceivedMessages();
        }
    }

    public void setChatActivityDestroyedBoolean() {
        chatActivityNotDestroyed = false;
    }
}
