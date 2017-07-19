package com.deray.http.http.retrofit;

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
                //提示log 按公司需求
                showToast(context, "请打开网络" + throwable.getMessage() + "");
            } else if (throwable instanceof SocketTimeoutException) {
                showToast(context, "请求超时" + throwable.getMessage() + "");
            } else if (throwable instanceof ConnectException) {
                showToast(context, "连接失败" + throwable.getMessage() + "");
            } else if (throwable instanceof HttpException) {
                showToast(context, "请求超时" + throwable.getMessage() + "");
            } else {
                if (throwable.getMessage() != null) {
                    showToast(context, "未知错误：" + throwable.getMessage() + "");
                } else {
                    showToast(context, "请求失败 服务器返回数据异常" + "");
                }
            }
        } else {
            showToast(context, "请求失败 服务器返回数据异常" + "");
        }
    }


    private static void showToast(Context context, String msg) {
        if (context.getApplicationContext() != null) {
            Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
