package com.deray.http.http.retrofit.httpResponse;

/**
 * Created by Deray on 2017/8/25.
 */

public interface HttpSimpleCallResponse<T, E> {
    void onAccept(T t);

    void onError(E e);
}
