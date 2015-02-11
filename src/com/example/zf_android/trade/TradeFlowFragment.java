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
import android.widget.Toast;

import com.example.zf_android.R;
import com.example.zf_android.trade.common.DialogUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TradeRecord;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Leo on 2015/2/6.
 */
public class TradeFlowFragment extends Fragment implements View.OnClickListener {

    public static final String TRADE_TYPE = "trade_type";
    public static final int TRADE_TRANSFER = 1;
    public static final int TRADE_CONSUME = 2;
    public static final int TRADE_REPAY = 3;
    public static final int TRADE_LIFE_CHARGE = 4;
    public static final int TRADE_PHONE_CHARGE = 5;

    public static final int REQUEST_TRADE_CLIENT = 0;

    private int mTradeType;

    private Dialog dlg;

    private View mTradeClient;
    private TextView mTradeClientName;

    private View mTradeStart;
    private TextView mTradeStartDate;
    private View mTradeEnd;
    private TextView mTradeEndDate;

    private Button mTradeSearch;
    private Button mTradeStatistic;
    private LinearLayout mTradeSearchContent;

    private LinearLayout mTradeContainer;

    private String tradeClientName;

    private String tradeStartDate;

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

        dlg = DialogUtil.getLoadingDialg(getActivity());

        mTradeClient = view.findViewById(R.id.trade_client);
        mTradeClientName = (TextView) view.findViewById(R.id.trade_client_name);

        mTradeStart = view.findViewById(R.id.trade_start);
        mTradeStartDate = (TextView) view.findViewById(R.id.trade_start_date);
        mTradeEnd = view.findViewById(R.id.trade_end);
        mTradeEndDate = (TextView) view.findViewById(R.id.trade_end_date);

        mTradeSearch = (Button) view.findViewById(R.id.trade_search);
        mTradeStatistic = (Button) view.findViewById(R.id.trade_statistic);
        mTradeSearchContent = (LinearLayout) view.findViewById(R.id.trade_search_content);

        mTradeContainer = (LinearLayout) view.findViewById(R.id.trade_container);

        mTradeClient.setOnClickListener(this);
        mTradeStart.setOnClickListener(this);
        mTradeEnd.setOnClickListener(this);
        mTradeSearch.setOnClickListener(this);
        mTradeStatistic.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                String clientName = data.getStringExtra(TradeClientActivity.CLIENT_NUMBER);
                mTradeClientName.setText(clientName);
                tradeClientName = clientName;
                toggleButtons();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trade_client:
                Intent i = new Intent(getActivity(), TradeClientActivity.class);
                i.putExtra(TradeClientActivity.CLIENT_NUMBER, tradeClientName);
                startActivityForResult(i, REQUEST_TRADE_CLIENT);
                break;
            case R.id.trade_start:
                showDatePicker(tradeStartDate, true);
                break;
            case R.id.trade_end:
                showDatePicker(tradeEndDate, false);
                break;
            case R.id.trade_search:
                mTradeSearchContent.setVisibility(View.VISIBLE);
                API.getTradeRecord(getActivity(),
                        mTradeType, tradeClientName, tradeStartDate, tradeEndDate, 1, 100,
                        new HttpCallback<List<TradeRecord>>(getActivity()) {

                            @Override
                            public void onSuccess(List<TradeRecord> data) {
                                LayoutInflater inflater = LayoutInflater.from(getActivity());
                                for (TradeRecord record : data) {
                                    LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.trade_flow_item, null);
                                    TextView time = (TextView) itemLayout.findViewById(R.id.trade_time);
                                    TextView account = (TextView) itemLayout.findViewById(R.id.trade_account);
                                    TextView receiveAccount = (TextView) itemLayout.findViewById(R.id.trade_receive_account);
                                    TextView clientNumber = (TextView) itemLayout.findViewById(R.id.trade_client_number);
                                    TextView amount = (TextView) itemLayout.findViewById(R.id.trade_amount);
                                    time.setText(record.getTradedTimeStr());
                                    account.setText(record.getPayFromAccount());
                                    receiveAccount.setText(record.getPayIntoAccount());
                                    clientNumber.setText(record.getTerminalNumber());
                                    amount.setText(getString(R.string.notation_yuan) + record.getAmount());
                                    mTradeContainer.addView(itemLayout);
                                    itemLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(getActivity(), TradeDetailActivity.class));
                                        }
                                    });
                                }
                            }

                            @Override
                            public TypeToken<List<TradeRecord>> getTypeToken() {
                                return new TypeToken<List<TradeRecord>>() {
                                };
                            }
                        });
                break;
            case R.id.trade_statistic:
                Intent intent = new Intent(getActivity(), TradeStatisticActivity.class);
                intent.putExtra(TradeStatisticActivity.TRADE_TYPE, mTradeType);
                intent.putExtra(TradeStatisticActivity.CLIENT_NUMBER, tradeClientName);
                intent.putExtra(TradeStatisticActivity.START_DATE, tradeStartDate);
                intent.putExtra(TradeStatisticActivity.END_DATE, tradeEndDate);
                startActivity(intent);
                break;
        }
    }

    /**
     * 将按钮置为可以点击, 触发条件:
     * <li>已选择终端号</li>
     * <li>已选择开始时间</li>
     * <li>已选择结束时间</li>
     */
    private void toggleButtons() {
        boolean shouldEnable = !TextUtils.isEmpty(tradeClientName)
                && !TextUtils.isEmpty(tradeStartDate)
                && !TextUtils.isEmpty(tradeEndDate);
        mTradeSearch.setEnabled(shouldEnable);
        mTradeStatistic.setEnabled(shouldEnable);
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
                                    if (!TextUtils.isEmpty(tradeStartDate) && dateStr.compareTo(tradeStartDate) < 0) {
                                        Toast.makeText(getActivity(), getString(R.string.toast_end_date_error), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    mTradeEndDate.setText(dateStr);
                                    tradeEndDate = dateStr;
                                }
                                toggleButtons();
                            }
                        }, year, month, day);
            }
        }.show(getActivity().getSupportFragmentManager(), "DatePicker");

    }
}
