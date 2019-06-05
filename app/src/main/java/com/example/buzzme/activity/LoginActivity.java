package com.example.buzzme.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.utility.SharedPrefHelper;
import com.example.buzzme.databinding.ActivityLoginBinding;
import com.example.buzzme.model.LoginData;
import com.example.buzzme.model.LoginResponse;
import com.example.buzzme.network.API;
import com.example.buzzme.network.ConnectionCheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        AnimatedVectorDrawable animatedVectorDrawable =
                (AnimatedVectorDrawable) getDrawable(R.drawable.anim_launcher_icon_1500);
        mBinding.lgLogoImageView.setImageDrawable(animatedVectorDrawable);
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
        }

        mBinding.lgUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.lgUsernameTextInputLayout.setError(null);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.lgPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.lgPasswordTextInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.lgRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        mBinding.lgLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectionCheck.getConnectionStatus(LoginActivity.this)) {
                    ConnectionCheck.showNoInternetSnackBar(LoginActivity.this, mBinding.lgRootLayout);
                    return;
                }
                //empty fields check and set error
                boolean emptyFields = false;
                mBinding.lgUsernameTextInputLayout.setError(null);
                mBinding.lgPasswordTextInputLayout.setError(null);
                if (TextUtils.isEmpty(mBinding.lgUsername.getText().toString().trim())) {
                    mBinding.lgUsernameTextInputLayout.setError("Field empty");
                    emptyFields = true;
                }
                if (TextUtils.isEmpty(mBinding.lgPassword.getText().toString().trim())) {
                    mBinding.lgPasswordTextInputLayout.setError("Field empty");
                    emptyFields = true;
                }
                if (emptyFields) {
                    return;
                }
                startLoginAction();
            }
        });

    }

    public void startLoginAction() {
        mBinding.lgLoginButton.startAnimation();
        disableViews();
        nextAction();
    }

    //action performed after button shrinks and progress bar appears on it
    //here we only wait. You can add your code
    private void nextAction() {

        final String username = mBinding.lgUsername.getText().toString().trim();
        final String password = mBinding.lgPassword.getText().toString().trim();

        Call<LoginResponse> call = API.getApiInterface().sendLoginData(new LoginData(username, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse == null) {
                    Toast.makeText(LoginActivity.this, "No login data received", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (loginResponse.getSuccess()) {
                    int memberId = loginResponse.getApplicationUserId();
                    SharedPrefHelper.saveDataSharedPreference(LoginActivity.this, username, password, memberId);
                    revealButton();
                    delayedStartNextActivity();
                }
                if (!loginResponse.getSuccess()) {
                    resetButtonAnimation();
                    switch (loginResponse.getErrorMessage()) {
                        case "Invalid username!!":
                            mBinding.lgUsernameTextInputLayout.setError("Invalid username");
                            break;
                        case "Invalid password!!":
                            mBinding.lgPasswordTextInputLayout.setError("Incorrect password");
                            break;
                        default:
                            Toast.makeText(LoginActivity.this,
                                    "Error other than username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                resetButtonAnimation();
                Toast.makeText(LoginActivity.this, "Login failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void disableViews() {
        mBinding.lgUsername.setEnabled(false);
        mBinding.lgPassword.setEnabled(false);
        mBinding.lgRegisterButton.setEnabled(false);
    }

    private void enableViews() {
        mBinding.lgUsername.setEnabled(true);
        mBinding.lgPassword.setEnabled(true);
        mBinding.lgRegisterButton.setEnabled(true);
    }

    private void resetButtonAnimation() {
        enableViews();
        mBinding.lgLoginButton.startMorphRevertAnimation();
    }

    //perform reveal animation
    private void revealButton() {

        mBinding.reveal.setVisibility(VISIBLE);

        int cx = mBinding.reveal.getWidth();
        int cy = mBinding.reveal.getHeight();

        //getting location of the button wrt screen
        int[] buttonLocation = new int[2];
        mBinding.lgLoginButton.getLocationOnScreen(buttonLocation);

        int x = (getFabWidth() / 2) + buttonLocation[0];
        int y = (getFabWidth() / 2) + buttonLocation[1];

        float finalRadius = Math.max(cx, cy) * 1.2f;

        Animator reveal = ViewAnimationUtils
                .createCircularReveal(mBinding.reveal, x, y, 0, finalRadius);

        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

        });

        reveal.start();
    }

    private void delayedStartNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

            }
        }, 100);
    }

    //returns the width in pixels stored in dimens
    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.login_button_height);
    }

}
