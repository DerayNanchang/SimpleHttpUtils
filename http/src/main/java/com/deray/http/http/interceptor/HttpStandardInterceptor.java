package com.deray.http.http.interceptor;

import com.deray.http.http.config.HttpConfig;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Deray on 2017/5/24.
 */

public class HttpStandardInterceptor implements Interceptor {

    private HttpConfig httpConfig;

    public HttpStandardInterceptor(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 这个是请求的时候是否缓存
        Request request = chain.request();
        request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
        // 相应时是否缓存数据
        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder()
                //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                .header("Cache-Control", "public, max-age=" + httpConfig.getNotNetCacheTime())
                .removeHeader("Pragma")
                .build();
    }
}
