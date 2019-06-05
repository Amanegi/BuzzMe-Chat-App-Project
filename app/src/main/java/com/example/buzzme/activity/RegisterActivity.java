package com.example.buzzme.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.buzzme.R;
import com.example.buzzme.databinding.ActivityRegisterBinding;
import com.example.buzzme.model.RegisterData;
import com.example.buzzme.model.RegisterResponse;
import com.example.buzzme.network.API;
import com.example.buzzme.network.ConnectionCheck;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        //remove password texInputLayoutError
        mBinding.regPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.regPasswordTextInputLayout.setHelperText(getResources().getString(R.string.confirm_password_before_registering));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.regRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectionCheck.getConnectionStatus(RegisterActivity.this)) {
                    ConnectionCheck.showNoInternetSnackBar(RegisterActivity.this, mBinding.regRootLayout);
                    return;
                }

                //empty fields check and set error
                boolean noEmptyFields = true;
                String name = mBinding.regName.getText().toString().trim();
                String username = mBinding.regUsername.getText().toString().trim();
                String email = mBinding.regEmail.getText().toString().trim();
                String phone = mBinding.regPhone.getText().toString().trim();
                String address = mBinding.regAddress.getText().toString().trim();
                String password = mBinding.regPassword.getText().toString().trim();

                if (name.equals("")) {
                    mBinding.regName.setError("Field empty");
                    noEmptyFields = false;
                }
                if (username.equals("")) {
                    mBinding.regUsername.setError("Field empty");
                    noEmptyFields = false;
                }
                if (email.equals("")) {
                    mBinding.regEmail.setError("Field empty");
                    noEmptyFields = false;
                }
                if (phone.equals("")) {
                    mBinding.regPhone.setError("Field empty");
                    noEmptyFields = false;
                }
                if (address.equals("")) {
                    mBinding.regAddress.setError("Field empty");
                    noEmptyFields = false;
                }
                if (password.equals("")) {
                    mBinding.regPasswordTextInputLayout.setError("Field empty");
                    noEmptyFields = false;
                }

                if (noEmptyFields) {
                    RegisterData registerData = new RegisterData();
                    registerData.setName(name);
                    registerData.setUserName(username);
                    registerData.setEmailId(email);
                    registerData.setPhone(phone);
                    registerData.setAddress(address);
                    registerData.setPassword(password);

                    Call<RegisterResponse> call = API.getApiInterface().sendRegisterData(registerData);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse.getResponseData() == 1) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error Registering!\nChange username", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Register failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    public void doCancel(View view) {
        finish();
    }
}
