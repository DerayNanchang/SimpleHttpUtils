package com.deray.http.http.retrofit.httpResponse;

/**
 * Created by Deray on 2017/5/4.
 */

public interface HttpSimpleResponse<T, E> {

    void onAccept(T t);

    void onError(E e);
}
