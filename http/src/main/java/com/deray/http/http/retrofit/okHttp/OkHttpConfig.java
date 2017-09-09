package com.deray.http.http.retrofit.okHttp;

import com.deray.http.http.DebugUtils.DebugUtil;
import com.deray.http.http.config.HttpConfig;
import com.deray.http.http.interceptor.HttpStandardInterceptor;
import com.deray.http.http.interceptor.addHeaderInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Deray on 2017/5/23.
 */

public class OkHttpConfig {


    private HttpConfig httpConfig;


    public OkHttpConfig(HttpConfig httpConfig) {
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
            httpConfig = new HttpConfig();
        }

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(httpConfig.isRetryOnConnectionFailure())         // 是否重连
                .connectTimeout(httpConfig.getConnectTimeOut(), TimeUnit.SECONDS)
                .build();   // 超时时间
    }

    public OkHttpClient getOkHttpClient(List<String> header) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                DebugUtil.error(message);
            }
        });

        if (httpConfig == null) {
            httpConfig = new HttpConfig();
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor);// 设置日志信息
        if (header != null) {
            builder = builder.addInterceptor(new addHeaderInterceptor(header));
        }
        return builder.retryOnConnectionFailure(httpConfig.isRetryOnConnectionFailure())         // 是否重连
                .connectTimeout(httpConfig.getConnectTimeOut(), TimeUnit.SECONDS)
                .build();   // 超时时间
    }

    public OkHttpClient getOkHttpClient(Cache cache) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                DebugUtil.error(message);
            }
        });

        if (httpConfig == null) {
            httpConfig = new HttpConfig();
        }
        if (cache == null) {
            try {
                throw new Exception("cache is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        httpConfig.setCache(cache);
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
                return builder.addNetworkInterceptor(new HttpStandardInterceptor(httpConfig)); //有网缓存
            default:
                return builder.addInterceptor(new HttpStandardInterceptor(httpConfig)); //无网缓存
        }
    }
}
