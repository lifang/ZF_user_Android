package com.example.zf_android;

 

import com.examlpe.zf_android.util.ClientUpdate;

import android.app.Activity;
import android.os.Bundle;
/***
 * 
*    
* 类名称：CancleUpdate   
* 类描述：   取消下载页面 ，切入APP然后消失，显示原来停留的页面
* 创建人： ljp 
*  
* @version    
*
 */
 

public class CancleUpdate extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		String str = getIntent().getStringExtra("quxiao");
		if(str != null && str.equals("取消")){
			ClientUpdate.mUpdate.cancel(true);
		}
		
		finish();
		super.onCreate(savedInstanceState);
	}

}
