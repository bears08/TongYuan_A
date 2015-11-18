package com.tongyuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongyuan.R;

public class MyGridViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private String[] names = { "购物车", "订单", "钱包", "红包", "积分", "优惠券" };
	private int[] pics = { R.drawable.gouwuche, R.drawable.dingdan,
			R.drawable.qianbao, R.drawable.hongbao, R.drawable.jifen,
			R.drawable.youhuiquan };

	public MyGridViewAdapter(Context context) {
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

			convertView = mInflater.inflate(R.layout.grid_item_my, null);
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
