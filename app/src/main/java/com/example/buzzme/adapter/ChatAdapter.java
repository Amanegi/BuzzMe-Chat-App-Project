package com.example.buzzme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzme.R;
import com.example.buzzme.model.SingleMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SingleMessage> messageArrayList;

    public ChatAdapter(ArrayList<SingleMessage> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    public void updateMessageList(ArrayList<SingleMessage> messages) {
        messageArrayList = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == SingleMessage.SENT_MESSAGE) {
            view = inflater.inflate(R.layout.row_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.row_received_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SingleMessage message = messageArrayList.get(position);
        String messageData = message.getMessageBody();
        Date date = message.getMessageTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
        String timeData = timeFormat.format(date);

        if (getItemViewType(position) == SingleMessage.SENT_MESSAGE) {
            ((SentMessageViewHolder) holder).setSentMessageData(messageData, timeData);
        } else {
            ((ReceivedMessageViewHolder) holder).setReceivedMessageData(messageData, timeData);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return messageArrayList.get(position).getMessageType();
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSentMessage, txtSentTimestamp;

        SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSentMessage = itemView.findViewById(R.id.row_sent_message);
            txtSentTimestamp = itemView.findViewById(R.id.row_sent_timestamp);
        }

        void setSentMessageData(String messageData, String timeData) {
            txtSentMessage.setText(messageData);
            txtSentTimestamp.setText(timeData);
        }
    }

    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView txtReceivedMessage, txtReceivedTimestamp;

        ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReceivedMessage = itemView.findViewById(R.id.row_received_message);
            txtReceivedTimestamp = itemView.findViewById(R.id.row_received_timestamp);
        }

        void setReceivedMessageData(String messageData, String timeData) {
            txtReceivedMessage.setText(messageData);
            txtReceivedTimestamp.setText(timeData);
        }
    }

}
