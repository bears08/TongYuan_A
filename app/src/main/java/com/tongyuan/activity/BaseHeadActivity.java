package com.tongyuan.activity;

import java.util.List;

import com.tongyuan.R;
import com.tongyuan.adapter.ShopCarAdapter;
import com.tongyuan.dao.ShopCarDao;
import com.tongyuan.domain.ShopCar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseHeadActivity extends Activity {
	protected Button shopCar;
	protected TextView shopNum;
	protected int shopCarNum;
	protected ShopCarClickListener shopCarClickListener;
	protected ShopCarDao shopCarDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题栏
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		// 加载布局
		this.setContentView(R.layout.activity_base);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_head_title);
		shopCar = (Button) this.findViewById(R.id.head_right_car);
		shopNum = (TextView) this.findViewById(R.id.textShopCarNum);
		shopCarDao = new ShopCarDao(getApplicationContext());
		// 获取购物车商品数量
		// TODO 抽成一个购物车类
		shopCarNum = getShopCarNum();
		shopCarClickListener = new ShopCarClickListener();
		if (shopCarNum > 0) {
			shopNum.setVisibility(View.VISIBLE);
			// 用String类型
			shopNum.setText(shopCarNum + "");
			shopNum.setOnClickListener(shopCarClickListener);
		} else {
			shopNum.setVisibility(View.INVISIBLE);
		}
		shopCar.setOnClickListener(shopCarClickListener);
		IntentFilter filter = new IntentFilter(TodayActivity.action);
		IntentFilter filter2 = new IntentFilter(ShopCarAdapter.action);
		registerReceiver(broadcastReceiver, filter);
		registerReceiver(broadcastReceiver, filter2);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// todo
//			int i = (Integer) intent.getExtras().get("num");
			ShopCar shopCar = (ShopCar) intent.getSerializableExtra("shopCar");
			ShopCar shopCarDB = shopCarDao.selectByProductId(shopCar.getProductId());
			//如果是添加操作
			if(shopCar.getProductNum() > 0 ){
				shopCarNum = shopCarNum + 1 ;
				//1、先做一次查询，如果没有则执行insert，如果有则执行update
				if(shopCarDB == null){
					shopCarDao.insert(shopCar);
				}else{
					shopCar.setProductNum(shopCarDB.getProductNum() + 1);
					shopCarDao.update(shopCar);
				}
			}else{
				shopCarNum = shopCarNum - 1;
				//删除操作，如果不为0，就是更新，否则就是删除
				if(shopCarDB != null){
					if(shopCarDB.getProductNum() > 1){
						shopCar.setProductNum(shopCarDB.getProductNum() - 1);
						shopCarDao.update(shopCar);
					}else{
						shopCarDao.delete(shopCar);
					}
				}
			}

			if (shopCarNum > 0) {
				shopNum.setVisibility(View.VISIBLE);
				// 用String类型
				shopNum.setText(shopCarNum + "");
				shopNum.setOnClickListener(shopCarClickListener);
			} else {
				shopNum.setVisibility(View.INVISIBLE);
			}
		}
	};

	private int getShopCarNum() {
		List<ShopCar> list = shopCarDao.selectAll();
		int num = 0;
		if(list != null && list.size() > 0){
			for(int i = 0 ; i < list.size() ; i++){
				num += list.get(i).getProductNum();
			}
		}
		return num;
	}

	private class ShopCarClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if(shopCarNum < 1){
				Toast.makeText(getApplicationContext(), "没有商品，请继续购物。。。",
				Toast.LENGTH_SHORT).show();
			}else{
				Intent intent = new Intent(BaseHeadActivity.this, ShopCarActivity.class);
				startActivity(intent);
			}
		}
	}

//	@Override
//	protected void onResume() {
//		System.out.println("------------------base------resume");
//	}
}
