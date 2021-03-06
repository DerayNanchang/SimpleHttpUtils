package com.deray.http.http.response.api;

import java.util.List;

/**
 * Created by Deray on 2017/8/25.
 */

public interface HttpSimpleListCallResponse<T, E> {
    void onAccept(List<T> t);

    void onError(E e);
}
