package com.example.buzzme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.utility.SharedPrefHelper;
import com.example.buzzme.databinding.ActivityProfileBinding;
import com.example.buzzme.model.LoginData;
import com.example.buzzme.model.LoginResponse;
import com.example.buzzme.network.API;
import com.example.buzzme.network.ConnectionCheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding mBinding;
    public static final String LOGOUT_BROADCAST = "BuzzMe_ACTION_LOGOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        setSupportActionBar(mBinding.profToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String username = SharedPrefHelper.getPrefUserName(ProfileActivity.this);
        String password = SharedPrefHelper.getPrefPassword(ProfileActivity.this);

        if (ConnectionCheck.getConnectionStatus(this)) {
            Call<LoginResponse> call = API.getApiInterface().sendLoginData(new LoginData(username, password));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getSuccess()) {
                        mBinding.profTextName.setText(loginResponse.getName());
                        mBinding.profTextUsername.setText("Null Object");
                        mBinding.profTextPhone.setText("Null Object");
                        mBinding.profTextEmail.setText(loginResponse.getEmailId());
                        mBinding.profTextAddress.setText("Null Object");
                        mBinding.materialCardView.setVisibility(View.VISIBLE);
                        mBinding.profButtonLogout.setVisibility(View.VISIBLE);
                        mBinding.profLoadingLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Login failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            ConnectionCheck.showNoInternetSnackBar(this, mBinding.profRootLayout);
        }

    }

    public void performLogout(View view) {
        SharedPrefHelper.deleteDataSharedPref(ProfileActivity.this);
        //to finish the dashboard activity
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(LOGOUT_BROADCAST);
        sendBroadcast(broadcastIntent);
        //go to login activity
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}
