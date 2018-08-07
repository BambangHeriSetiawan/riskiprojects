package com.simx.riskiprojects.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Trace;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import javax.inject.Inject;

/**
 * User: simx Date: 08/08/18 1:21
 */
public class LocationFetchService extends Service implements LocationListener {
	private LocationManager locationManager;


	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		locationManager = (LocationManager) MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
	}

	@SuppressLint("MissingPermission")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("LocationFetchService", "onStartCommand: ");
		boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (isGPS && isNetwork){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000,0, this);
			@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location!=null){
				onLocationChanged(location);
			}
		}
		else if (isGPS){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,0, this);
			@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location!=null){
				onLocationChanged(location);
			}
		}

		else if (isNetwork){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000,0, this);
			@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location!=null){
				onLocationChanged(location);
			}
		}
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.e("LocationFetchService", "onLocationChanged: " + location.toString());
		LocationPreferences.instance().write(PrefKey.USER_LAT,String.valueOf(location.getLatitude()),String.class);
		LocationPreferences.instance().write(PrefKey.USER_LNG,String.valueOf(location.getLongitude()),String.class);
		String latlng = location.getLatitude() +","+ location.getLongitude();
		LocationPreferences.instance().write(PrefKey.USER_LATLNG,latlng,String.class);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.e("LocationFetchService", "onStatusChanged: " + provider);
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.e("LocationFetchService", "onProviderEnabled: " + provider);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.e("LocationFetchService", "onProviderDisabled: " + provider);
		showGPSDiabledDialog();
	}
	public void showGPSDiabledDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("GPS Disabled");
		builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");
		AlertDialog mGPSDialog = builder.create();
		mGPSDialog.show();
	}
}
