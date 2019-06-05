package com.example.buzzme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.model.ActionOnFriendRequestData;
import com.example.buzzme.model.ActionOnFriendRequestResponse;
import com.example.buzzme.model.SingleFriendRequestResponse;
import com.example.buzzme.network.API;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.FriendRequestViewHolder> {
    Context context;
    List<SingleFriendRequestResponse> requestsList;

    public FriendRequestsAdapter(Context context, List<SingleFriendRequestResponse> requestsList) {
        this.context = context;
        this.requestsList = requestsList;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friend_request, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        SingleFriendRequestResponse request = requestsList.get(position);
        holder.textMemberId.setText(request.getFriendId().toString());
        holder.textMemberName.setText(request.getFriendName());
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    class FriendRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textMemberId, textMemberName;
        MaterialButton btnAcceptRequest, btnRejectRequest;

        FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            textMemberId = itemView.findViewById(R.id.row_friend_request_textView_member_id);
            textMemberName = itemView.findViewById(R.id.row_friend_request_textView_member_name);
            btnAcceptRequest = itemView.findViewById(R.id.row_friend_request_accept_button);
            btnRejectRequest = itemView.findViewById(R.id.row_friend_request_reject_button);
        }

        void setListeners() {
            btnAcceptRequest.setOnClickListener(FriendRequestViewHolder.this);
            btnRejectRequest.setOnClickListener(FriendRequestViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            String action = "";
            ActionOnFriendRequestData actionOnFriendRequestData = null;
            final int position = getAdapterPosition();
            String applicationFriendAssociationId = requestsList.get(position).getApplicationFriendAssociationId().toString();

            switch (v.getId()) {
                case R.id.row_friend_request_accept_button:
                    actionOnFriendRequestData = new ActionOnFriendRequestData(applicationFriendAssociationId, "Accept");
                    action = "Accepted";
                    break;
                case R.id.row_friend_request_reject_button:
                    actionOnFriendRequestData = new ActionOnFriendRequestData(applicationFriendAssociationId, "Reject");
                    action = "Rejected";
                    break;
            }

            Call<ActionOnFriendRequestResponse> call = API.getApiInterface().doActionOnFriendRequest(actionOnFriendRequestData);
            final String finalAction = action;
            call.enqueue(new Callback<ActionOnFriendRequestResponse>() {
                @Override
                public void onResponse(Call<ActionOnFriendRequestResponse> call, Response<ActionOnFriendRequestResponse> response) {
                    ActionOnFriendRequestResponse actionOnFriendRequestResponse = response.body();
                    if (actionOnFriendRequestResponse.getSuccess()) {
                        removeItem(position);
                        Toast.makeText(context, "Request " + finalAction, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ActionOnFriendRequestResponse> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        void removeItem(int position) {
            requestsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, requestsList.size());
            //above two lines can be replaced with following
            //notifyDataSetChanged();
        }
    }
}
