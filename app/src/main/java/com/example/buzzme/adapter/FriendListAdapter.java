package com.example.buzzme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzme.R;
import com.example.buzzme.model.SingleFriendResponse;

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private List<SingleFriendResponse> friendList;

    public FriendListAdapter(List<SingleFriendResponse> friendList) {
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friend, parent, false);
        return new FriendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder holder, int position) {
        SingleFriendResponse singleFriend = friendList.get(position);
        holder.textViewFriendId.setText(singleFriend.getFriendId().toString());
        holder.textViewFriendName.setText(singleFriend.getFriendName());
        holder.textViewInitials.setText(singleFriend.getFriendName().substring(0, 1).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class FriendListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFriendId, textViewFriendName, textViewInitials;

        FriendListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFriendId = itemView.findViewById(R.id.row_friend_textView_friend_id);
            textViewFriendName = itemView.findViewById(R.id.row_friend_textView_friend_name);
            textViewInitials = itemView.findViewById(R.id.row_friend_textView_friend_initial);
        }
    }
}
