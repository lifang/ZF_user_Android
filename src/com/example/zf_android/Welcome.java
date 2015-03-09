package com.example.zf_android;

import com.example.zf_android.activity.LoginActivity;
import com.example.zf_android.activity.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		Intent i =new Intent(Welcome.this,Main.class);
		startActivity(i);
		finish();
	}

 
}
