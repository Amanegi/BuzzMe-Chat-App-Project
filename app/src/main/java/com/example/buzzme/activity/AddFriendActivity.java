package com.example.buzzme.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.adapter.MemberListAdapter;
import com.example.buzzme.databinding.ActivityAddFriendBinding;
import com.example.buzzme.model.MemberListResponse;
import com.example.buzzme.network.API;

public class AddFriendActivity extends AppCompatActivity {
    private ActivityAddFriendBinding mBinding;
    private MemberListAdapter adapter;
    private AnimatedVectorDrawable animatedVectorDrawable;
    private boolean stopLoadingAnimation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend);
        mBinding.addFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(mBinding.addFriendsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        startLoadingAnimation();
        populateMemberList();

    }

    private void populateMemberList() {
        Call<MemberListResponse> call = API.getApiInterface().getApplicationMemberList();
        call.enqueue(new Callback<MemberListResponse>() {
            @Override
            public void onResponse(Call<MemberListResponse> call, Response<MemberListResponse> response) {
                MemberListResponse memberListResponse = response.body();
                if (memberListResponse.getSuccess()) {
                    stopLoadingAnimation();
                    adapter = new MemberListAdapter(AddFriendActivity.this, memberListResponse.getResponseData());
                    mBinding.addFriendsRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MemberListResponse> call, Throwable t) {
                Toast.makeText(AddFriendActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startLoadingAnimation() {
        animatedVectorDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.custom_loading_anim);
        mBinding.addFriendsLoadingLayout.setImageDrawable(animatedVectorDrawable);
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
        }
        new CountDownTimer(1800, 1800) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (!stopLoadingAnimation) {
                    startLoadingAnimation();
                }
            }
        }.start();
    }

    private void stopLoadingAnimation() {
        stopLoadingAnimation = true;
        animatedVectorDrawable.stop();
        mBinding.addFriendsLoadingLayout.setVisibility(View.GONE);
        mBinding.addFriendsLoadingTextView.setVisibility(View.GONE);
        mBinding.addFriendsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_friends_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
