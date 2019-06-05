package com.example.buzzme.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.utility.SharedPrefHelper;
import com.example.buzzme.adapter.FriendListAdapter;
import com.example.buzzme.model.MyFriendListResponse;
import com.example.buzzme.model.SingleFriendResponse;
import com.example.buzzme.network.API;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendsFragment extends Fragment {
    private RecyclerView friendsRecyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        shimmerFrameLayout = view.findViewById(R.id.friend_fragment_shimmer_view_container);
        friendsRecyclerView = view.findViewById(R.id.friends_recycler_view);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    List<SingleFriendResponse> friendList = myFriendListResponse.getResponseData();
                    if (friendList.size() == 0) {
                        Toast.makeText(getContext(), "OOPS! It seems you don't have any friends right now.", Toast.LENGTH_SHORT).show();
                    } else {
                        FriendListAdapter adapter = new FriendListAdapter(friendList);
                        friendsRecyclerView.setAdapter(adapter);
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
