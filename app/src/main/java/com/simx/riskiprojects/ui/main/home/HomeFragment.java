package com.simx.riskiprojects.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.data.model.ResultsItem;
import com.simx.riskiprojects.di.base.BaseFragment;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.ProgresUtils;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class HomeFragment extends BaseFragment implements HomePresenter {
	public HomeFragment() {}
	public static HomeFragment newInstance() {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	private List<Marker> markers = new ArrayList<>();
	private GoogleMap mMap;
	private SupportMapFragment mapFragment;
	@Inject HomePresenterImpl presenter;
	private IconGenerator iconFactory;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.maps_fragment, container, false);
		presenter.getData();
		iconFactory = new IconGenerator(getContext());
		return view;
	}

	@SuppressLint("MissingPermission")
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (mapFragment == null){
			mapFragment = SupportMapFragment.newInstance();
			mapFragment.getMapAsync(googleMap -> {
			mMap = googleMap;
			mMap.setMyLocationEnabled(true);

			LatLng latLng = new LatLng(
					Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LAT, String.class)),
					Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LNG, String.class)));
			if (latLng!=null){
				mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
				mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng,14)));
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
			}
			});
		}
		getChildFragmentManager ().beginTransaction ().replace (R.id.map, mapFragment).commit ();
	}

	@Override
	public void initMarkerToMap(List<ResultsItem> results) {
		for (int i = 0; i < results.size(); i++) {
			createMarker(
					results.get(i).getGeometry().getLocation().getLat(),
					results.get(i).getGeometry().getLocation().getLng(),
					results.get(i).getName(),
					results.get(i).getVicinity()
			).showInfoWindow();
		}
	}

	@Override
	public void showError(String error_message) {
		Toast.makeText(getContext(),error_message,Toast.LENGTH_LONG).show();
	}


	protected Marker createMarker(double latitude, double longitude, String title, String jenis) {
		MarkerOptions markerOptions = new MarkerOptions();
		switch (jenis) {
			case "rumah_sakit":
				markerOptions.position(new LatLng(latitude,longitude)).title(title).icon(AppConst.createMarkerRed(getContext(),R.drawable.ic_location_on_red_900_24dp)).snippet(jenis);
				break;
			case "puskesmas":
				markerOptions.position(new LatLng(latitude,longitude)).title(title).icon(AppConst.createMarkerGreen(getContext(),R.drawable.ic_location_on_green_600_24dp)).snippet(jenis);
				break;
			case "klinik":
				markerOptions.position(new LatLng(latitude,longitude)).title(title).icon(AppConst.createMarkerBlue(getContext(),R.drawable.ic_location_on_blue_900_24dp)).snippet(jenis);
				break;
			default:
				markerOptions.position(new LatLng(latitude,longitude)).title(title).icon(AppConst.createMarkerRed(getContext(),R.drawable.ic_location_on_red_900_24dp)).snippet(jenis);
		}
		//markerOptions.position(new LatLng(latitude,longitude)).title(title).icon(bitmapDescriptorFromVector(getContext(),R.drawable.ic_add_location)).snippet(snippet);
		return mMap.addMarker(markerOptions);
	}

	@Override
	public void showLoading(boolean isshow) {
		if (isshow)
			ProgresUtils.getInstance().showLodingDialog(getActivity());
				else ProgresUtils.getInstance().dismisDialog();
	}

	@Override
	public void createMarkerInMap(ResponseSample responseSample) {
		createMarker(
				Double.parseDouble(responseSample.getLatitude()),
				Double.parseDouble(responseSample.getLongitude()),
				responseSample.getNama(),
				responseSample.getTipe()
		).showInfoWindow();
	}
}
