package com.example.zf_android.trade;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Leo on 2015/2/6.
 */
public class TradeFlowFragment extends Fragment implements View.OnClickListener {

    public static final String TRADE_TYPE = "trade_type";
    public static final int TRADE_TRANSFER = 0;
    public static final int TRADE_CONSUME = 1;
    public static final int TRADE_REPAY = 2;
    public static final int TRADE_LIFE_CHARGE = 3;
    public static final int TRADE_PHONE_CHARGE = 4;

    public static final int REQUEST_TRADE_CLIENT = 0;

    private int mTradeType;

    private View mTradeClient;
    private TextView mTradeClientName;

    private View mTradeStart;
    private TextView mTradeStartDate;
    private View mTradeEnd;
    private TextView mTradeEndDate;

    private Button mTradeSearch;
    private Button mTradeStatistic;

    /**
     * 终端名
     */
    private String tradeClientName;
    /**
     * 开始时间
     */
    private String tradeStartDate;
    /**
     * 结束时间
     */
    private String tradeEndDate;

    public static TradeFlowFragment newInstance(int tradeType) {
        TradeFlowFragment fragment = new TradeFlowFragment();
        Bundle args = new Bundle();
        args.putInt(TRADE_TYPE, tradeType);
        fragment.setArguments(args);
        return fragment;
    }

    public TradeFlowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTradeType = getArguments().getInt(TRADE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trade_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTradeClient = view.findViewById(R.id.trade_client);
        mTradeClientName = (TextView) view.findViewById(R.id.trade_client_name);

        mTradeStart = view.findViewById(R.id.trade_start);
        mTradeStartDate = (TextView) view.findViewById(R.id.trade_start_date);
        mTradeEnd = view.findViewById(R.id.trade_end);
        mTradeEndDate = (TextView) view.findViewById(R.id.trade_end_date);

        mTradeSearch = (Button) view.findViewById(R.id.trade_search);
        mTradeStatistic = (Button) view.findViewById(R.id.trade_statistic);

        mTradeClient.setOnClickListener(this);
        mTradeStart.setOnClickListener(this);
        mTradeEnd.setOnClickListener(this);
        mTradeSearch.setOnClickListener(this);
        mTradeStatistic.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_TRADE_CLIENT:
                String clientName = data.getStringExtra(TradeClientActivity.CLIENT_NAME);
                mTradeClientName.setText(clientName);
                mTradeSearch.setEnabled(true);
                mTradeStatistic.setEnabled(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trade_client:
                startActivityForResult(new Intent(getActivity(), TradeClientActivity.class), REQUEST_TRADE_CLIENT);
                break;
            case R.id.trade_start:
                showDatePicker(tradeStartDate, true);
                break;
            case R.id.trade_end:
                showDatePicker(tradeEndDate, false);
                break;
        }
    }

    /**
     * 选择日期控件
     *
     * @param date        已经选好的日期, 如果没有则显示当前日期
     * @param isStartDate true开始时间, false结束时间
     */
    private void showDatePicker(final String date, final boolean isStartDate) {

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
                                if (isStartDate) {
                                    mTradeStartDate.setText(dateStr);
                                    tradeStartDate = dateStr;
                                } else {
                                    mTradeEndDate.setText(dateStr);
                                    tradeEndDate = dateStr;
                                }
                            }
                        }, year, month, day);
            }
        }.show(getActivity().getSupportFragmentManager(), "DatePicker");

    }
}
