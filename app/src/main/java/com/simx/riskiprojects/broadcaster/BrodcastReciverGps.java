package com.simx.riskiprojects.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

/**
 * User: simx Date: 08/08/18 1:48
 */
public class BrodcastReciverGps extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Log.d("BUHOO", " :D IS ON!!!!!!!!!!!!!!!!!!!");
		} else {
			Log.d("BUHOO", " :( GPS TURNED OFF !!!!!!!!!!!!!!!!!!!");
		}
	}
}
