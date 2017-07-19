package com.deray.http.http.interceptor;

import android.content.Context;

import com.deray.http.http.config.HttpConfig;
import com.deray.http.http.utils.HttpUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Deray on 2017/5/24.
 */

public class HttpStandardInterceptor implements Interceptor {

    private Context context;
    private HttpConfig httpConfig;

    public HttpStandardInterceptor(Context context, HttpConfig httpConfig) {
        this.context = context;
        this.httpConfig = httpConfig;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 这个是请求的时候是否缓存
        Request request = chain.request();
        if (!HttpUtils.isNetworkAvailable(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        // 相应时是否缓存数据
        Response originalResponse = chain.proceed(request);
        if (HttpUtils.isNetworkAvailable(context)) {
            // 网络连通时不进行缓存
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                    .header("Cache-Control", "public, max-age=" + httpConfig.getNotNetCacheTime())
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + httpConfig.getDiskCacheTime())
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
