package com.simx.riskiprojects.ui.Tracker;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.helper.AppConst;

public class TrackerMapsActivity extends FragmentActivity implements TrackerMapPresenter {

	private GoogleMap mMap;

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
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map);
		responseSample = getIntent().getExtras().getParcelable("id");
		if (responseSample!=null)initMap(responseSample);

	}

	private void initMap(ResponseSample responseSample) {
		mapFragment.getMapAsync(googleMap -> {
			mMap = googleMap;
			LatLng latLng = new LatLng(Double.parseDouble(responseSample.getLatitude()), Double.parseDouble(responseSample.getLongitude()));
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


			mMap.addMarker(markerOptions);
		});
	}


}
