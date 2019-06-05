package com.example.buzzme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzme.R;
import com.example.buzzme.model.SingleFriendResponse;
import com.example.buzzme.utility.ChatRecyclerViewClickListner;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    private List<SingleFriendResponse> friendList;
    private ChatRecyclerViewClickListner mListener;

    public ChatListAdapter(List<SingleFriendResponse> friendList, ChatRecyclerViewClickListner mListener) {
        this.friendList = friendList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_person, parent, false);
        return new ChatListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        SingleFriendResponse singleFriend = friendList.get(position);
        holder.textViewFriendName.setText(singleFriend.getFriendName());
        holder.textViewFriendInitials.setText(singleFriend.getFriendName().substring(0, 1).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ChatRecyclerViewClickListner listener;
        private TextView textViewFriendInitials, textViewFriendName;

        ChatListViewHolder(@NonNull View itemView, ChatRecyclerViewClickListner listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            textViewFriendInitials = itemView.findViewById(R.id.row_chat_person_textView_friend_initial);
            textViewFriendName = itemView.findViewById(R.id.row_chat_person_textView_friend_name);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}

