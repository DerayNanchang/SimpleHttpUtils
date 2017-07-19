package com.deray.http.http.retrofit;

import android.content.Context;

import com.deray.http.http.config.HttpConfig;
import com.deray.http.http.gsonUtils.GsonBuilderUtil;
import com.deray.http.http.retrofit.okHttp.OkHttpConfig;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Deray on 2017/5/4.
 */

public class HttpSimpleRetrofit {


    private static HttpSimpleRetrofit http = new HttpSimpleRetrofit();

    private HttpSimpleRetrofit() {
    }

    public static HttpSimpleRetrofit getInstance() {
        return HttpSimpleRetrofit.http;
    }

    public Retrofit getNormRetrofit(String baseUrl, Context context, HttpConfig httpConfig) {

        Gson gson = GsonBuilderUtil.create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpConfig(context, httpConfig).getOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
