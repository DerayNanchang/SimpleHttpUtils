package com.deray.http.http.response;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Deray on 2017/5/24.
 */

public abstract class HttpSimpleConsumer<T> implements Consumer<T> {

    @Override
    public void accept(@NonNull T t) throws Exception {
        if (t == null) {
            return;
        }
        //TODO  在没有找到更好的办法前暂时放弃
/*        if (t instanceof Throwable) {
            HttpSimpleError.httpSimpleErrorDisposeCenter((Throwable) t);
        }*/

        simpleCallBack(t);

    }

    protected abstract void simpleCallBack(T t);

}
