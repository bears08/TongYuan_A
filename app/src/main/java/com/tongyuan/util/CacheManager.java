package com.tongyuan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

//缓存，可以缓存图片和被序列化的对象， 实现缓存到内存和本地磁盘
public class CacheManager {

	private static final String CACHE_DIRNAME = ".ccutil";
	private static final String CACHE_SUFFIX = "";

	private static CacheManager cacheManager = null;
	private volatile LRUCacheMemory<String, WeakReference<?>> dataCache;
	private ExecutorService cacheService;

	private CacheManager() {
		dataCache = new LRUCacheMemory<String, WeakReference<?>>(10, 0.75f);
		cacheService = Executors.newCachedThreadPool();
	}

	/**
	 * singlton
	 * 
	 * @return
	 */
	public static synchronized CacheManager getInstance() {
		if (cacheManager == null) {
			cacheManager = new CacheManager();
		}
		return cacheManager;
	}

	/**
	 * cache data directly in memory 将数据缓存到内存中
	 * 
	 * @param key
	 *            {@link java.lang.String}
	 * @param T
	 */
	public <T> void cacheInMemory(String key, T t) {
		if (t == null)
			return;
		WeakReference<T> reference = new WeakReference<T>(t);
		t = null;
		System.gc();
		if (!dataCache.containsKey(key))
			dataCache.put(key, reference);
	}

	/**
	 * cache data in memory according to {@link java.lang.ref.WeakReference}
	 * 
	 * @param key
	 *            {@link java.lang.String}
	 * @param bmpRef
	 *            {@link java.lang.ref.WeakReference}
	 */
	public <T> void cacheInMemory(String key, WeakReference<T> tRef) {
		dataCache.put(key, tRef);
	}

	/**
	 * 首先将数据缓存到内存，然后缓存到本地 cache data in merory and then cache in disk by async
	 * method
	 * 
	 * @param key
	 *            {@link java.lang.String}
	 * @param T
	 *            extends {@link java.io.Serializable}
	 * @param context
	 */
	public <T> void cacheInDisk(final String key, final T t,
			final Context context) {
		if (t == null)
			return;
		cacheInMemory(key, t);
		cacheService.execute(new Runnable() {

			@Override
			public void run() {
//				// TODO Auto-generated method stub
//				String cachePath = "";
//				if (SdcardDetection.isAccessExternal(context))
//					cachePath = context.getExternalCacheDir() + File.separator
//							+ CACHE_DIRNAME;
//				else
//					cachePath = context.getCacheDir() + File.separator
//							+ CACHE_DIRNAME;
//				File dir = new File(cachePath);
//				if (!dir.exists())
//					dir.mkdirs();
//				File file = new File(cachePath
//						+ (cachePath.endsWith("/") ? "" : File.separator)
//						+ CCCoreUtil.toMD5(key) + CACHE_SUFFIX);
//				try {
//					Entity.saveObject(file, t);
//				} catch (Exception e) {
//					// TODO: handle exception
//					Log.e("CacheManager--cacheInDisk", e.getMessage() + "---");
//				}
			}
		});
	}

	/**
	 * cache data in merory and then cache in disk by async method
	 * 
	 * @param key
	 *            {@link java.lang.String} the url for bitmap
	 * @param T
	 *            extends {@link java.io.Serializable}
	 * @param context
	 */
	public <T> void cacheInDisk(final String key, final WeakReference<T> tRef,
			final Context context) {
		cacheInDisk(key, tRef.get(), context);
	}

	/**
	 * get object from memory
	 * 
	 * @param key
	 * @return
	 */
	public Object getFromCache(final String key) {
		Object obj = dataCache.get(key) == null ? null : dataCache.get(key)
				.get();
		return obj;
	}

	/**
	 * firstly, detect the key wether exits in memory, if exists, return object. <br>
	 * else search from disk and then return.
	 * 
	 * @param key
	 * @param context
	 * @return
	 */
	public Object getFromCache(final String key, final Context context) {
		Object object = dataCache.get(key) == null ? null : dataCache.get(key)
				.get();
		if (object == null) {
//			String cachePath = "";
//			if (SdcardDetection.isAccessExternal(context))
//				cachePath = context.getExternalCacheDir() + File.separator
//						+ CACHE_DIRNAME;
//			else
//				cachePath = context.getCacheDir() + File.separator
//						+ CACHE_DIRNAME;
//			File file = new File(cachePath
//					+ (cachePath.endsWith("/") ? "" : "/")
//					+ CCCoreUtil.toMD5(key) + CACHE_SUFFIX);
//			if (!file.exists())
//				return null;
//			try {
//				object = Entity.loadObject(file);
//			} catch (Exception e) {
//				// TODO: handle exception
//				Log.e("CacheManager--getRefFromCache", e.getMessage() + "--");
//			}
		}
		return object;
	}

	/**
	 * A class helps save object or load object
	 */
	private static class Entity {

		private static <T> void saveObject(final File file, final T t) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {
				fos = new FileOutputStream(file);
				if (t instanceof Bitmap) {
					Log.e("here", "here");
					Bitmap bitmap = (Bitmap) t;
					bitmap.compress(CompressFormat.PNG, 100, fos);
					fos.close();
				} else if (t instanceof Serializable) {
					oos = new ObjectOutputStream(fos);
					oos.writeObject(t);
					oos.close();
					fos.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private static Object loadObject(final File file) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(file);
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
				if (bitmap != null) {
					fis.close();
					return bitmap;
				}
				ois = new ObjectInputStream(fis);
				Object object = ois.readObject();
				ois.close();
				fis.close();
				return object;
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
			}
			return null;
		}
	}

}

// 实现lru缓存机制
class LRUCacheMemory<K, V> extends LinkedHashMap<K, V> {

	/**
   * 
   */
	private static final long serialVersionUID = -112723006799969648L;
	private static int MAX_COUNT = 75;

	public LRUCacheMemory(int capability, float factor, int cacheSize) {
		super(capability, factor, true);
		MAX_COUNT = cacheSize;
	}

	public LRUCacheMemory(int capability, float factor) {
		super(capability, factor, true);
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		// TODO Auto-generated method stub
		return size() > MAX_COUNT;
	}
}
