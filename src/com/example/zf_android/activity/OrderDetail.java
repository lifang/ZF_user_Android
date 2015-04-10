package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.entity.MarkEntity;
import com.example.zf_android.entity.OrderDetailEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.OrderDetail_PosAdapter;
import com.example.zf_zandroid.adapter.RecordAdapter;
import com.google.gson.reflect.TypeToken;
/***
 * 
 *    
 * ����ƣ�OrderDetail   
 * ��������   ��������
 * �����ˣ� ljp 	
 * ����ʱ�䣺2015-2-5 ����3:06:02   
 * @version    
 *
 */
public class OrderDetail extends BaseActivity implements OnClickListener{
	private  ScrollViewWithListView   pos_lv,his_lv;
	List<Goodlist>  goodlist = new ArrayList<Goodlist>();
	List<MarkEntity>  relist = new ArrayList<MarkEntity>(); 
	private OrderDetail_PosAdapter posAdapter;
	private RecordAdapter reAdapter;
	private LinearLayout ll_ishow,ll_ishow2;
	private Button btn_ishow,btn_pay,btn_cancle,btn_pj;
	private List<OrderDetailEntity>  ode =new ArrayList<OrderDetailEntity>();
	private TextView tv_status,tv_sjps,tv_psf,tv_reperson,tv_tel,tv_adress,tv_ly,tv_fplx,fptt,
	tv_ddbh,tv_pay,tv_time,tv_gj,tv_money;
	private int status,id;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				OrderDetailEntity entity= ode.get(0);
				tv_sjps.setText("实际配送金额(含配送费) ：￥ "+entity.getOrder_totalprice());
				tv_psf.setText("含配送费 ：￥ "+entity.getOrder_psf());
				tv_reperson.setText("收件人  ：   "+entity.getOrder_receiver());
				tv_tel.setText(entity.getOrder_receiver_phone());
				tv_adress.setText("收货地址  ：   "+entity.getOrder_address());
				tv_ly.setText("留言  ：   "+entity.getOrder_comment());
				tv_fplx.setText(entity.getOrder_invoce_type().equals("1")?"发票类型 : 个人":"发票类型 : 公司");
				fptt.setText("发票抬头  ：   "+entity.getOrder_invoce_info());
				tv_ddbh.setText("订单编号  ：   "+entity.getOrder_number());
				tv_pay.setText("支付方式  ：   "+entity.getOrder_payment_type());
				tv_time.setText("实付金额  ：   ￥"+entity.getOrder_totalprice());
				tv_money.setText("订单日期  ：   "+entity.getOrder_createTime());
				tv_gj.setText("共计  ：   "+entity.getOrder_totalNum()+"件");

				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // ����������
				Toast.makeText(getApplicationContext(), "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(),  " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		new TitleMenuUtil(OrderDetail.this, "订单详情").show();

		status = getIntent().getExtras().getInt("status");
		id = Integer.valueOf(getIntent().getExtras().getString("id"));

		initView();
		getData();
	} 
	private void getData() {

		API.getMyOrderById(this, id, 
				new HttpCallback<List<OrderDetailEntity>>(this) {

			@Override
			public void onSuccess(List<OrderDetailEntity> data) {
				ode = data;
				goodlist= data.get(0).getOrder_goodsList() ;
				relist=data.get(0).getComments().getContent() ;
				if (goodlist.size()>0) {
					posAdapter=new OrderDetail_PosAdapter(OrderDetail.this, goodlist,status);
					pos_lv.setAdapter(posAdapter);
				}
				MyApplication.setGoodlist(goodlist);

				if (relist.size()>0) {
					reAdapter=new RecordAdapter(OrderDetail.this, relist);
					his_lv.setAdapter(reAdapter);
				}
				handler.sendEmptyMessage(0);
			}
			@Override
			public TypeToken<List<OrderDetailEntity>> getTypeToken() {
				return new TypeToken<List<OrderDetailEntity>>() {
				};
			}
		});


	}
	private void initView() {
		tv_money=(TextView) findViewById(R.id.tv_money);
		btn_pay=(Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(this);
		btn_cancle=(Button) findViewById(R.id.btn_cancle);
		btn_cancle.setOnClickListener(this);
		btn_pj=(Button) findViewById(R.id.btn_pj);
		btn_pj.setOnClickListener(this);
		ll_ishow2=(LinearLayout) findViewById(R.id.ll_ishow2);
		tv_gj=(TextView) findViewById(R.id.tv_gj);
		btn_ishow=(Button) findViewById(R.id.btn_ishow);
		btn_ishow.setOnClickListener(this);
		tv_ddbh=(TextView) findViewById(R.id.tv_ddbh);
		fptt=(TextView) findViewById(R.id.fptt);
		tv_pay=(TextView) findViewById(R.id.tv_pay);
		tv_fplx=(TextView) findViewById(R.id.tv_fplx);	
		tv_ly=(TextView) findViewById(R.id.tv_ly);			
		tv_adress=(TextView) findViewById(R.id.tv_adress);
		tv_reperson=(TextView) findViewById(R.id.tv_reperson);
		tv_tel=(TextView) findViewById(R.id.tv_tel);
		tv_time=(TextView) findViewById(R.id.tv_time);
		tv_psf=(TextView) findViewById(R.id.tv_psf);
		tv_sjps=(TextView) findViewById(R.id.tv_sjps);
		tv_status=(TextView) findViewById(R.id.tv_status);
		ll_ishow=(LinearLayout) findViewById(R.id.ll_ishow);
		//			ll_ishow.setVisibility(status==1 ? View.INVISIBLE
		//					: View.VISIBLE); // 只有状态是1 才有下面的按钮
		pos_lv=(ScrollViewWithListView) findViewById(R.id.pos_lv1);


		his_lv=(ScrollViewWithListView) findViewById(R.id.his_lv);

		switch (status) {
		case 1:
			tv_status.setText("未付款");
			ll_ishow.setVisibility(View.VISIBLE);
			ll_ishow2.setVisibility(View.GONE);
			break;
		case 2:
			tv_status.setText("已付款");
			ll_ishow.setVisibility(View.GONE);
			ll_ishow2.setVisibility(View.VISIBLE);
			break;
		case 3:
			tv_status.setText("已发货");
			ll_ishow2.setVisibility(View.VISIBLE);
			break;
		case 4:
			tv_status.setText("已评价");

			break;
		case 5:
			tv_status.setText("已取消");

			break;
		case 6:
			tv_status.setText("交易关闭");

			break;

		default:
			break;
		}
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_ishow:
			Toast.makeText(getApplicationContext(), "请先付款···", 1000).show();
			break;
		case R.id.btn_pay:

			Intent i = new Intent(getApplicationContext(),PayFromCar.class);
			startActivity(i);
			break;
		case R.id.btn_cancle:
			API.cancelMyOrder(this,id,new HttpCallback(this) {
				@Override
				public void onSuccess(Object data) {
					Toast.makeText(getApplicationContext(), "取消成功", 1000).show();
					Intent intent = new Intent();
					setResult(Activity.RESULT_OK, intent);
					finish();
				}
				@Override
				public TypeToken getTypeToken() {
					return null;
				}
			});
			break;
		case R.id.btn_pj:
			//Comment
			Intent btn_pj = new Intent(getApplicationContext(),Comment.class);
			Bundle bundle = new Bundle();
			bundle.putInt("id", id);
			btn_pj.putExtras(bundle);
			startActivityForResult(btn_pj, 101);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 101) {
				ode.clear();
				relist.clear();
				goodlist.clear();
				getData();
			}
		}
	}
}
