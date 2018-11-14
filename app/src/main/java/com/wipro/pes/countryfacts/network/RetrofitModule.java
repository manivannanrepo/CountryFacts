package com.wipro.pes.countryfacts.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitModule {

    private final String baseURL;

    public RetrofitModule(String baseURL) {
        this.baseURL = baseURL;
    }

    public ApiService provideApiService() {
        return getRetrofit().create(ApiService.class);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(getGson())).baseUrl(baseURL).client(getOkHttpClient()).build();
    }

    private Gson getGson() {
        return new GsonBuilder().create();
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }
}
