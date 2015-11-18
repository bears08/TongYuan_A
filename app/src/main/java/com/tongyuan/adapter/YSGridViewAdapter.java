package com.tongyuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongyuan.R;

public class YSGridViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private String[] names = { "补肝", "补肾", "补脾", "补心", "补阴", "补阳" };
	private int[] pics = { R.drawable.meishi01, R.drawable.meishi02,
			R.drawable.meishi03, R.drawable.meishi04, R.drawable.meishi05,
			R.drawable.meishi06 };

	public YSGridViewAdapter(Context context) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return names.length;
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
		final ViewHolder holder;
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.grid_item_yangsheng, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		holder.img.setBackgroundResource(pics[position]);
		return convertView;
	}

	private class ViewHolder {
		public ImageView img;
		public TextView title;
	}
}
