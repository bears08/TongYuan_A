package com.tongyuan.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.tongyuan.interf.ImageCallBack;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class DownImage {
	public String image_path;

	public DownImage(String image_path) {

		this.image_path = image_path;

	}

	public void loadImage(final ImageCallBack callBack) {

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				// TODO Auto-generated method stub

				super.handleMessage(msg);

				Drawable drawable = (Drawable) msg.obj;

				callBack.getDrawable(drawable);

			}

		};

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					Drawable drawable = Drawable.createFromStream(new URL(
							image_path).openStream(), "");

					Message message = Message.obtain();

					message.obj = drawable;

					handler.sendMessage(message);

				} catch (MalformedURLException e) {
					e.printStackTrace();

				} catch (IOException e) {
					e.printStackTrace();

				}

			}

		}).start();

	}


}
