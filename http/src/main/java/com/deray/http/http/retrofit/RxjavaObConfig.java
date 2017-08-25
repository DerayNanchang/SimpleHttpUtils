package com.deray.http.http.retrofit;

import com.deray.http.http.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Deray on 2017/8/25.
 */

public class RxjavaObConfig<T> implements ObservableTransformer<T, T> {
    @Override
    public Observable<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .timeout(HttpConfig.DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .retry(5);
    }
}
