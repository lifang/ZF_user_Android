package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.Answer;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.CommentAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	private int id;
	//Answer
	List<Answer>  as = new ArrayList<Answer>();
	private CommentAdapter posAdapter;
	List<Goodlist>  goodlist = new ArrayList<Goodlist>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		lv=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		goodlist=MyApplication.getGoodlist();

		id = getIntent().getExtras().getInt("id");

		posAdapter=new CommentAdapter(Comment.this,goodlist,new OnClickListen());
		lv.setAdapter(posAdapter);

		new TitleMenuUtil(Comment.this, "评价").show();

		btn_pay=(Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				submit();
			}

		});
	}
	private class OnClickListen implements CommentAdapter.AfterTextChangedListener{

		@Override
		public void onAfterTextChanged(int position) {
			posAdapter.notifyDataSetChanged();
		}
		
	}
	public void submit(){
		for(int i=0;i<goodlist.size();i++){
			Answer answer=new Answer();
			answer.setContent(goodlist.get(i).getContent());
			answer.setCustomer_id(MyApplication.getInstance().getCustomerId());
			
			if (!StringUtil.isNull(goodlist.get(i).getGood_id())) 
				answer.setGood_id(Integer.parseInt( goodlist.get(i).getGood_id()));
			
			answer.setScore(Integer.parseInt( goodlist.get(i).getScore()));
			as.add(answer);
			System.out.println(goodlist.get(i).getScore()+"---submit---"+goodlist.get(i).getContent()+"id-"+goodlist.get(i).getGood_id());
		}

		Gson gson = new Gson();

		try {
			API.batchSaveComment(this,id,new JSONArray(gson.toJson(as)),
					new HttpCallback(this) {
				@Override
				public void onSuccess(Object data) {
					Toast.makeText(getApplicationContext(), "评论成功", 1000).show();
					Intent intent = new Intent();
					setResult(Activity.RESULT_OK, intent);
					finish();
				}
				@Override
				public TypeToken getTypeToken() {
					return null;
				}

			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
