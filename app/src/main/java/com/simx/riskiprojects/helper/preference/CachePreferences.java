package com.simx.riskiprojects.helper.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.simx.riskiprojects.MyApplication;

/**
 * User: simx Date: 08/08/18 0:51
 */
public class CachePreferences {
	private SharedPreferences sharedPreferences;
	public CachePreferences(String cacheName) {
		this.sharedPreferences = MyApplication.getContext().getSharedPreferences(cacheName, Context.MODE_PRIVATE);
	}

	public synchronized <T> T read(String key, Class<T> tClass) {
		Object object = null;
		if (tClass == String.class)
			object = sharedPreferences.getString(key, "");
		else if (tClass == Integer.class)
			object = sharedPreferences.getInt(key, 0);
		else if (tClass == Boolean.class)
			object = sharedPreferences.getBoolean(key, false);
		else if (tClass == Uri.class) {
			String uri = sharedPreferences.getString(key, "");
			object = !TextUtils.isEmpty(uri) ? Uri.parse(uri) : "";
		}
		return tClass.cast(object);
	}

	public synchronized <T> void write(String key, T value, Class<T> tClass) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		if (tClass == String.class)
			editor.putString(key, (String) value);
		else if (tClass == Integer.class)
			editor.putInt(key, (value != null ? (Integer) value : 0));
		else if (tClass == Boolean.class)
			editor.putBoolean(key, (value != null ? (Boolean) value : false));
		else if (tClass == Uri.class)
			editor.putString(key, (value != null ? new Gson().toJson(value) : null));
		editor.apply();
	}

	public synchronized void clear() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.apply();
	}

}
