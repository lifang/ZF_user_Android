package com.example.zf_android.trade.common;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Leo on 2015/2/11.
 */
public class CommonUtil {

    public static void toastShort(Context context, int res) {
        String message = context.getString(res);
        toastShort(context, message);
    }

    public static void toastShort(Context context, String message) {
        if (null != context && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void calcViewMeasure(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static List<Province> readProvincesAndCities(Context context) {
        BufferedReader bufReader = null;
        String result = "";
        try {
            bufReader = new BufferedReader(
                    new InputStreamReader(context.getResources().getAssets().open("city.json")));
            String line;
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != bufReader) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Response<List<Province>> data = JsonParser.fromJson(result, new TypeToken<List<Province>>() {
        });
        List<Province> provinces = data.getResult();
        return provinces;
    }
}
