package com.deray.http.http.retrofit;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.deray.http.http.retrofit.httpResponse.HttpSimpleCallResponse;
import com.deray.http.http.retrofit.httpResponse.HttpSimpleListCallResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deray on 2017/8/25.
 */

public class HttpRequest {

    private HttpRequest() {
    }

    private static HttpRequest simpleDerayHttp = new HttpRequest();

    public static HttpRequest getInstance() {
        return simpleDerayHttp;
    }
    private final ObservableTransformer ot = new RxjavaObConfig();

    public <T> Observable<T> accept(Observable<T> observable, RxAppCompatActivity activity) {
        Observable compose = observable.compose(ot);
        return compose.compose(activity.<T>bindUntilEvent(ActivityEvent.DESTROY));
    }
    public <T> Observable<T> accept(Observable<T> observable, RxFragment fragment) {
        Observable compose = observable.compose(ot);
        return compose.compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    public <T> void accept(Call<ResponseBody> call, final Class<T> clazz, final HttpSimpleListCallResponse<T, Throwable> httpSimpleCallResponse) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null) {
                        String data = response.body().string();
                        if (!TextUtils.isEmpty(data)) {
                            httpSimpleCallResponse.onAccept(JSON.parseArray(data, clazz));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                httpSimpleCallResponse.onError(t);
            }
        });
    }

    public <T> void accept(Call<ResponseBody> call, final Class<T> clazz, final HttpSimpleCallResponse<T, Throwable> httpSimpleCallResponse) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null) {
                        String data = response.body().string();
                        if (!TextUtils.isEmpty(data)) {
                            httpSimpleCallResponse.onAccept(JSON.parseObject(data, clazz));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                httpSimpleCallResponse.onError(t);
            }
        });
    }
}
