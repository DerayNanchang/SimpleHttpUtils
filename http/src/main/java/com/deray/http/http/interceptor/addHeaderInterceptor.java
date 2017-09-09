package com.deray.http.http.interceptor;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Deray on 2017/9/9.
 */

public class addHeaderInterceptor implements Interceptor {

    private List<String> header;

    public addHeaderInterceptor(List<String> header) {
        this.header = header;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        Request builder = null;
        if (header == null) {

        } else {
            if (header.size() == 2) {
                builder = newBuilder.addHeader(header.get(0), header.get(1)).build();
            }
            if (header.size() == 4) {
                newBuilder.addHeader(header.get(0), header.get(1));
                builder = newBuilder.addHeader(header.get(2), header.get(3)).build();
            }
            if (header.size() == 6) {
                newBuilder.addHeader(header.get(0), header.get(1));
                newBuilder.addHeader(header.get(2), header.get(3));
                builder = newBuilder.addHeader(header.get(4), header.get(5)).build();
            }
        }
        return chain.proceed(builder);
    }
}
