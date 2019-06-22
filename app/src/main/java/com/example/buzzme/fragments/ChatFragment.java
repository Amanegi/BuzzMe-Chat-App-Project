package com.example.buzzme.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.activity.ChatActivity;
import com.example.buzzme.adapter.ChatListAdapter;
import com.example.buzzme.adapter.FriendListAdapter;
import com.example.buzzme.model.MyFriendListResponse;
import com.example.buzzme.model.SingleFriendResponse;
import com.example.buzzme.network.API;
import com.example.buzzme.utility.ChatRecyclerViewClickListner;
import com.example.buzzme.utility.SharedPrefHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatFragment extends Fragment {
    private RecyclerView chatsRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        shimmerFrameLayout = view.findViewById(R.id.chat_fragment_shimmer_view_container);
        chatsRecyclerView = view.findViewById(R.id.chatFragmentRecyclerView);
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
        populateFriendList();
    }

    private void populateFriendList() {
        Call<MyFriendListResponse> call = API.getApiInterface().getMyFriendList(SharedPrefHelper.getPrefMemberId(getContext()));
        call.enqueue(new Callback<MyFriendListResponse>() {
            @Override
            public void onResponse(Call<MyFriendListResponse> call, Response<MyFriendListResponse> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                MyFriendListResponse myFriendListResponse = response.body();
                if (myFriendListResponse.getSuccess()) {
                    final List<SingleFriendResponse> friendList = myFriendListResponse.getResponseData();
                    if (friendList.size() == 0) {
                        Toast.makeText(getContext(), "OOPS! It seems you don't have any friends right now.", Toast.LENGTH_SHORT).show();
                    } else {
                        ChatRecyclerViewClickListner listener = new ChatRecyclerViewClickListner() {
                            @Override
                            public void onClick(View view, int position) {
                                String friendId = friendList.get(position).getFriendId().toString();
                                String friendName = friendList.get(position).getFriendName();
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.putExtra(ChatActivity.KEY_FRIEND_ID, friendId);
                                intent.putExtra(ChatActivity.KEY_FRIEND_NAME, friendName);
                                startActivity(intent);
                            }
                        };
                        ChatListAdapter adapter = new ChatListAdapter(friendList, listener);
                        chatsRecyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyFriendListResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
