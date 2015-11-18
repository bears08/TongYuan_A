package com.tongyuan.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tongyuan.R;
import com.tongyuan.interf.ImageCallBack;
import com.tongyuan.util.DownImage;
import com.tongyuan.util.HttpUtil;

public class TodayActivity2 extends Activity {

	public static final String POSITION = "position";
	private ListView listView;
	private static List<Map<String, Object>> mData;
	private TodayAdapter adapter;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		// mData = this.getData();
		listView = (ListView) this.findViewById(R.id.listview);
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("正在下载....");
		new MyTask().execute(HttpUtil.BASE_URL);
//		adapter = new TodayAdapter(this);
//		listView.setAdapter(adapter);
	}

	class TodayAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		private List<Map<String, Object>> list;

		public TodayAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int i = position;
			final ViewHolder holder;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.today_listview, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
//				holder.info = (TextView) convertView.findViewById(R.id.info);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));
			holder.title.setText((String) mData.get(position).get("title"));
			holder.info.setText((String) mData.get(position).get("info"));

			// 接口回调的方法，完成图片的读取;

			// DownImage downImage = new DownImage(HttpUtil.IMG_URL
			// + list.get(position).get("pic").toString() + ".jpg");
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

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					Intent intent = new Intent(TodayActivity2.this,
							ProductActivity.class);
					intent.putExtra(POSITION, i);
					startActivity(intent);
				}
			});
			return convertView;
		}

	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "酸菜鱼");
		map.put("info", "酸主肝，补肝阴");
		map.put("img", R.drawable.suancaiyu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "羊肉汤");
		map.put("info", "羊肉补益阳气");
		map.put("img", R.drawable.yangtang);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "西瓜");
		map.put("info", "西瓜消暑，补益心脏");
		map.put("img", R.drawable.xigua);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "豆腐海参");
		map.put("info", "豆腐补脾，海参补肾");
		map.put("img", R.drawable.doufu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "羊肉汤");
		map.put("info", "羊肉补益阳气");
		map.put("img", R.drawable.yangtang);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "西瓜");
		map.put("info", "西瓜消暑，补益心脏");
		map.put("img", R.drawable.xigua);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "豆腐海参");
		map.put("info", "豆腐补脾，海参补肾");
		map.put("img", R.drawable.doufu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "羊肉汤");
		map.put("info", "羊肉补益阳气");
		map.put("img", R.drawable.yangtang);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "西瓜");
		map.put("info", "西瓜消暑，补益心脏");
		map.put("img", R.drawable.xigua);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "豆腐海参");
		map.put("info", "豆腐补脾，海参补肾");
		map.put("img", R.drawable.doufu);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "羊肉汤");
		map.put("info", "羊肉补益阳气");
		map.put("img", R.drawable.yangtang);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "西瓜");
		map.put("info", "西瓜消暑，补益心脏");
		map.put("img", R.drawable.xigua);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "豆腐海参");
		map.put("info", "豆腐补脾，海参补肾");
		map.put("img", R.drawable.doufu);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "羊肉汤");
		map.put("info", "羊肉补益阳气");
		map.put("img", R.drawable.yangtang);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "西瓜");
		map.put("info", "西瓜消暑，补益心脏");
		map.put("img", R.drawable.xigua);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "豆腐海参");
		map.put("info", "豆腐补脾，海参补肾");
		map.put("img", R.drawable.doufu);
		list.add(map);

		return list;
	}

	public static List<Map<String, Object>> getmData() {
		return mData;
	}

	public void setmData(List<Map<String, Object>> mData) {
		this.mData = mData;
	}

	class MyTask extends AsyncTask<String, Void, List<Map<String, Object>>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();

		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {

			super.onPostExecute(result);

			// adapter.setData(result);
			mData = getData();
			adapter = new TodayAdapter(TodayActivity2.this);
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			progressDialog.dismiss();

		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... params) {

			// List<Map<String, Object>> list;
			//
			// String str = HttpUtil.getRequest(params[0]);
			//
			// list = HttpUtil.getRequest2List(str, "products");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

	}
}
