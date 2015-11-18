package com.tongyuan.activity;

import com.tongyuan.R;
import com.tongyuan.adapter.ShopCarAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class ShopCarActivity extends Activity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题栏
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_shop_car);
		// 加载布局
//		this.setContentView(R.layout.activity_base);
		listView = (ListView) findViewById(R.id.listview_shop_car);
		listView.setAdapter(new ShopCarAdapter(this.getApplicationContext()));
	}
}
