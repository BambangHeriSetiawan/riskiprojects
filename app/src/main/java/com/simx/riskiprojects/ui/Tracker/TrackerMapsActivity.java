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
			mMap.addMarker(new MarkerOptions().position(latLng).title(responseSample.getNama()));
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
					CameraPosition.fromLatLngZoom(latLng,14)));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
		});
	}


}
