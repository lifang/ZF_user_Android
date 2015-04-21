package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.entity.FactoryEntity;

public class FactoryInfo extends BaseActivity {

	private static FactoryEntity factory = new FactoryEntity();
	private ImageView img;
	private TextView nameText;
	private TextView contentText;
	
	public static FactoryEntity getFactory() {
		return factory;
	}
	public static void setFactory(FactoryEntity factory) {
		FactoryInfo.factory = factory;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_factory_info);
		new TitleMenuUtil(FactoryInfo.this, "厂家信息").show();
		//factory = (FactoryEntity)this.getIntent().getSerializableExtra("factory");
		initView();
	}
	private void initView(){
		img = (ImageView)findViewById(R.id.img);
		nameText = (TextView)findViewById(R.id.name_text);
		contentText = (TextView)findViewById(R.id.content_text);
		if(factory != null){
			if(factory.getLogo_file_path() != null){
				ImageCacheUtil.IMAGE_CACHE.get(factory.getLogo_file_path(),
						img);
			}
			nameText.setText(factory.getName());
			contentText.setText(factory.getDescription());
		}
	}

}
