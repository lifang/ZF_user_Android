package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.ChanelEntitiy;
import com.example.zf_zandroid.adapter.HuilvAdapter;
import com.example.zf_zandroid.adapter.HuilvAdapter1;
import com.example.zf_zandroid.adapter.HuilvAdapter2;
import com.example.zf_zandroid.adapter.JiaoyiHuilvAdapter;
/**
 * 
*    
* 类名称：TradeRate   
* 类描述：   交易费率
* 创建人： ljp 
* 创建时间：2015-3-11 下午2:38:35   
* @version    
*
 */
public class TradeRate extends BaseActivity{
	private ScrollViewWithListView lv;
	private JiaoyiHuilvAdapter myAdapter;
	private List<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	
	private ScrollViewWithListView  pos_lv1,pos_lv2,pos_lv3;
	private HuilvAdapter lvAdapter;
	private HuilvAdapter1 lvAdapter2;
	private HuilvAdapter2 lvAdapter3;
	private ArrayList<ChanelEntitiy> celist1 = new ArrayList<ChanelEntitiy>();
	private ArrayList<ChanelEntitiy> celist2 = new ArrayList<ChanelEntitiy>();
	private ArrayList<ChanelEntitiy> celist3 = new ArrayList<ChanelEntitiy>();
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.act_tradereate);
		
		celist1 = (ArrayList<ChanelEntitiy>) getIntent().getExtras().getSerializable("celist1");
		celist2 = (ArrayList<ChanelEntitiy>) getIntent().getExtras().getSerializable("celist2");
		celist3 = (ArrayList<ChanelEntitiy>) getIntent().getExtras().getSerializable("celist3");
		
		lv=(ScrollViewWithListView) findViewById(R.id.lv);
		new TitleMenuUtil(TradeRate.this, "交易费率").show();
		ChanelEntitiy ce=new ChanelEntitiy();
		ce.setName("结算时间");
		ce.setService_rate( 10000);
		 
		celist.addAll(MyApplication.getCelist() );
		myAdapter =new  JiaoyiHuilvAdapter(TradeRate.this,celist);
		lv.setAdapter(myAdapter);
		//celist=MyApplication.getCelist();
		initView();

	}
	private void initView() {
		pos_lv1=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		pos_lv2=(ScrollViewWithListView) findViewById(R.id.pos_lv2);
		pos_lv3=(ScrollViewWithListView) findViewById(R.id.pos_lv3);
		
		lvAdapter=new HuilvAdapter(TradeRate.this, celist1);
		pos_lv1.setAdapter(lvAdapter);
		lvAdapter2=new HuilvAdapter1(TradeRate.this, celist2);
		pos_lv2.setAdapter(lvAdapter2);
		lvAdapter3=new HuilvAdapter2(TradeRate.this, celist3);
		pos_lv3.setAdapter(lvAdapter3);
	}
}
