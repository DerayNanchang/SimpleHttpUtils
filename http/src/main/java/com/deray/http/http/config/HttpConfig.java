package com.deray.http.http.config;

import android.content.Context;

import java.io.Serializable;

import okhttp3.Cache;

/**
 * Created by Deray on 2017/5/23.
 */

public class HttpConfig implements Serializable {

    private Context context;
    private boolean isDiskCache;   // 是否本地缓存
    private boolean isMemoryCache; // 是否内存缓存
    private int diskCacheTime;     // 磁盘缓存时间
    private int memoryCacheTime;       // 内存缓存时间
    private int maxDiskCache;      // 最大缓存数
    private int connectTimeOut; //超时时间
    private int notNetCacheTime;
    private boolean isRetryOnConnectionFailure;// 超时是否重连
    public static final int DEFAULT_NOT_NET_CACHE_TIME = 0;
    public static final int DEFAULT_MEMORY_CACHE_TIME = 60;
    public static final int DEFAULT_DISK_CACHE_TIME = 60 * 60 * 24 * 4;
    public static final int DEFAULT_MAX_DISK_CACHE = 30 * 1024 * 1024;
    public static final int DEFAULT_CONNECT_TIME_OUT = 8000;
    public static final int NO_INTERCEPTOR = 0;
    public static final int INTERCEPTOR = 1;
    public static final int NETWORK_INTERCEPTOR = 2;
    private int interceptorType;
    private Cache cache;

    private static final String CACHE_DIR_NAME = "httpCache";
    private String cacheDirName;

    public HttpConfig() {
        setMemoryCache(false);
        setMemoryCacheTime(DEFAULT_MEMORY_CACHE_TIME);
        setDiskCache(false);
        setDiskCacheTime(DEFAULT_DISK_CACHE_TIME);
        setMaxDiskCache(DEFAULT_MAX_DISK_CACHE);
        setNotNetCacheTime(DEFAULT_NOT_NET_CACHE_TIME);
        setInterceptorType(INTERCEPTOR);
        setConnectTimeOut(DEFAULT_CONNECT_TIME_OUT);
        setRetryOnConnectionFailure(true);
    }

    public HttpConfig(Cache cache) {
        setMemoryCache(false);
        setMemoryCacheTime(DEFAULT_MEMORY_CACHE_TIME);
        setDiskCache(false);
        setDiskCacheTime(DEFAULT_DISK_CACHE_TIME);
        setMaxDiskCache(DEFAULT_MAX_DISK_CACHE);
        setNotNetCacheTime(DEFAULT_NOT_NET_CACHE_TIME);
        setInterceptorType(INTERCEPTOR);
        setConnectTimeOut(DEFAULT_CONNECT_TIME_OUT);
        setRetryOnConnectionFailure(true);
        this.context = context.getApplicationContext();
        setCache(cache);
    }

    public int getInterceptorType() {
        return interceptorType;
    }

    public void setInterceptorType(int interceptorType) {
        this.interceptorType = interceptorType;
    }

    public int getNotNetCacheTime() {
        return notNetCacheTime;
    }

    public void setNotNetCacheTime(int notNetCacheTime) {
        this.notNetCacheTime = notNetCacheTime;
    }

    public Cache getChche() {
        return this.cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
        //this.cache = new Cache(new File(context.getCacheDir(), cacheDirName == null ? context.getPackageName() + CACHE_DIR_NAME : cacheDirName), getMaxDiskCache());
    }

    public boolean isDiskCache() {
        return isDiskCache;
    }

    public void setDiskCache(boolean diskCache) {
        isDiskCache = diskCache;
    }

    public boolean isMemoryCache() {
        return isMemoryCache;
    }

    public void setMemoryCache(boolean memoryCache) {
        isMemoryCache = memoryCache;
    }

    public int getDiskCacheTime() {
        return diskCacheTime;
    }

    public void setDiskCacheTime(int diskCacheTime) {
        this.diskCacheTime = diskCacheTime;
    }

    public int getMemoryCacheTime() {
        return memoryCacheTime;
    }

    public void setMemoryCacheTime(int memoryCacheTime) {
        this.memoryCacheTime = memoryCacheTime;
    }

    public int getMaxDiskCache() {
        return maxDiskCache;
    }

    public void setMaxDiskCache(int maxDiskCache) {
        this.maxDiskCache = maxDiskCache;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public boolean isRetryOnConnectionFailure() {
        return isRetryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        isRetryOnConnectionFailure = retryOnConnectionFailure;
    }

    @Override
    public String toString() {
        return "HttpConfig{" +
                "context=" + context +
                ", isDiskCache=" + isDiskCache +
                ", isMemoryCache=" + isMemoryCache +
                ", diskCacheTime=" + diskCacheTime +
                ", memoryCacheTime=" + memoryCacheTime +
                ", maxDiskCache=" + maxDiskCache +
                ", connectTimeOut=" + connectTimeOut +
                ", isRetryOnConnectionFailure=" + isRetryOnConnectionFailure +
                ", cache=" + cache +
                '}';
    }
}
