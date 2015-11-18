package com.tongyuan.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库释放资源
 * 
 */
public class DBUtil {
	public static void release(Cursor cursor) {
		release(null, cursor);
	}

	public static void release(SQLiteDatabase conn) {
		release(conn, null);
	}

	public static void release(SQLiteDatabase conn, Cursor cursor) {
		if (cursor != null) {
			try {
				cursor.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			cursor = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
