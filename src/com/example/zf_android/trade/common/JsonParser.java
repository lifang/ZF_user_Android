package com.example.zf_android.trade.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/2/11.
 */
public class JsonParser {

    public static <T> Response<T> fromJson(String json, Class<T> rawType) {
        Gson gson = new Gson();
        JsonObject jo = gson.fromJson(json, JsonObject.class);

        int code = jo.get("code").getAsInt();
        JsonElement messageElement = jo.get("message");
        String message = messageElement.isJsonNull() ? null : messageElement.getAsString();
        JsonElement resultElement = jo.get("result");
        T result = gson.fromJson(resultElement, rawType);

        return new Response(code, message, result);
    }

    public static <T> Response<T> fromJson(String json, TypeToken<T> typeToken) {
        Gson gson = new Gson();
        JsonObject jo = gson.fromJson(json, JsonObject.class);

        int code = jo.get("code").getAsInt();
        JsonElement messageElement = jo.get("message");
        String message = messageElement.isJsonNull() ? null : messageElement.getAsString();
        JsonElement resultElement = jo.get("result");
        T result = gson.fromJson(resultElement, typeToken.getType());

        return new Response(code, message, result);
    }
}
