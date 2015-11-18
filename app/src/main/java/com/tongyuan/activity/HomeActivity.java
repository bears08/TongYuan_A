package com.tongyuan.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.tongyuan.R;

@SuppressLint("ResourceAsColor")
public class HomeActivity extends BaseHeadActivity{
	/*
	 * 做好人，买好股，方能得好报
	 */
	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	TabHost tabHost = null;
	TextView t1, t2, t3;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		//设置标题栏
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		//加载布局
		super.setContentView(R.layout.activity_home);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_head_title);


		context = HomeActivity.this;
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		initTextView();
		initPagerViewer();

	}

	/**
	 * 初始化标题
	 */
	private void initTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);

		t1.setOnClickListener((OnClickListener) new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));

	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, TodayActivity.class);
		list.add(getView("A", intent));
		Intent intent2 = new Intent(context, YangShengActivity.class);
		list.add(getView("B", intent2));
		Intent intent3 = new Intent(context, MyActivity.class);
		list.add(getView("C", intent3));

		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	/**
	 * Pager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		List<View> list = new ArrayList<View>();

		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				t1.setFocusable(true);
				t2.setFocusable(false);
				t3.setFocusable(false);
//				t1.setTextColor(context.getResources().getColor(R.color.red));
//				t2.setTextColor(context.getResources().getColor(R.color.black));
//				t3.setTextColor(context.getResources().getColor(R.color.black));
				break;
			case 1:
				t1.setFocusable(false);
				t2.setFocusable(true);
				t3.setFocusable(false);
//				t1.setTextColor(context.getResources().getColor(R.color.black));
//				t2.setTextColor(context.getResources().getColor(R.color.red));
//				t3.setTextColor(context.getResources().getColor(R.color.black));
				break;
			case 2:
				t1.setFocusable(false);
				t2.setFocusable(false);
				t3.setFocusable(true);
//				t1.setTextColor(context.getResources().getColor(R.color.black));
//				t2.setTextColor(context.getResources().getColor(R.color.black));
//				t3.setTextColor(context.getResources().getColor(R.color.red));
				break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("-------------------------------Home-------Resume");
		switch (pager.getCurrentItem()) {
		case 0:
			System.out.println("-----------suansuan1");
			TodayActivity todayActivity = (TodayActivity) manager.getActivity("A");
			System.out.println("-----------suansuan2");
			todayActivity.onResume();
			break;

		default:
			break;
		}
	}
}
