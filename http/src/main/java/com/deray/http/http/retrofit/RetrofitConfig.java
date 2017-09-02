package com.deray.http.http.retrofit;

import com.deray.http.http.config.HttpConfig;
import com.deray.http.http.config.HttpConstant;
import com.deray.http.http.fastjsonUtils.FastJsonConverterFactory;
import com.deray.http.http.gsonUtils.GsonBuilderUtil;
import com.deray.http.http.retrofit.okHttp.OkHttpConfig;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Cache;
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

    public Retrofit getSimpleRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null, HttpConstant.KEY_SIMPLE, null, false);
    }

    public Retrofit getNormRetrofit(String baseUrl, Cache cache, HttpConfig httpConfig, boolean isGson) {
        return getRetrofit(baseUrl, cache, HttpConstant.KEY_NORM, httpConfig, isGson);
    }

    public Retrofit getOnlyRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null, HttpConstant.KEY_ONLY, null, false);
    }

    private Retrofit getRetrofit(String baseUrl, Cache cache, int type, HttpConfig httpConfig, boolean isGson) {
        Gson gson = null;
        if (isGson) {
            gson = GsonBuilderUtil.create();
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        switch (type) {
            case HttpConstant.KEY_ONLY:
                return builder.baseUrl(baseUrl).build();
            case HttpConstant.KEY_SIMPLE:
                builder.client(new OkHttpConfig(null).getOkHttpClient());
                return getBuild(baseUrl, gson, builder);
            case HttpConstant.KEY_NORM:
                builder.client(new OkHttpConfig(httpConfig).getOkHttpClient(cache));
                return getBuild(baseUrl, gson, builder);
            default:
                return null;
        }
    }

    private Retrofit getBuild(String baseUrl, Gson gson, Retrofit.Builder builder) {
        return builder.baseUrl(baseUrl)
                .addConverterFactory(gson != null ? GsonConverterFactory.create() : FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
