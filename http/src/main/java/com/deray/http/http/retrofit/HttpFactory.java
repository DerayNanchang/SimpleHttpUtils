package com.deray.http.http.retrofit;

import com.deray.http.http.config.HttpConfig;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Deray on 2017/5/5.
 */

public class HttpFactory {

    private final static ObservableTransformer ot = new SimpleRxJavaOb();

    /**
     * activity network request
     *
     * @param observable
     * @param activity
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> accept(Observable<T> observable, RxAppCompatActivity activity) {
        Observable compose = observable.compose(ot);
        return compose.compose(activity.<T>bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * fragment network request
     *
     * @param observable
     * @param fragment
     * @param <T>
     * @return
     */
    public static <T> Observable<T> accept(Observable<T> observable, RxFragment fragment) {
        Observable compose = observable.compose(ot);
        return compose.compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }


    private static class SimpleRxJavaOb<T> implements ObservableTransformer<T, T> {

        @Override
        public Observable<T> apply(Observable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .timeout(HttpConfig.DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .retry(5);
        }
    }
}
