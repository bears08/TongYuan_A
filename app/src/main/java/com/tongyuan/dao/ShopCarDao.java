package com.tongyuan.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tongyuan.domain.ShopCar;

public class ShopCarDao {
	// (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id VARCHAR, product_id
	// INTEGER, product_name VARCHAR ,product_num INTEGER ,product_price NUMERIC
	// ,small_pic VARCHAR)
	private Context context;

	public ShopCarDao(Context context) {
		this.context = context;
	}

	public void insert(ShopCar shopCar) {
		SQLConnection con = new SQLConnection(context);
		SQLiteDatabase db = con.getWritableDatabase();
		if (db != null) {
			db.execSQL(
					"insert into "
							+ SQLConnection.TABLE_NAME_SHOP_CAR
							+ "(user_id,product_id,product_name,product_num,product_price,small_pic) values (?,?,?,?,?,?);",
					new Object[] { shopCar.getUserId(), shopCar.getProductId(),
							shopCar.getName(), shopCar.getProductNum(),
							shopCar.getPrice(), shopCar.getSmallPic() });
			db.close();
		}
	}

	public void update(ShopCar shopCar) {
		SQLConnection con = new SQLConnection(context);
		SQLiteDatabase db = con.getWritableDatabase();
		if (db != null) {
			db.execSQL(
					"update " + SQLConnection.TABLE_NAME_SHOP_CAR
							+ " set product_num = ? where product_id = ? ;",
					new Object[] { shopCar.getProductNum(),
							shopCar.getProductId() });
			db.close();
		}
	}

	public void delete(ShopCar shopCar) {
		SQLConnection con = new SQLConnection(context);
		SQLiteDatabase db = con.getWritableDatabase();
		if (db != null) {
			db.execSQL("delete from  " + SQLConnection.TABLE_NAME_SHOP_CAR
					+ "  where product_id = ? ;",
					new Object[] { shopCar.getProductId() });
			db.close();
		}
	}

	public void deleteAll() {
		SQLConnection con = new SQLConnection(context);
		SQLiteDatabase db = con.getWritableDatabase();
		if (db != null) {
			db.execSQL("delete * from  " + SQLConnection.TABLE_NAME_SHOP_CAR);
			db.close();
		}
	}

	public ShopCar selectByProductId(int productid) {
		SQLConnection con = new SQLConnection(context);
		SQLiteDatabase db = con.getWritableDatabase();
		if (db != null) {

			Cursor c = db.rawQuery("select * from "
					+ SQLConnection.TABLE_NAME_SHOP_CAR + " where product_id = ? ;", new String[]{""+productid});
			if (c != null && c.getCount() > 0) {
				String userId;
				int productId;
				String name;
				int productNum;
				double price;
				String smallPic;

				if (c.moveToNext()) {
					userId = c.getString(1);
					productId = c.getInt(2);
					name = c.getString(3);
					productNum = c.getInt(4);
					price = c.getDouble(5);
					smallPic = c.getString(6);

					ShopCar shopCar = new ShopCar();
					shopCar.setUserId(userId);
					shopCar.setProductId(productId);
					shopCar.setName(name);
					shopCar.setProductNum(productNum);
					shopCar.setPrice(price);
					shopCar.setSmallPic(smallPic);
					
					db.close();
					return shopCar;
				}
				db.close();
				return null;
			}
			db.close();

		}
		return null;
	}

	public List<ShopCar> selectAll() {
		SQLConnection con = new SQLConnection(context);
		SQLiteDatabase db = con.getWritableDatabase();
		if (db != null) {
			Cursor c = db.rawQuery("select * from "
					+ SQLConnection.TABLE_NAME_SHOP_CAR + " ;", null);
			if (c != null && c.getCount() > 0) {
				List<ShopCar> list = new ArrayList<ShopCar>();
				String userId;
				int productId;
				String name;
				int productNum;
				double price;
				String smallPic;

				while (c.moveToNext()) {
					userId = c.getString(1);
					productId = c.getInt(2);
					name = c.getString(3);
					productNum = c.getInt(4);
					price = c.getDouble(5);
					smallPic = c.getString(6);

					ShopCar shopCar = new ShopCar();
					shopCar.setUserId(userId);
					shopCar.setProductId(productId);
					shopCar.setName(name);
					shopCar.setProductNum(productNum);
					shopCar.setPrice(price);
					shopCar.setSmallPic(smallPic);
					list.add(shopCar);
				}
				db.close();
				return list;
			}
			db.close();
		}
		return null;
	}
}
