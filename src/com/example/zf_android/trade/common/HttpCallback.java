package com.example.zf_android.trade.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/2/11.
 */
public abstract class HttpCallback<T> {

    private Context context;

    private Dialog loadintDialog;

    public HttpCallback(Activity context) {
        this.context = context;
        loadintDialog = DialogUtil.getLoadingDialg(context);
    }

    public void preLoad() {
        loadintDialog.show();
    }

    public void postLoad() {
        loadintDialog.dismiss();
    }

    public void onFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public abstract void onSuccess(T data);

    public abstract TypeToken<T> getTypeToken();
}
