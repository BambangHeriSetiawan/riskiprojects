package com.simx.riskiprojects.helper.preference;

import android.content.Context;
import com.simx.riskiprojects.MyApplication;

/**
 * User: simx Date: 08/08/18 0:52
 */
public class LocationPreferences {
	private static final String CACHE_NAME = LocationPreferences.class.getName();
	private static CachePreferences cachePreferences;
	private static Context mContext;
	public LocationPreferences() {
		this.mContext = MyApplication.getContext();
	}
	public static LocationPreferences instance(){
		return new LocationPreferences ();
	}
	private static CachePreferences getInstance() {
		if (cachePreferences == null)
			cachePreferences = new CachePreferences(CACHE_NAME);
		return cachePreferences;
	}

	public synchronized <T> T read(String key, Class<T> tClass) {
		return getInstance().read(key, tClass);
	}

	public synchronized <T> void write(String key, T value, Class<T> tClass) {
		getInstance().write(key, value, tClass);
	}

	public synchronized void clear() {
		getInstance().clear();
	}
}
