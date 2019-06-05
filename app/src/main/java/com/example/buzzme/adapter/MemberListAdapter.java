package com.example.buzzme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.utility.SharedPrefHelper;
import com.example.buzzme.model.SendFriendRequestData;
import com.example.buzzme.model.SendFriendRequestResponse;
import com.example.buzzme.model.SingleMemberResponse;
import com.example.buzzme.network.API;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder> implements Filterable {
    private Context context;
    private ArrayList<SingleMemberResponse> memberList, fullMemberList;

    public MemberListAdapter(Context context, List<SingleMemberResponse> memberList) {
        this.context = context;
        this.memberList = (ArrayList<SingleMemberResponse>) memberList;
        fullMemberList = new ArrayList<>(memberList);
    }

    @NonNull
    @Override
    public MemberListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member_list, parent, false);
        return new MemberListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListViewHolder holder, int position) {
        final SingleMemberResponse singleMember = memberList.get(position);
        holder.textViewMemberId.setText(singleMember.getMemberId().toString());
        holder.textViewName.setText(singleMember.getName());
        holder.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = String.valueOf(SharedPrefHelper.getPrefMemberId(context));
                Call<SendFriendRequestResponse> call = API.getApiInterface().sendFriendRequest(
                        new SendFriendRequestData(userId, singleMember.getMemberId().toString(), userId, userId, userId));
                call.enqueue(new Callback<SendFriendRequestResponse>() {
                    @Override
                    public void onResponse(Call<SendFriendRequestResponse> call, Response<SendFriendRequestResponse> response) {
                        SendFriendRequestResponse sendFriendRequestResponse = response.body();
                        if (sendFriendRequestResponse.getSuccess()) {
                            Toast.makeText(context, sendFriendRequestResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendFriendRequestResponse> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    class MemberListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMemberId, textViewName;
        MaterialButton sendRequest;

        MemberListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMemberId = itemView.findViewById(R.id.row_member_list_textView_member_id);
            textViewName = itemView.findViewById(R.id.row_member_list_textView_member_name);
            sendRequest = itemView.findViewById(R.id.row_member_list_send_request_button);
        }
    }

    @Override
    public Filter getFilter() {
        return memberFilter;
    }

    private Filter memberFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SingleMemberResponse> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullMemberList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SingleMemberResponse item : fullMemberList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            notifyDataSetChanged();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            memberList.clear();
            if (results.values == null) {
                Toast.makeText(context, "results.values == null", Toast.LENGTH_SHORT).show();
                return;
            }
            memberList.addAll((ArrayList<SingleMemberResponse>) results.values);
            notifyDataSetChanged();
        }
    };
}
