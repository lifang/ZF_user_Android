package com.example.zf_android;

import com.example.zf_android.activity.LoginActivity;
import com.example.zf_android.activity.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Welcome extends Activity {
	private SharedPreferences mySharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		Boolean  islogin=mySharedPreferences.getBoolean("islogin", false);
		int id = mySharedPreferences.getInt("id", 0);
		if(islogin && id != 0){
			Intent i = new Intent(getApplicationContext(), Main.class);
			MyApplication.getInstance().setCustomerId(id);
			startActivity(i);
			finish();
		}else{
			Intent i =new Intent(Welcome.this,LoginActivity.class);
			startActivity(i);
			finish();
		}
	}

 
}
