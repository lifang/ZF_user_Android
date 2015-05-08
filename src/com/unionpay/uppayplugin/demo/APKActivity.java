package com.unionpay.uppayplugin.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.example.zf_android.activity.PayFromCar;
import com.unionpay.UPPayAssistEx;

public class APKActivity extends BaseActivity {

	private String apkTn = null;
	private String apkMode = null;
	private boolean IS_PLUGIN_NOT_INSTALLED = false;
	
    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        // mMode参数解释：
        // 0 - 启动银联正式环境
        // 1 - 连接银联测试环境
    	apkTn = tn;
    	apkMode = mode;
        int ret = UPPayAssistEx.startPay(this, null, null, tn, mode);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
        	IS_PLUGIN_NOT_INSTALLED = true;
            // 需要重新安装控件
            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(APKActivity.this);
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent();
                	        intent.setClass(APKActivity.this, PayFromCar.class);
                	        intent.putExtra("orderId", orderId);
                	        startActivity(intent);
                	        finish();
                        }
                    });
            builder.create().show();

        }
        Log.e(LOG_TAG, "" + ret);
    }

    //安装完银行插件回来继续支付
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	if(IS_PLUGIN_NOT_INSTALLED){
    		doStartUnionPayPlugin(this, apkTn, apkMode);
    		IS_PLUGIN_NOT_INSTALLED = false;
    	}
    	super.onResume();
    }
    
}
