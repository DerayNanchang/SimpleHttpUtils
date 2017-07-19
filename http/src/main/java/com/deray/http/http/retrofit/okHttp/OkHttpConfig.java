package com.deray.http.http.retrofit.okHttp;

import android.content.Context;

import com.deray.http.http.DebugUtils.DebugUtil;
import com.deray.http.http.config.HttpConfig;
import com.deray.http.http.interceptor.HttpStandardInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Deray on 2017/5/23.
 */

public class OkHttpConfig {


    private Context context;
    private HttpConfig httpConfig;


    public OkHttpConfig(Context context, HttpConfig httpConfig) {
        this.context = context;
        this.httpConfig = httpConfig;
    }


    public OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                DebugUtil.error(message);
            }
        });

        if (httpConfig == null) {
            httpConfig = new HttpConfig(context);
        }


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)            // 设置日志信息
                .retryOnConnectionFailure(httpConfig.isRetryOnConnectionFailure())         // 是否重连
                .connectTimeout(httpConfig.getConnectTimeOut(), TimeUnit.SECONDS)   // 超时时间
                .cache(httpConfig.getChche());
        OkHttpClient client = interceptorType(builder, httpConfig.getInterceptorType()).build();

        return client;
    }

    public OkHttpClient.Builder interceptorType(OkHttpClient.Builder builder, int type) {
        switch (type) {
            case HttpConfig.NO_INTERCEPTOR:
                return builder;
            case HttpConfig.NETWORK_INTERCEPTOR:
                return builder.addNetworkInterceptor(new HttpStandardInterceptor(context, httpConfig)); //有网缓存
            default:
                return builder.addInterceptor(new HttpStandardInterceptor(context, httpConfig)); //无网缓存
        }
    }
}
