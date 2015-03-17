package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_zandroid.adapter.CommentAdapter;
import com.example.zf_zandroid.adapter.OrderDetail_PosAdapter;

/**
 * 
*    
* 类名称：Comment   
* 类描述：   评价
* 创建人： ljp 
* 创建时间：2015-3-16 下午3:12:02   
* @version    
*
 */
public class Comment extends BaseActivity{
	private ScrollViewWithListView lv;
	private Button btn_pay;
	private TextView next_sure;
	
	private CommentAdapter posAdapter;
	List<Goodlist>  goodlist = new ArrayList<Goodlist>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		lv=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		goodlist=MyApplication.getGoodlist();
		posAdapter=new CommentAdapter(Comment.this,goodlist);
		lv.setAdapter(posAdapter);
		
		next_sure=(TextView) findViewById(R.id.next_sure);
		new TitleMenuUtil(Comment.this, "评价").show();
		next_sure.setText("提交");
		next_sure.setVisibility(View.VISIBLE);
		btn_pay=(Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				submit();
			}

		 
		});
	}
	public void submit(){
		for(int i=0;i<goodlist.size();i++){
			
			goodlist.get(i).getContent();
			System.out.println(goodlist.get(i).getScore()+"---submit---"+goodlist.get(i).getContent());
		}
		
	}
}
