package com.example.buzzme.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String BASE_URL = "http://chatapp.sfsd.sebizfinishingschool.com/API/";

    private static Retrofit retrofitInstance;
    private static ApiInterface apiInterface;

    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static ApiInterface getApiInterface() {
        if (apiInterface == null) {
            apiInterface = getRetrofitInstance().create(ApiInterface.class);
        }
        return apiInterface;
    }
}
