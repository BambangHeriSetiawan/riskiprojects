package com.simx.riskiprojects.ui.Tracker;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.data.model.waypoint.ResponseWaypoint;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.ProgresUtils;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class TrackerMapsActivity extends FragmentActivity implements TrackerMapPresenter {

	private GoogleMap mMap;
	private TrackerMapPresenterImpl presenter;
	public static void start(Context context, ResponseSample responseSample) {
	    Intent starter = new Intent(context, TrackerMapsActivity.class);
	    starter.putExtra("id",responseSample);
	    context.startActivity(starter);
	}
	private ResponseSample responseSample;
	private SupportMapFragment mapFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map);
		responseSample = getIntent().getExtras().getParcelable("id");
		if (responseSample!=null)initMap(responseSample);
		presenter = new TrackerMapPresenterImpl(this);
		if (presenter==null){
			Log.e("TrackerMapsActivity", "initMap: NULL" );
		}
	}

	private void initMap(ResponseSample responseSample) {
		mapFragment.getMapAsync(googleMap -> {
			mMap = googleMap;
			LatLng latLng = new LatLng(Double.parseDouble(responseSample.getLatitude()), Double.parseDouble(responseSample.getLongitude()));
			LatLng latLngUser = new LatLng(Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LAT,String.class)),
					Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LNG,String.class))
			);

			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
					CameraPosition.fromLatLngZoom(latLng,14)));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
			MarkerOptions markerOptions = new MarkerOptions();

			switch (responseSample.getTipe()) {
				case "rumah_sakit":
					markerOptions.position(new LatLng(Double.parseDouble(responseSample.getLatitude()),Double.parseDouble(responseSample.getLongitude()))).title(responseSample.getNama()).icon(
							AppConst.createMarkerRed(this,R.drawable.ic_location_on_red_24dp)).snippet(responseSample.getAlamat());
					break;
				case "puskesmas":
					markerOptions.position(new LatLng(Double.parseDouble(responseSample.getLatitude()),Double.parseDouble(responseSample.getLongitude()))).title(responseSample.getNama()).icon(
							AppConst.createMarkerGreen(this,R.drawable.ic_location_on_green_24dp)).snippet(responseSample.getAlamat());
					break;
				case "klinik":
					markerOptions.position(new LatLng(Double.parseDouble(responseSample.getLatitude()),Double.parseDouble(responseSample.getLongitude()))).title(responseSample.getNama()).icon(
							AppConst.createMarkerBlue(this,R.drawable.ic_location_on_blue_24dp)).snippet(responseSample.getAlamat());
					break;
				default:
					markerOptions.position(new LatLng(Double.parseDouble(responseSample.getLatitude()),Double.parseDouble(responseSample.getLongitude()))).title(responseSample.getNama()).icon(
							AppConst.createMarkerRed(this,R.drawable.ic_location_on_red_24dp)).snippet(responseSample.getAlamat());
			}
			String origin =latLngUser.latitude+","+latLngUser.longitude;
			String desti = responseSample.getLatitude()+","+ responseSample.getLongitude();
			presenter.getPolyline(origin,desti);

			mMap.addMarker(markerOptions);
		});
	}

	@Override
	public void initMapLineTracker(ResponseWaypoint responseWaypoint) {
		String encodeString = responseWaypoint.getRoutes().get(0).getOverviewPolyline().getPoints();
		List<LatLng> latLngList = PolyUtil.decode(encodeString);
		Log.e("TrackerMapsActivity", "initMapLineTracker: " + latLngList);
		drawPolyLineOnMap(latLngList);
	}

	@Override
	public void showError(String message) {
		ProgresUtils.getInstance().showLodingDialogMsg(this,message);
	}

	@Override
	public void showLoading(boolean isLoading) {
		if (isLoading)ProgresUtils.getInstance().showLodingDialog(this);
			else ProgresUtils.getInstance().dismisDialog();
	}

	public void drawPolyLineOnMap(List<LatLng> list) {
		PolylineOptions polyOptions = new PolylineOptions();
		polyOptions.color(Color.RED);
		polyOptions.width(5);
		polyOptions.addAll(list);

		mMap.clear();
		mMap.addPolyline(polyOptions);

		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (LatLng latLng : list) {
			builder.include(latLng);
		}

		final LatLngBounds bounds = builder.build();

		//BOUND_PADDING is an int to specify padding of bound.. try 100.
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
		mMap.animateCamera(cu);
	}
	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}
}
