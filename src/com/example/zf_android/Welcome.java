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
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
	 
		
		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		Boolean  islogin=mySharedPreferences.getBoolean("islogin", false);
		 
		if(islogin){
			Intent i = new Intent(getApplicationContext(), Main.class);
			startActivity(i);
			finish();
		}else{
			Intent i =new Intent(Welcome.this,LoginActivity.class);
			startActivity(i);
			finish();
		}
	}

 
}
