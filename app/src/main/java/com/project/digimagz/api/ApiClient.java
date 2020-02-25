package com.project.digimagz.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.digimagz.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static HttpLoggingInterceptor httpLoggingInterceptor;
    private static OkHttpClient okHttpClient;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .baseUrl("https://pn10mobprd.ptpn10.co.id:8598/api/")
                    //.baseUrl("http://digimon.kristomoyo.com/api/")
                    .build();

        }
        return retrofit;
    }
}
