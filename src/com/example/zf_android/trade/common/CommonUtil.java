package com.example.zf_android.trade.common;

import android.content.Context;

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
