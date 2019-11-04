package com.alirezazoghi.mvvm.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitProvider {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().create();
        OkHttpClient client = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.8/note/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

    public static API getApi() {
        if (retrofit == null) getRetrofit();
        return retrofit.create(API.class);
    }
}
