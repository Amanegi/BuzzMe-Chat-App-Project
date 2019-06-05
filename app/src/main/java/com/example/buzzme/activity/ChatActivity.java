package com.example.buzzme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.buzzme.R;
import com.example.buzzme.adapter.ChatAdapter;
import com.example.buzzme.databinding.ActivityChatBinding;
import com.example.buzzme.model.SingleMessage;
import com.example.buzzme.model.SubmitMessageData;
import com.example.buzzme.model.SubmitMessageResponse;
import com.example.buzzme.network.API;
import com.example.buzzme.utility.LoadMessages;
import com.example.buzzme.utility.SharedPrefHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    public static final String KEY_FRIEND_ID = "key_friend_id";
    private String userId, friendId;
    private LoadMessages loadMessages;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityChatBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        init();
        mBinding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMessages = ViewModelProviders.of(this).get(LoadMessages.class);
        loadMessages.init(this, userId, friendId);

        loadMessages.getMessages().observe(this, new Observer<ArrayList<SingleMessage>>() {
            @Override
            public void onChanged(ArrayList<SingleMessage> messages) {
                if (adapter == null) {
                    adapter = new ChatAdapter(messages);
                    mBinding.chatRecyclerView.setAdapter(adapter);
                    mBinding.chatRecyclerView.scrollToPosition(messages.size() - 1);
                } else {
                    adapter.updateMessageList(messages);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mBinding.chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mBinding.chatNewMessageTextView.getText().toString().trim();
                if (msg.equals("")) {
                    return;
                }
                mBinding.chatNewMessageTextView.setText("");
                Call<SubmitMessageResponse> call = API.getApiInterface().sendMessage(
                        new SubmitMessageData(userId, friendId, "Chat", msg));
                call.enqueue(new Callback<SubmitMessageResponse>() {
                    @Override
                    public void onResponse(Call<SubmitMessageResponse> call, Response<SubmitMessageResponse> response) {
                    }

                    @Override
                    public void onFailure(Call<SubmitMessageResponse> call, Throwable t) {
                        Toast.makeText(ChatActivity.this, "SendMessageError:\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void init() {
        userId = String.valueOf(SharedPrefHelper.getPrefMemberId(this));
        friendId = getIntent().getStringExtra(KEY_FRIEND_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadMessages.setChatActivityDestroyedBoolean();
    }
}
