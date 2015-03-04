package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TradeIntent.CLIENT_NUMBER;
import static com.example.zf_android.trade.Constants.TradeIntent.END_DATE;
import static com.example.zf_android.trade.Constants.TradeIntent.REQUEST_TRADE_CLIENT;
import static com.example.zf_android.trade.Constants.TradeIntent.START_DATE;
import static com.example.zf_android.trade.Constants.TradeIntent.TRADE_RECORD_ID;
import static com.example.zf_android.trade.Constants.TradeIntent.TRADE_TYPE;
import static com.example.zf_android.trade.Constants.TradeType.CONSUME;
import static com.example.zf_android.trade.Constants.TradeType.LIFE_PAY;
import static com.example.zf_android.trade.Constants.TradeType.PHONE_PAY;
import static com.example.zf_android.trade.Constants.TradeType.REPAYMENT;
import static com.example.zf_android.trade.Constants.TradeType.TRANSFER;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TradeRecord;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/2/6.
 */
public class TradeFlowFragment extends Fragment implements View.OnClickListener {

    private int mTradeType;

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
    private List<TradeRecord> records;
    private boolean hasSearched = false;

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
        mTradeSearchContent = (LinearLayout) view.findViewById(R.id.trade_search_content);

        mTradeContainer = (LinearLayout) view.findViewById(R.id.trade_container);

        mTradeClient.setOnClickListener(this);
        mTradeStart.setOnClickListener(this);
        mTradeEnd.setOnClickListener(this);
        mTradeSearch.setOnClickListener(this);
        mTradeStatistic.setOnClickListener(this);

        // restore the saved state
        if (!TextUtils.isEmpty(tradeClientName)) {
            mTradeClientName.setText(tradeClientName);
        }
        if (!TextUtils.isEmpty(tradeStartDate)) {
            mTradeStartDate.setText(tradeStartDate);
        }
        if (!TextUtils.isEmpty(tradeEndDate)) {
            mTradeEndDate.setText(tradeEndDate);
        }
        if (hasSearched) {
            mTradeSearchContent.setVisibility(View.VISIBLE);
            addTradeItems();
        }
        toggleButtons();
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
                String clientName = data.getStringExtra(CLIENT_NUMBER);
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
                i.putExtra(CLIENT_NUMBER, tradeClientName);
                startActivityForResult(i, REQUEST_TRADE_CLIENT);
                break;
            case R.id.trade_start:
                showDatePicker(tradeStartDate, true);
                break;
            case R.id.trade_end:
                showDatePicker(tradeEndDate, false);
                break;
            case R.id.trade_search:
                hasSearched = true;
                mTradeSearchContent.setVisibility(View.VISIBLE);
                Log.d("", "search records from trade type = " + mTradeType);
                API.getTradeRecordList(getActivity(),
                        mTradeType, tradeClientName, tradeStartDate, tradeEndDate, 1, 100,
                        new HttpCallback<List<TradeRecord>>(getActivity()) {

                            @Override
                            public void onSuccess(List<TradeRecord> data) {
                                records = data;
                                addTradeItems();
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
                intent.putExtra(TRADE_TYPE, mTradeType);
                intent.putExtra(CLIENT_NUMBER, tradeClientName);
                intent.putExtra(START_DATE, tradeStartDate);
                intent.putExtra(END_DATE, tradeEndDate);
                startActivity(intent);
                break;
        }
    }

    private void addTradeItems() {
        if (null == records || records.size() == 0)
            return;
        mTradeContainer.removeAllViews();
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (final TradeRecord record : records) {
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.trade_flow_item, null);
            TextView status = (TextView) itemLayout.findViewById(R.id.trade_status);
            TextView time = (TextView) itemLayout.findViewById(R.id.trade_time);
            TextView account = (TextView) itemLayout.findViewById(R.id.trade_account);
            TextView receiveAccount = (TextView) itemLayout.findViewById(R.id.trade_receive_account);
            TextView clientNumber = (TextView) itemLayout.findViewById(R.id.trade_client_number);
            TextView amount = (TextView) itemLayout.findViewById(R.id.trade_amount);
            
            // this text value is according to the trade type
            TextView accountKey = (TextView) itemLayout.findViewById(R.id.trade_account_key);
            TextView receiveAccountKey = (TextView) itemLayout.findViewById(R.id.trade_receive_account_key);
			switch (mTradeType) {
			case TRANSFER:
			case REPAYMENT:
				accountKey.setText(getString(R.string.trade_pay_account));
				receiveAccountKey.setText(getString(R.string.trade_receive_account));
				
				account.setText(record.getPayFromAccount());
	            receiveAccount.setText(record.getPayIntoAccount());
				break;
			case CONSUME:
				accountKey.setText(getString(R.string.trade_close_time));
				receiveAccountKey.setText(getString(R.string.trade_poundage));
				
				account.setText(record.getTradedTimeStr());
	            receiveAccount.setText(record.getPoundage() + "");
				break;
			case LIFE_PAY:
				accountKey.setText(getString(R.string.trade_account_name));
				receiveAccountKey.setText(getString(R.string.trade_account_number));
				
				account.setText(record.getAccountName());
	            receiveAccount.setText(record.getAccountNumber());
				break;
			case PHONE_PAY:
				accountKey.setVisibility(View.INVISIBLE);
				receiveAccountKey.setText(getString(R.string.trade_phone_number));
				
				receiveAccountKey.setText(record.getPhone());
				break;
			}
            
            status.setText(getResources().getStringArray(R.array.trade_status)[record.getTradedStatus()]);
            time.setText(record.getTradedTimeStr());
            clientNumber.setText(record.getTerminalNumber());
            amount.setText(getString(R.string.notation_yuan) + record.getAmount());
            mTradeContainer.addView(itemLayout);
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TradeDetailActivity.class);
                    intent.putExtra(TRADE_RECORD_ID, record.getId());
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * enable or disable the buttons
     *
     * @param
     * @return
     */
    private void toggleButtons() {
        boolean shouldEnable = !TextUtils.isEmpty(tradeClientName)
                && !TextUtils.isEmpty(tradeStartDate)
                && !TextUtils.isEmpty(tradeEndDate);
        mTradeSearch.setEnabled(shouldEnable);
        mTradeStatistic.setEnabled(shouldEnable);
    }

    /**
     * show the date picker
     *
     * @param date the chosen date
     * @param isStartDate if true to choose the start date, else the end date
     * @return
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
