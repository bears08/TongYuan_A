package com.tongyuan.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongyuan.R;
import com.tongyuan.dao.ShopCarDao;
import com.tongyuan.domain.ShopCar;
import com.tongyuan.util.image.ImageLoaderFactory;

public class ShopCarAdapter extends BaseAdapter {
	public static final String action = "jason.broadcast.action";

	private Context context;
	private LayoutInflater mInflater;
	private ImageLoaderFactory factory;
	private ShopCarDao shopCarDao;
	// todo 是不是需要新起任务
	private List<ShopCar> list;

	private static final String NUM = "nums";
	private static final String PRODUCTID = "productid";
	private static final String NAME = "name";
	private static final String INFO = "info";
	private static final String PRICE = "price";
	private static final String IMG = "img";

	public ShopCarAdapter(Context context) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		factory = new ImageLoaderFactory(context);
		shopCarDao = new ShopCarDao(context);
		list = shopCarDao.selectAll();
	}

	@Override
	public int getCount() {
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return 0;
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
		final int pos = position;
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.shop_car_listview, null);
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

		holder.name.setText((String) list.get(position).getName());
		holder.price.setText("" + list.get(position).getPrice());
		holder.num.setText("" + list.get(position).getProductNum());
		holder.productid.setText("" + list.get(position).getProductId());
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
				if (n > 0) {
					holder.num.setVisibility(View.VISIBLE);
					holder.jian.setVisibility(View.VISIBLE);
				}
				ShopCar shopCar = new ShopCar();
				shopCar.setProductId(list.get(pos).getProductId());
				shopCar.setProductNum(1);
				shopCar.setName(list.get(pos).getName());
				shopCar.setPrice(list.get(pos).getPrice());
				shopCar.setSmallPic(list.get(pos).getSmallPic());

				Intent intent = new Intent(action);
				Bundle bundle = new Bundle();
				bundle.putSerializable("shopCar", shopCar);
				intent.putExtras(bundle);

				context.sendBroadcast(intent);
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
				if (n <= 0) {
					holder.num.setVisibility(View.INVISIBLE);
					holder.jian.setVisibility(View.INVISIBLE);
				}
				ShopCar shopCar = new ShopCar();
				shopCar.setProductId(list.get(pos).getProductId());
				shopCar.setProductNum(-1);
				shopCar.setName(list.get(pos).getName());
				shopCar.setPrice(list.get(pos).getPrice());
				shopCar.setSmallPic(list.get(pos).getSmallPic());
				Intent intent = new Intent(action);
				Bundle bundle = new Bundle();
				bundle.putSerializable("shopCar", shopCar);
				intent.putExtras(bundle);
				context.sendBroadcast(intent);
			}
		});
		factory.displayImage(list.get(position).getSmallPic(), holder.img);
		return convertView;
	}

	private class ViewHolder {
		public ImageView img;
		public TextView name;
		public TextView num;
		public TextView price;
		public ImageView jia;
		public ImageView jian;
		public TextView productid;
	}

}
