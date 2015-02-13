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

    public static final String TERMINAL_LIST = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTerminals/%d";
    public static final String TRADE_RECORD_LIST = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecords/%d/%s/%s/%s/%d/%d";
    public static final String TRADE_RECORD_STATISTIC = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecordTotal/%d/%s/%s/%s";
    public static final String TRADE_RECORD_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecord/%d";

    public static void getTerminalList(Context context, int customerId, HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TERMINAL_LIST, customerId));
    }

    public static void getTradeRecordList(
            Context context,
            int tradeTypeId,
            String terminalNumber,
            String startTime,
            String endTime,
            int page,
            int rows,
            HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TRADE_RECORD_LIST,
                tradeTypeId, terminalNumber, startTime, endTime, page, rows));
    }

    public static void getTradeRecordStatistic(
            Context context,
            int tradeTypeId,
            String terminalNumber,
            String startTime,
            String endTime,
            HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TRADE_RECORD_STATISTIC,
                tradeTypeId, terminalNumber, startTime, endTime));
    }

    public static void getTradeRecordDetail(Context context, int recordId, HttpCallback callback) {
        new HttpRequest(context, callback).get(String.format(TRADE_RECORD_DETAIL, recordId));
    }
}
