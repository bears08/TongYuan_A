package com.tongyuan.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLConnection extends SQLiteOpenHelper {

	private static final String TAG = "SQLConnection";
	public static final String DATABASE_NAME = "tongyuan.db"; // 数据库名称
	public static final int DATABASE_VERSION = 2;// 数据库版本
	public static final String TABLE_NAME_SHOP_CAR = "shopping_car";

	public SQLConnection(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/**
		 * 购物车
		 */
		String create_shopping_car = " CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME_SHOP_CAR
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,  user_id VARCHAR, product_id INTEGER, product_name VARCHAR ,product_num INTEGER ,product_price NUMERIC ,small_pic VARCHAR) ";
		db.execSQL(create_shopping_car);
		Log.i(TAG, create_shopping_car);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE_NAME_SHOP_CAR);
		onCreate(db);
	}

}
