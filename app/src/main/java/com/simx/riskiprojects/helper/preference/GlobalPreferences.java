package com.simx.riskiprojects.helper.preference;

import android.content.Context;

/**
 * User: simx Date: 08/08/18 0:53
 */
public class GlobalPreferences {
	private static final String CACHE_NAME = GlobalPreferences.class.getName();
	private static CachePreferences cachePreferences;
	private Context mContext;
	GlobalPreferences preferences;
	public static GlobalPreferences instance(){
		return new GlobalPreferences ();
	}
	public GlobalPreferences(Context context) {
		this.mContext = context;
	}

	public GlobalPreferences () {
	}

	private static CachePreferences getInstance(Context context) {
		if (cachePreferences == null)
			cachePreferences = new CachePreferences(CACHE_NAME);
		return cachePreferences;
	}

	public synchronized <T> T read(String key, Class<T> tClass) {
		return getInstance(this.mContext).read(key, tClass);
	}

	public synchronized <T> void write(String key, T value, Class<T> tClass) {
		getInstance(this.mContext).write(key, value, tClass);
	}

	public synchronized void clear() {
		getInstance(this.mContext).clear();
	}

}
