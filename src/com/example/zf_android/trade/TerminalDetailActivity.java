package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TerminalApply;
import com.example.zf_android.trade.entity.TerminalComment;
import com.example.zf_android.trade.entity.TerminalDetail;
import com.example.zf_android.trade.entity.TerminalRate;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;
import static com.example.zf_android.trade.Constants.TerminalStatus.CANCELED;
import static com.example.zf_android.trade.Constants.TerminalStatus.OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.PART_OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.STOPPED;
import static com.example.zf_android.trade.Constants.TerminalStatus.UNOPENED;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalDetailActivity extends Activity {

    private int mTerminalId;

    private LayoutInflater mInflater;
    private TextView mStatus;
    private Button mBtnLeftTop;
    private Button mBtnLeftBottom;
    private Button mBtnRightTop;
    private Button mBtnRightBottom;
    private LinearLayout mCategoryContainer;
    private LinearLayout mCommentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);
        setContentView(R.layout.activity_terminal_detail);
        new TitleMenuUtil(this, getString(R.string.title_terminal_detail)).show();

        initViews();
        loadData();
    }

    private void initViews() {
        mInflater = LayoutInflater.from(this);
        mStatus = (TextView) findViewById(R.id.terminal_detail_status);
        mBtnLeftTop = (Button) findViewById(R.id.terminal_button_left_top);
        mBtnLeftBottom = (Button) findViewById(R.id.terminal_button_left_bottom);
        mBtnRightTop = (Button) findViewById(R.id.terminal_button_right_top);
        mBtnRightBottom = (Button) findViewById(R.id.terminal_button_right_bottom);
        mCategoryContainer = (LinearLayout) findViewById(R.id.terminal_category_container);
        mCommentContainer = (LinearLayout) findViewById(R.id.terminal_comment_container);
    }

    private void loadData() {
        API.getTerminalDetail(this, mTerminalId, new HttpCallback<TerminalDetail>(this) {
            @Override
            public void onSuccess(TerminalDetail data) {
                TerminalApply apply = data.getApplyDetails();
                List<TerminalComment> comments = data.getTrackRecord();
                List<TerminalRate> rates = data.getRates();

                // set the status and buttons
                setStatusAndButtons(apply);
                // render terminal info
                LinearLayout terminalCategory = setTerminalInfo(apply);
                // add rate's table
                addRatesTable(terminalCategory, rates);
                // render terminal open info
                LinearLayout openCatetory = setOpenInfo();
                // add terminal trace records
                addComments(comments);
            }

            @Override
            public TypeToken<TerminalDetail> getTypeToken() {
                return new TypeToken<TerminalDetail>() {
                };
            }
        });
    }

    private void setStatusAndButtons(TerminalApply apply) {
        if (null == apply) return;
        String[] terminalStatus = getResources().getStringArray(R.array.terminal_status);
        mStatus.setText(terminalStatus[apply.getStatus()]);
        switch (apply.getStatus()) {
            case OPENED:
                mBtnRightTop.setVisibility(View.VISIBLE);
                mBtnRightBottom.setVisibility(View.VISIBLE);

                mBtnRightTop.setText(getString(R.string.terminal_button_video));
                mBtnRightBottom.setText(getString(R.string.terminal_button_pos));
                break;
            case PART_OPENED:
                mBtnLeftTop.setVisibility(View.VISIBLE);
                mBtnLeftBottom.setVisibility(View.VISIBLE);
                mBtnRightTop.setVisibility(View.VISIBLE);
                mBtnRightBottom.setVisibility(View.VISIBLE);

                mBtnLeftTop.setText(getString(R.string.terminal_button_sync));
                mBtnLeftBottom.setText(getString(R.string.terminal_button_reopen));
                mBtnRightTop.setText(getString(R.string.terminal_button_video));
                mBtnRightBottom.setText(getString(R.string.terminal_button_pos));
                break;
            case UNOPENED:
                mBtnLeftTop.setVisibility(View.VISIBLE);
                mBtnLeftBottom.setVisibility(View.INVISIBLE);
                mBtnRightTop.setVisibility(View.VISIBLE);
                mBtnRightBottom.setVisibility(View.VISIBLE);

                mBtnLeftTop.setText(getString(R.string.terminal_button_sync));
                mBtnRightTop.setText(getString(R.string.terminal_button_open));
                mBtnRightBottom.setText(getString(R.string.terminal_button_video));
                break;
            case CANCELED:
                break;
            case STOPPED:
                mBtnRightTop.setVisibility(View.VISIBLE);

                mBtnRightTop.setText(getString(R.string.terminal_button_sync));
                break;
        }
    }

    private LinearLayout setTerminalInfo(TerminalApply apply) {
        if (null == apply) {
            LinearLayout terminalCategory = (LinearLayout) mInflater.inflate(R.layout.after_sale_detail_category, null);
            mCategoryContainer.addView(terminalCategory);
            return terminalCategory;
        }
        LinkedHashMap<String, String> pairs = new LinkedHashMap<String, String>();
        String[] keys = getResources().getStringArray(R.array.terminal_apply_keys);
        pairs.put(keys[0], apply.getTerminalNum());
        pairs.put(keys[1], apply.getBrandName());
        pairs.put(keys[2], apply.getModelNumber());
        pairs.put(keys[3], apply.getFactorName());
        pairs.put(keys[4], apply.getTitle());
        pairs.put(keys[5], apply.getPhone());
        pairs.put(keys[6], apply.getOrderNumber());
        pairs.put(keys[7], apply.getCreatedAt());
        return renderCategoryTemplate(R.string.terminal_category_apply, pairs);
    }

    private void addRatesTable(LinearLayout category, List<TerminalRate> rates) {
        LinearLayout header = (LinearLayout) mInflater.inflate(R.layout.terminal_rates_header, null);
        category.addView(header);
        for (TerminalRate rate : rates) {
            LinearLayout column = (LinearLayout) mInflater.inflate(R.layout.terminal_rates_column, null);
            TextView typeTv = (TextView) column.findViewById(R.id.terminal_column_type);
            TextView rateTv = (TextView) column.findViewById(R.id.terminal_column_rate);
            TextView statusTv = (TextView) column.findViewById(R.id.terminal_column_status);
            String[] status = getResources().getStringArray(R.array.terminal_status);
            typeTv.setText(rate.getType());
            rateTv.setText(rate.getBaseRate() + getString(R.string.notation_percent));
            statusTv.setText(status[rate.getStatus()]);
            category.addView(column);
        }
    }

    private LinearLayout setOpenInfo() {
        LinkedHashMap<String, String> pairs = new LinkedHashMap<String, String>();
        String[] keys = getResources().getStringArray(R.array.terminal_open_keys);
        pairs.put(keys[0], "");
        pairs.put(keys[1], "");
        pairs.put(keys[2], "");
        pairs.put(keys[3], "");
        return renderCategoryTemplate(R.string.terminal_category_open, pairs);
    }

    private void addComments(List<TerminalComment> comments) {
        if (null != comments && comments.size() > 0) {
            for (TerminalComment comment : comments) {
                if (null == comment) continue;
                LinearLayout commentLayout = (LinearLayout) mInflater.inflate(R.layout.after_sale_detail_comment, null);
                TextView content = (TextView) commentLayout.findViewById(R.id.comment_content);
                TextView person = (TextView) commentLayout.findViewById(R.id.comment_person);
                TextView time = (TextView) commentLayout.findViewById(R.id.comment_time);
                content.setText(comment.getContent());
                person.setText(comment.getName());
                time.setText(comment.getCreateAt());
                mCommentContainer.addView(commentLayout);
            }
        }
    }

    private TextView createKeyText() {
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.RIGHT);
        tv.setPadding(0, 5, 0, 5);
        tv.setTextColor(getResources().getColor(R.color.text6c6c6c6));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        return tv;
    }

    private TextView createValueText() {
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.LEFT);
        tv.setPadding(0, 5, 0, 5);
        tv.setTextColor(getResources().getColor(R.color.text6c6c6c6));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        return tv;
    }

    private LinearLayout renderCategoryTemplate(int titleRes, LinkedHashMap<String, String> pairs) {
        LinearLayout terminalCategory = (LinearLayout) mInflater.inflate(R.layout.after_sale_detail_category, null);
        mCategoryContainer.addView(terminalCategory);

        TextView title = (TextView) terminalCategory.findViewById(R.id.category_title);
        LinearLayout keyContainer = (LinearLayout) terminalCategory.findViewById(R.id.category_key_container);
        LinearLayout valueContainer = (LinearLayout) terminalCategory.findViewById(R.id.category_value_container);

        title.setText(getString(titleRes));
        for (Map.Entry<String, String> pair : pairs.entrySet()) {
            TextView key = createKeyText();
            key.setText(pair.getKey());
            keyContainer.addView(key);

            TextView value = createValueText();
            value.setText(pair.getValue());
            valueContainer.addView(value);
        }
        return terminalCategory;
    }

}
