package com.tongyuan.activity;

import java.util.Map;

import com.tongyuan.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends Activity {
	private ImageView imageView;
	private TextView t1,t2,t3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		imageView = (ImageView) findViewById(R.id.image);
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		
		imageView.setBackgroundResource(R.drawable.xigua);
		t1.setText("酸酸");
		t2.setText("甜甜");
		t1.setText("不错");
//		Intent intent = getIntent();
//		Bundle bundle = intent.getExtras();
//		int position = bundle.getInt(TodayActivity2.POSITION);
//		Map<String, Object> map = TodayActivity2.getmData().get(position);
//		imageView.setBackgroundResource((Integer) map.get("img"));
//		t1.setText((String) map.get("title"));
//		t2.setText((String) map.get("info"));
	}
}
