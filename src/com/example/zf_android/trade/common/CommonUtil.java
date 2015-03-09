package com.example.zf_android.trade.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static void showDatePicker(FragmentActivity activity, final String date, final OnDateSetListener listener) {

        final Calendar c = Calendar.getInstance();
        if (TextUtils.isEmpty(date)) {
            c.setTime(new Date());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker,
                                                  int year, int month, int day) {
                                month = month + 1;
                                String dateStr = year + "-"
                                        + (month < 10 ? "0" + month : month) + "-"
                                        + (day < 10 ? "0" + day : day);
                                if (null != listener) {
                                    listener.onDateSet(dateStr);
                                }
                            }
                        }, year, month, day);
            }
        }.show(activity.getSupportFragmentManager(), "DatePicker");
    }

    public static void uploadFile(String filePath, String paramName, OnUploadListener listener) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(API.UPLOAD_IMAGE);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"" + paramName + "\";filename=\"" +
                    fileName + "\"" + end);
            ds.writeBytes(end);
            FileInputStream fStream = new FileInputStream(filePath);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            fStream.close();
            ds.flush();
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            ds.close();
            if (null != listener)
                listener.onSuccess(b.toString());
        } catch (Exception e) {
            if (null != listener)
                listener.onFailed(e.getMessage());
        }
    }

    public static interface OnUploadListener {
        void onSuccess(String result);

        void onFailed(String message);
    }

    public static interface OnDateSetListener {
        void onDateSet(String date);
    }
}
