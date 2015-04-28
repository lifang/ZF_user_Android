package com.example.zf_android.trade.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.zf_android.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Leo on 2015/2/11.
 */
public class HttpRequest {

	private AsyncHttpClient client;
	private Context context;
	private TextHttpResponseHandler responseHandler;
	private HttpCallback callback;

	public HttpRequest(final Context context, final HttpCallback callback) {
		this.context = context;
		this.client = new AsyncHttpClient();
		this.callback = callback;
		this.responseHandler = new TextHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				if(callback == null){
					return;
				}
				callback.onFailure(context.getString(R.string.load_data_failed));
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				Log.e("", responseString);
				if(callback == null){
					return;
				}
				Response data;
				try {
					data = null == callback.getTypeToken() ?
							JsonParser.fromJson(responseString) :
							JsonParser.fromJson(responseString, callback.getTypeToken());
				} catch (Exception e) {
					callback.onFailure(context.getString(R.string.parse_data_failed));
					return;
				}
				if (data.getCode() == 1) {
					callback.onSuccess(data.getResult());
				} else if (!TextUtils.isEmpty(data.getMessage())) {
					callback.onFailure(data.getMessage());
				}
			}

			@Override
			public void onFinish() {
				callback.postLoad();
			}

			@Override
			public void onStart() {
				callback.preLoad();
			}
		};
	}

	public void get(String url) {
		if (!NetworkUtil.isNetworkAvailable(context)) {
			callback.onFailure(context.getString(R.string.network_info));
			return;
		}
		client.get(url, responseHandler);
	}

	public void post(String url, RequestParams requestParams) {
		if (!NetworkUtil.isNetworkAvailable(context)) {
			callback.onFailure(context.getString(R.string.network_info));
			return;
		}
		client.post(url, requestParams, responseHandler);
	}

	public void post(String url, HttpEntity entity) {
		if (!NetworkUtil.isNetworkAvailable(context)) {
			callback.onFailure(context.getString(R.string.network_info));
			return;
		}
		client.post(context, url, entity, null, responseHandler);
	}

	public void post(String url, Map<String, Object> params) {
		JSONObject jsonParams = new JSONObject(params);
		StringEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
			entity.setContentType("application/json;charset=UTF-8");
		} catch (UnsupportedEncodingException e) {
			callback.onFailure(context.getString(R.string.load_data_failed));
			return;
		}
		post(url, entity);
	}

	public void post(String url) {
		HttpEntity entity = null;
		post(url, entity);
	}
}
