package com.tongyuan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.tongyuan.R;
import com.tongyuan.adapter.MyGridViewAdapter;

public class MyActivity extends Activity {
	private ImageView imageView;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		imageView = (ImageView) this.findViewById(R.id.my_image);
		gridView = (GridView) this.findViewById(R.id.my_grid);
		gridView.setAdapter(new MyGridViewAdapter(this));
	}
}




