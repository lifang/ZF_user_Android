package com.example.zf_android.activity;
 
import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.CitySelectActivity;
import com.example.zf_android.trade.TradeFlowActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Main extends BaseActivity implements OnClickListener{

	private RelativeLayout  main_rl_pos,main_rl_renzhen,main_rl_zdgl,main_rl_jyls,
	main_rl_Forum,main_rl_wylc,main_rl_xtgg,main_rl_lxwm,main_rl_my,main_rl_pos1,main_rl_gwc;
	private ImageView testbutton;

    private View citySelect;
    private TextView cityTextView;
    private int cityId;
    private String cityName;
    public static final int REQUEST_CITY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		testbutton=(ImageView) findViewById(R.id.testbutton);
		testbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i =new Intent(Main.this,LoginActivity.class);
					 startActivity(i);
			}
		});
		System.out.println("-----");
		getdata();
	}

	private void getdata() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String url="https://114.215.149.242:18080/ZFMerchant/api/customers/getOne/8";
		System.out.println("-url---"+url);
		MyApplication.getInstance().getClient().get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) { 
				System.out.println("-onSuccess---");
				String responseMsg = new String(responseBody).toString();
				Log.e("LJP", responseMsg);
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				System.out.println("-onFailure---");
			}
		});
	
	}

	private void initView() {
		// TODO Auto-generated method stub
        citySelect = findViewById(R.id.titleback_linear_back);
        cityTextView = (TextView) findViewById(R.id.tv_city);
        citySelect.setOnClickListener(this);
        main_rl_gwc=(RelativeLayout) findViewById(R.id.main_rl_gwc);
        main_rl_gwc.setOnClickListener(this);
		main_rl_pos=(RelativeLayout) findViewById(R.id.main_rl_pos);
		main_rl_pos.setOnClickListener(this);
		main_rl_renzhen=(RelativeLayout) findViewById(R.id.main_rl_renzhen);
		main_rl_renzhen.setOnClickListener(this);
		main_rl_zdgl=(RelativeLayout) findViewById(R.id.main_rl_zdgl);
		main_rl_zdgl.setOnClickListener(this);
		main_rl_jyls=(RelativeLayout) findViewById(R.id.main_rl_jyls);
		main_rl_jyls.setOnClickListener(this);
		main_rl_Forum=(RelativeLayout) findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_wylc=(RelativeLayout) findViewById(R.id.main_rl_wylc);
		main_rl_wylc.setOnClickListener(this);
		main_rl_lxwm=(RelativeLayout) findViewById(R.id.main_rl_lxwm);
		main_rl_lxwm.setOnClickListener(this);
		main_rl_xtgg=(RelativeLayout) findViewById(R.id.main_rl_xtgg);
		main_rl_xtgg.setOnClickListener(this);
		main_rl_my=(RelativeLayout) findViewById(R.id.main_rl_my);
		main_rl_my.setOnClickListener(this);
		main_rl_pos1=(RelativeLayout) findViewById(R.id.main_rl_pos1);
		main_rl_pos1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        case R.id.titleback_linear_back: // 选择城市
            Intent intent = new Intent(Main.this, CitySelectActivity.class);
            intent.putExtra(CitySelectActivity.CITY_NAME, cityTextView.getText().toString());
            startActivityForResult(intent, REQUEST_CITY);
            break;

		case R.id.main_rl_pos1:  // 买POS机器
			 startActivity(new Intent(Main.this,MyMessage.class));
			 
			break;
		case R.id.main_rl_my:  // 买POS机器
			 startActivity(new Intent(Main.this,MenuMine.class));
			 
			break;
		
		case R.id.main_rl_pos:  // 买POS机器
			 startActivity(new Intent(Main.this,PosListActivity.class));
			 
			break;
			
		case R.id.main_rl_renzhen:  //认证
			 Intent i =new Intent(Main.this,OrderList.class);
			 startActivity(i);
			 
			break;
		case R.id.main_rl_zdgl: //终端管理 
			 
			break;
		case R.id.main_rl_jyls: //交易流水 
			 
			startActivity(new Intent(Main.this, TradeFlowActivity.class));
			break;
		case R.id.main_rl_Forum: //我要贷款   
			 
			break;
		case R.id.main_rl_xtgg: //系统公告   
			  
			 startActivity(new Intent(Main.this,SystemMessage.class));
			 
			break;
		case R.id.main_rl_lxwm: //联系我们
			 startActivity(new Intent(Main.this,ContentUs.class));
		case R.id.main_rl_gwc:  
			 startActivity(new Intent(Main.this,ShopCar.class));
			break;
		default:
			break;
		}
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CITY:
                cityId = data.getIntExtra(CitySelectActivity.CITY_ID, 0);
                cityName = data.getStringExtra(CitySelectActivity.CITY_NAME);
                cityTextView.setText(cityName);
                break;
        }
    }
}
