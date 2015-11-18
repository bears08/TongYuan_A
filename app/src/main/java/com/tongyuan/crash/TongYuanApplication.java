package com.tongyuan.crash;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.tongyuan.activity.ProductActivity;
import com.tongyuan.activity.SplashActivity;

public class TongYuanApplication extends Application {
	protected static Context instance;

	public void onCreate() {
		super.onCreate();
		instance = this;
		Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
																	// 以下用来捕获程序崩溃异常
	}

	// 创建服务用于捕获崩溃异常
	private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
		public void uncaughtException(Thread thread, Throwable ex) {
			restartApp();// 发生崩溃异常时,重启应用
		}
	};

	public void restartApp() {
		 Intent intent = new Intent(instance, SplashActivity.class);
		 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 instance.startActivity(intent);
		 android.os.Process.killProcess(android.os.Process.myPid());
//		 结束进程之前可以把你程序的注销或者退出代码放在这段代码之前

//		Intent intent = new Intent(this, SplashActivity.class);
//		PendingIntent restartIntent = PendingIntent.getActivity(this, 0,
//				intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//		// 退出程序
//		AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,restartIntent); // 1秒钟后重启应用
		// finish();
	}
}
