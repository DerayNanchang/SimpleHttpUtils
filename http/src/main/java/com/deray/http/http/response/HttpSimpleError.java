package com.deray.http.http.response;

import android.content.Context;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Deray on 2017/5/5.
 */

public class HttpSimpleError {

    public static void httpSimpleErrorDisposeCenter(Context context, Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof UnknownHostException) {
                showToast(context, "Please open the network" + throwable.getMessage() + "");
            } else if (throwable instanceof SocketTimeoutException) {
                showToast(context, "The request timeout" + throwable.getMessage() + "");
            } else if (throwable instanceof ConnectException) {
                showToast(context, "The connection failed" + throwable.getMessage() + "");
            } else if (throwable instanceof HttpException) {
                showToast(context, "The request timeout" + throwable.getMessage() + "");
            } else {
                if (throwable.getMessage() != null) {
                    showToast(context, "An unknown error" + throwable.getMessage() + "");
                } else {
                    showToast(context, "request failed . message is empty in response" + "");
                }
            }
        } else {
            showToast(context, "request failed .  throwable is empty in response" + "");
        }
    }


    private static void showToast(Context context, String msg) {
        if (context.getApplicationContext() != null) {
            Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
