package com.deray.http.http.retrofit;

import android.content.Context;

import com.deray.http.http.config.HttpConfig;
import com.deray.http.http.config.HttpRetrofitConfig;
import com.deray.http.http.gsonUtils.GsonBuilderUtil;
import com.deray.http.http.retrofit.okHttp.OkHttpConfig;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Deray on 2017/5/4.
 */

public class RetrofitConfig {


    private static RetrofitConfig http = new RetrofitConfig();

    private RetrofitConfig() {
    }

    public static RetrofitConfig getInstance() {
        return RetrofitConfig.http;
    }

    public Retrofit getSimpleRetrofit(String baseUrl, Context context) {
        return getRetrofit(baseUrl, context, HttpRetrofitConfig.KEY_SIMPLE, null);
    }

    public Retrofit getNormRetrofit(String baseUrl, Context context, HttpConfig httpConfig) {
        return getRetrofit(baseUrl, context, HttpRetrofitConfig.KEY_NORM, httpConfig);
    }

    public Retrofit getOnlyRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null, HttpRetrofitConfig.KEY_ONLY, null);
    }

    private Retrofit getRetrofit(String baseUrl, Context context, int type, HttpConfig httpConfig) {
        Gson gson = GsonBuilderUtil.create();
        Retrofit.Builder builder = new Retrofit.Builder();
        switch (type) {
            case HttpRetrofitConfig.KEY_ONLY:
                return builder.baseUrl(baseUrl).build();
            case HttpRetrofitConfig.KEY_SIMPLE:
                builder.client(new OkHttpConfig(context, null).getOkHttpClient());
                return getBuild(baseUrl, gson, builder);
            case HttpRetrofitConfig.KEY_NORM:
                builder.client(new OkHttpConfig(context, httpConfig).getOkHttpClient());
                return getBuild(baseUrl, gson, builder);
            default:
                return null;
        }
    }

    private Retrofit getBuild(String baseUrl, Gson gson, Retrofit.Builder builder) {
        return builder.baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
