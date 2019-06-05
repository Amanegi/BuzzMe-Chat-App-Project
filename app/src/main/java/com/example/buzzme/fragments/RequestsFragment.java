package com.example.buzzme.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buzzme.activity.AddFriendActivity;
import com.example.buzzme.R;
import com.example.buzzme.utility.SharedPrefHelper;
import com.example.buzzme.adapter.FriendRequestsAdapter;
import com.example.buzzme.databinding.FragmentRequestsBinding;
import com.example.buzzme.model.MyFriendRequestsResponse;
import com.example.buzzme.model.SingleFriendRequestResponse;
import com.example.buzzme.network.API;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsFragment extends Fragment {
    private FragmentRequestsBinding mBinding;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_requests, container, false);
        shimmerFrameLayout = mBinding.requestsFragmentShimmerViewContainer;
        mBinding.recyclerViewFriendRequest.setLayoutManager(new LinearLayoutManager(getContext()));

        mBinding.buttonAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
        populateFriendRequests();
    }

    private void populateFriendRequests() {
        Call<MyFriendRequestsResponse> call = API.getApiInterface().getMyFriendRequests(SharedPrefHelper.getPrefMemberId(getContext()));
        call.enqueue(new Callback<MyFriendRequestsResponse>() {
            @Override
            public void onResponse(Call<MyFriendRequestsResponse> call, Response<MyFriendRequestsResponse> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                MyFriendRequestsResponse myFriendRequestsResponse = response.body();
                if (myFriendRequestsResponse.getSuccess()) {
                    List<SingleFriendRequestResponse> requestsList = myFriendRequestsResponse.getResponseData();
                    if (requestsList.size() == 0) {
                        mBinding.textViewNoFriendRequests.setVisibility(View.VISIBLE);
                    } else {
                        FriendRequestsAdapter adapter = new FriendRequestsAdapter(getContext(), requestsList);
                        mBinding.recyclerViewFriendRequest.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyFriendRequestsResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
