package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.ApplyneedEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_zandroid.adapter.AppleNeedAdapter;

/***
 * 
*    
* 类名称：ApplyNeed   
* 类描述：   开通所需材料
* 创建人： ljp 
* 创建时间：2015-3-11 下午3:13:43   
* @version    
*
 */
public class ApplyNeed extends BaseActivity{
	private AppleNeedAdapter myAdapter,myAdapter2;
	private ScrollViewWithListView   pos_lv1,pos_lv2;
	List<ApplyneedEntity>  pubList = new ArrayList<ApplyneedEntity>();
	List<ApplyneedEntity>  singleList = new ArrayList<ApplyneedEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_need);
		new TitleMenuUtil(ApplyNeed.this, "申请开通所需材料").show();
		pos_lv1=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		pos_lv2=(ScrollViewWithListView) findViewById(R.id.pos_lv2);
		pubList=MyApplication.getPub();
		singleList=MyApplication.getSingle();
	 
		System.out.println("`pubList.size()``"+pubList.size());
		myAdapter=new AppleNeedAdapter(ApplyNeed.this, pubList);
		myAdapter2=new AppleNeedAdapter(ApplyNeed.this, singleList);
		pos_lv1.setAdapter(myAdapter);
		pos_lv2.setAdapter(myAdapter2);
		
	}
}
