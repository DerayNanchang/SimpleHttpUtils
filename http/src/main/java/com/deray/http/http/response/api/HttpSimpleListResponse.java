package com.deray.http.http.response.api;

import java.util.List;

/**
 * Created by Deray on 2017/7/10.
 */

public interface HttpSimpleListResponse<T, E> {

    void onAccept(List<T> t);

    void onError(E e);
}
