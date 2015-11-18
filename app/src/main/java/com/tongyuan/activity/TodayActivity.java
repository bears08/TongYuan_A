package com.tongyuan.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongyuan.R;
import com.tongyuan.dao.ShopCarDao;
import com.tongyuan.domain.ShopCar;
import com.tongyuan.image.AsyncImageLoader;
import com.tongyuan.util.image.ImageLoaderFactory;

public class TodayActivity extends Activity {

	public static final String action = "jason.broadcast.action";
	// /////////////////////ListView////////////////
	private List<Map<String, Object>> mData;
	private static String NUMBER = "number";
	private TodayAdapter adapter;
	private ListView listView;
	private ProgressDialog progressDialog;
	private int current = 0;
	private int count = 10;
	private String url = "suansuan";

	// /////////////////////ViewPager////////////////
	private ViewPager viewPager;
	int pics[] = { R.drawable.suancaiyu, R.drawable.xigua, R.drawable.yangtang,
			R.drawable.doufu };
	private List<ImageView> imageViews;// 滑动的图片集合

	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	private int currentItem = 0; // 当前图片的索引号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		listView = (ListView) this.findViewById(R.id.listview);
		progressDialog = new ProgressDialog(this);
		new TodayTask().execute(url);

		// ///////////////////////////ViewPager////////////////////
		viewPager = (ViewPager) this.findViewById(R.id.viewpage2);
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

	}

	public class TodayTask extends
			AsyncTask<String, Void, List<Map<String, Object>>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... arg0) {
			try {
				System.out.println(arg0);
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getData();
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (mData == null) {
				mData = result;
				adapter = new TodayAdapter(TodayActivity.this);
				listView.setAdapter(adapter);
			} else {
				mData.addAll(result);
			}
			listView.setOnScrollListener(new OnScrollListener() {
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					/**
					 * firstVisibleItem
					 * 表示在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始）
					 * visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
					 * totalItemCount表示ListView的ListItem总数
					 * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem
					 * (最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）
					 */

				}

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					/**
					 * scrollState有三种状态，分别是SCROLL_STATE_IDLE、
					 * SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
					 * SCROLL_STATE_IDLE是当屏幕停止滚动时
					 * SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时（The
					 * user is scrolling using touch, and their finger is still
					 * on the screen）
					 * SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时（The user
					 * had previously been scrolling using touch and had
					 * performed a fling）
					 */
					switch (scrollState) {
					case OnScrollListener.SCROLL_STATE_IDLE:
						if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
							// new TodayTask().execute("tiantian");
						}
						break;
					case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
						break;
					case OnScrollListener.SCROLL_STATE_FLING:
						break;
					default:
						break;
					}
				}
			});
			adapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}

		private List<Map<String, Object>> getData() {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productid", 1);
			map.put("name", "麻辣豆腐");
			map.put("price", 8.20);
			map.put("info", "豆类补肾");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/c/e/6/220_ce3f53dbf8b0858e2d199edf89f564a6.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 2);
			map.put("name", "羊肉串");
			map.put("price", 18.80);
			map.put("info", "羊肉补益阳气");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/1/9/3/220_1942de4ded23a5e4f7ac96a1ce552b73.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 3);
			map.put("name", "茄子肉沫");
			map.put("price", 9.80);
			map.put("info", "茄子多子，补肾");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/5/9/b/220_592cce76981298bc812f4a273d4691bb.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 4);
			map.put("name", "排骨莲藕");
			map.put("price", 15.80);
			map.put("info", "莲藕补肺气");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/c/f/1/220_cfb73424a42d37ab9535fa159d0bafb1.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 5);
			map.put("name", "水煮花生");
			map.put("price", 6.00);
			map.put("info", "花生补血补气");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/d/d/9/220_dd575450344b29e9b5314733be24c019.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 6);
			map.put("name", "糖醋排骨");
			map.put("price", 16.80);
			map.put("info", "红糖补血，排骨补肾，醋主酸，补肝");
			map.put("img",
					"http://zt1.douguo.net/upload/post/b/1/6/b11ba6d18e1bff227e19a08890dccd16.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 7);
			map.put("name", "肉沫海参");
			map.put("price", 23.80);
			map.put("info", "肉沫补脾，海参补肾");
			map.put("img", "http://i1.douguo.net/upload/banners/1443403123.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 8);
			map.put("name", "南瓜汤");
			map.put("price", 2.90);
			map.put("info", "南瓜黄色补脾");
			map.put("img", "http://i1.douguo.net/upload/banners/1443431898.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 9);
			map.put("name", "菠菜丸子");
			map.put("price", 11.80);
			map.put("info", "菠菜补肝，丸子补肾");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/2/1/0/220_21ac74abaa460c96e3f9b4e2bc2b9de0.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 10);
			map.put("name", "家常豆腐");
			map.put("price", 7.20);
			map.put("info", "豆腐补肾");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/5/6/4/220_56ac24ef47a4231bd410d8b3cb827e04.jpg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("productid", 11);
			map.put("name", "甜点");
			map.put("price", 8.80);
			map.put("info", "甜点补脾");
			map.put("img",
					"http://cp1.douguo.net/upload/caiku/7/b/d/220_7b31b4b30bb7f794ff838a1478ce60ed.jpg");
			list.add(map);
			return list;
		}

	}

	public class TodayAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private AsyncImageLoader imageLoader;
		private ImageLoaderFactory factory;
		private ShopCarDao shopCarDao;
		private Map<String, Integer> nums = new HashMap<String, Integer>();
		private static final String NUM = "nums";
		private static final String PRODUCTID = "productid";
		private static final String NAME = "name";
		private static final String INFO = "info";
		private static final String PRICE = "price";
		private static final String IMG = "img";

		public TodayAdapter(Context context) {
			factory = new ImageLoaderFactory(context);
			this.mInflater = LayoutInflater.from(context);
			this.shopCarDao = new ShopCarDao(context);
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			final int pos = position;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.today_listview, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.price = (TextView) convertView.findViewById(R.id.price);
				holder.num = (TextView) convertView.findViewById(R.id.num);
				holder.jia = (ImageView) convertView.findViewById(R.id.jia);
				holder.jian = (ImageView) convertView.findViewById(R.id.jian);
				holder.productid = (TextView) convertView
						.findViewById(R.id.product_id);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText((String) mData.get(position).get(NAME));
			holder.price.setText("" + mData.get(position).get(PRICE));
			holder.productid.setText("" + mData.get(position).get(PRODUCTID));
			if (mData.get(position).get(NUM) == null) {
				mData.get(position).put(NUM, 0);
			}

			ShopCar shopCar = shopCarDao.selectByProductId((Integer) mData.get(
					position).get(PRODUCTID));
			int ns = 0;
			if (shopCar != null && shopCar.getProductNum() > 0) {
				ns = shopCar.getProductNum();
			}
			holder.num.setText("" + ns);
			if (ns > 0) {
				holder.num.setVisibility(View.VISIBLE);
				holder.jian.setVisibility(View.VISIBLE);
			} else {
				holder.num.setVisibility(View.INVISIBLE);
				holder.jian.setVisibility(View.INVISIBLE);
			}
			holder.jia.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					String s = (String) holder.num.getText();
					int n = 0;
					if (s != null) {
						n = Integer.parseInt(s);
					}
					n++;
					holder.num.setText("" + n);
					ShopCar shopCar = new ShopCar();
					shopCar.setProductId((Integer) mData.get(pos)
							.get(PRODUCTID));
					shopCar.setProductNum(1);
					shopCar.setName((String) mData.get(pos).get(NAME));
					shopCar.setPrice((Double) mData.get(pos).get(PRICE));
					shopCar.setSmallPic((String) mData.get(pos).get(IMG));

					if (n > 0) {
						holder.num.setVisibility(View.VISIBLE);
						holder.jian.setVisibility(View.VISIBLE);
					}
					Intent intent = new Intent(action);
					Bundle bundle = new Bundle();
					bundle.putSerializable("shopCar", shopCar);
					intent.putExtras(bundle);

					sendBroadcast(intent);
					// adapter.notifyDataSetChanged();
				}
			});
			holder.jian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					String s = (String) holder.num.getText();
					int n = 0;
					if (s != null) {
						n = Integer.parseInt(s);
					}
					n--;
					holder.num.setText("" + n);
					ShopCar shopCar = new ShopCar();
					shopCar.setProductId((Integer) mData.get(pos)
							.get(PRODUCTID));
					shopCar.setProductNum(-1);
					shopCar.setName((String) mData.get(pos).get(NAME));
					shopCar.setPrice((Double) mData.get(pos).get(PRICE));
					shopCar.setSmallPic((String) mData.get(pos).get(IMG));
					if (n <= 0) {
						holder.num.setVisibility(View.INVISIBLE);
						holder.jian.setVisibility(View.INVISIBLE);
					}
					Intent intent = new Intent(action);
					Bundle bundle = new Bundle();
					bundle.putSerializable("shopCar", shopCar);
					intent.putExtras(bundle);
					sendBroadcast(intent);
				}
			});
			factory.displayImage((String) mData.get(position).get("img"),
					holder.img);
			// 接口回调的方法，完成图片的读取;

			// DownImage downImage = new DownImage(
			// "http://img10.360buyimg.com/da/jfs/t2011/356/286237450/102146/3c6632ba/55fbbfcfNbd16bbd7.jpg");
			//
			// downImage.loadImage(new ImageCallBack() {
			//
			// @Override
			// public void getDrawable(Drawable drawable) {
			// holder.img.setImageDrawable(drawable);
			//
			// }
			//
			// });
			// Bitmap bitmap = imageLoader.loadImage(holder.img,
			// "http://img10.360buyimg.com/da/jfs/t2011/356/286237450/102146/3c6632ba/55fbbfcfNbd16bbd7.jpg");
			// if(bitmap != null){
			// holder.img.setImageBitmap(bitmap);
			// }

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					Intent intent = new Intent(TodayActivity.this,
							ProductActivity.class);
					startActivity(intent);
				}
			});
			return convertView;

		}

		private class ViewHolder {
			public ImageView img;
			public TextView name;
			public TextView price;
			public TextView info;
			public TextView num;
			public ImageView jia;
			public ImageView jian;
			public TextView productid;
		}
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

	@Override
	protected void onResume() {
		System.out.println("---------today----onResume");
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	@Override
	protected void onRestart() {
		System.out.println("-------today------onRestart");
		adapter.notifyDataSetChanged();
	}

}
