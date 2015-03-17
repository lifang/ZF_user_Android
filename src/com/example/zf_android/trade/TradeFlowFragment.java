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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Page;
import com.example.zf_android.trade.entity.TradeRecord;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private String tradeClientName;
    private String tradeStartDate;
    private String tradeEndDate;

    private LayoutInflater mInflater;
    private ListView mRecordList;
    private TradeRecordListAdapter mAdapter;
    private List<TradeRecord> mRecords;
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
        return inflater.inflate(R.layout.fragment_trade_flow_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

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
            mAdapter.notifyDataSetChanged();
        }
        toggleButtons();
    }

    private void initViews(View view) {
        mInflater = LayoutInflater.from(getActivity());
        View header = mInflater.inflate(R.layout.fragment_trade_flow, null);
        mTradeClient = header.findViewById(R.id.trade_client);
        mTradeClientName = (TextView) header.findViewById(R.id.trade_client_name);

        mTradeStart = header.findViewById(R.id.trade_start);
        mTradeStartDate = (TextView) header.findViewById(R.id.trade_start_date);
        mTradeEnd = header.findViewById(R.id.trade_end);
        mTradeEndDate = (TextView) header.findViewById(R.id.trade_end_date);

        mTradeSearch = (Button) header.findViewById(R.id.trade_search);
        mTradeStatistic = (Button) header.findViewById(R.id.trade_statistic);
        mTradeSearchContent = (LinearLayout) header.findViewById(R.id.trade_search_content);

        mTradeClient.setOnClickListener(this);
        mTradeStart.setOnClickListener(this);
        mTradeEnd.setOnClickListener(this);
        mTradeSearch.setOnClickListener(this);
        mTradeStatistic.setOnClickListener(this);

        if (null == mRecords) {
            mRecords = new ArrayList<TradeRecord>();
        }

        mRecordList = (ListView) view.findViewById(R.id.trade_record_list);
        mAdapter = new TradeRecordListAdapter();
        mRecordList.addHeaderView(header);
        mRecordList.setAdapter(mAdapter);

        mRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TradeRecord record = (TradeRecord) view.getTag(R.id.trade_status);
                Intent intent = new Intent(getActivity(), TradeDetailActivity.class);
                intent.putExtra(TRADE_RECORD_ID, record.getId());
                intent.putExtra(TRADE_TYPE, mTradeType);
                startActivity(intent);
            }
        });
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
                API.getTradeRecordList(getActivity(),
                        mTradeType, tradeClientName, tradeStartDate, tradeEndDate, 1, 100,
                        new HttpCallback<Page<TradeRecord>>(getActivity()) {

                            @Override
                            public void onSuccess(Page<TradeRecord> data) {
                                mRecords.clear();
                                mRecords.addAll(data.getList());
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public TypeToken<Page<TradeRecord>> getTypeToken() {
                                return new TypeToken<Page<TradeRecord>>() {
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
     * @param date        the chosen date
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

    private class TradeRecordListAdapter extends BaseAdapter {
        TradeRecordListAdapter() {
        }

        @Override
        public int getCount() {
            return mRecords.size();
        }

        @Override
        public TradeRecord getItem(int i) {
            return mRecords.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.trade_flow_item, null);
                holder = new ViewHolder();
                holder.tvStatus = (TextView) convertView.findViewById(R.id.trade_status);
                holder.tvTime = (TextView) convertView.findViewById(R.id.trade_time);
                holder.tvAccount = (TextView) convertView.findViewById(R.id.trade_account);
                holder.tvReceiveAccount = (TextView) convertView.findViewById(R.id.trade_receive_account);
                holder.tvClientNumber = (TextView) convertView.findViewById(R.id.trade_client_number);
                holder.tvAmount = (TextView) convertView.findViewById(R.id.trade_amount);
                // this 2 text values are according to the trade type
                holder.tvAccountKey = (TextView) convertView.findViewById(R.id.trade_account_key);
                holder.tvReceiveAccountKey = (TextView) convertView.findViewById(R.id.trade_receive_account_key);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final TradeRecord record = getItem(i);
            convertView.setTag(R.id.trade_status, record);
            switch (mTradeType) {
                case TRANSFER:
                case REPAYMENT:
                    holder.tvAccountKey.setText(getString(R.string.trade_pay_account));
                    holder.tvReceiveAccountKey.setText(getString(R.string.trade_receive_account));

                    holder.tvAccount.setText(record.getPayFromAccount());
                    holder.tvReceiveAccount.setText(record.getPayIntoAccount());
                    break;
                case CONSUME:
                    holder.tvAccountKey.setText(getString(R.string.trade_close_time));
                    holder.tvReceiveAccountKey.setText(getString(R.string.trade_poundage));

                    holder.tvAccount.setText(record.getTradedTimeStr());
                    holder.tvReceiveAccount.setText(record.getPoundage() + "");
                    break;
                case LIFE_PAY:
                    holder.tvAccountKey.setText(getString(R.string.trade_account_name));
                    holder.tvReceiveAccountKey.setText(getString(R.string.trade_account_number));

                    holder.tvAccount.setText(record.getAccountName());
                    holder.tvReceiveAccount.setText(record.getAccountNumber());
                    break;
                case PHONE_PAY:
                    holder.tvAccountKey.setVisibility(View.INVISIBLE);
                    holder.tvReceiveAccountKey.setText(getString(R.string.trade_phone_number));

                    holder.tvReceiveAccount.setText(record.getPhone());
                    break;
            }

            holder.tvStatus.setText(getResources().getStringArray(R.array.trade_status)[record.getTradedStatus()]);
            holder.tvTime.setText(record.getTradedTimeStr());
            holder.tvClientNumber.setText(record.getTerminalNumber());
            holder.tvAmount.setText(getString(R.string.notation_yuan) + record.getAmount());

            return convertView;
        }
    }

    private static class ViewHolder {
        public TextView tvStatus;
        public TextView tvTime;
        public TextView tvAccountKey;
        public TextView tvReceiveAccountKey;
        public TextView tvAccount;
        public TextView tvReceiveAccount;
        public TextView tvClientNumber;
        public TextView tvAmount;
    }
}
