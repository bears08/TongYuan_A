package com.tongyuan.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tongyuan.R;
import com.tongyuan.adapter.YSGridViewAdapter;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class YangShengActivity extends Activity {

	private ViewPager viewPager;
	int pics[] = { R.drawable.meishi01, R.drawable.meishi02,
			R.drawable.meishi03, R.drawable.meishi04, R.drawable.meishi05,
			R.drawable.meishi06 };
	private List<ImageView> imageViews;// 滑动的图片集合

	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	private int currentItem = 0; // 当前图片的索引号
	// ///////////////////////////////////////
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yangsheng);
		viewPager = (ViewPager) this.findViewById(R.id.viewpage2);
		gridView = (GridView) this.findViewById(R.id.ys_grid);

		imageViews = new ArrayList<ImageView>();

		for (int i = 0; i < pics.length; i++) {
			ImageView img = new ImageView(this);
			img.setImageResource(pics[i]);
			imageViews.add(img);
		}
		viewPager.setAdapter(new YangShengAdapter());
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);

		gridView.setAdapter(new YSGridViewAdapter(this));
	}

	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
		};
	};

	private class YangShengAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pics.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageView iv = imageViews.get(position);
			((ViewPager) container).addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

	}

	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
		}

	}
}
