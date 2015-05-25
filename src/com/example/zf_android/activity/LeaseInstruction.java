package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.GoodinfoEntity;

/**
 * 
*    
* 类名称：LeaseInstruction   
* 类描述：   租赁说明
* 创建人： ljp 
* 创建时间：2015-3-11 下午3:55:56   
* @version    
*
 */
public class LeaseInstruction extends BaseActivity{
	public GoodinfoEntity gfe=new GoodinfoEntity();
	private TextView tv1,tv2,tv3,tv4,tv5;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.act_leas_in);
			gfe=MyApplication.getGfe();
			System.out.println("````"+gfe.getId());
			tv1=(TextView) findViewById(R.id.tv1);
			tv2=(TextView) findViewById(R.id.tv2);
			tv3=(TextView) findViewById(R.id.tv3);
			tv4=(TextView) findViewById(R.id.tv4);
			tv5=(TextView) findViewById(R.id.tv5);
			
			tv5.setText(gfe.getLease_agreement()+"");
			tv4.setText(gfe.getDescription()+"");
			tv3.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_price()));
			tv2.setText(gfe.getReturn_time()+"");
			tv1.setText(gfe.getLease_time()+"");
			new TitleMenuUtil(LeaseInstruction.this, "租赁说明").show();
		}
}
