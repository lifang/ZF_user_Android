package com.example.zf_android.trade;

import android.content.Context;

import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.HttpRequest;
import com.example.zf_android.trade.entity.TradeClient;
import com.example.zf_android.trade.entity.TradeRecord;
import com.google.gson.Gson;

import org.apache.http.client.HttpClient;

import java.util.List;

/**
 * Created by Leo on 2015/2/11.
 */
public class API {

    public static final String SCHEMA = "http://";
    public static final String HOST = "114.215.149.242:18080";

    public static final String TERMINALS = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTerminals/%d";
    public static final String TRADE_RECORD = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecords/%d/%s/%s/%s/%d/%d";
    public static final String TRADE_RECORD_TOTAL = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecordTotal/%d/%s/%s/%s";

    public static void getTerminals(Context context, int customerId, HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TERMINALS, customerId));
    }

    public static void getTradeRecord(
            Context context,
            int tradeTypeId,
            String terminalNumber,
            String startTime,
            String endTime,
            int page,
            int rows,
            HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TRADE_RECORD,
                tradeTypeId, terminalNumber, startTime, endTime, page, rows));
    }

    public static void getTradeRecordTotal(
            Context context,
            int tradeTypeId,
            String terminalNumber,
            String startTime,
            String endTime,
            HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TRADE_RECORD_TOTAL,
                tradeTypeId, terminalNumber, startTime, endTime));
    }

}
