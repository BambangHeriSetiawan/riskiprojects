package com.simx.riskiprojects.ui.main.maps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.base.BaseFragment;

public class MapsFragment extends BaseFragment implements MapsPresenter {
	public MapsFragment() {}
	public static MapsFragment newInstance() {
		MapsFragment fragment = new MapsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	private GoogleMap mMap;
	private SupportMapFragment mapFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.maps_fragment, container, false);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (mapFragment == null){
			mapFragment = SupportMapFragment.newInstance();
			mapFragment.getMapAsync(googleMap -> {
			mMap = googleMap;
				LatLng sydney = new LatLng(-6.218584, 106.946066);
				mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
				mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
				mMap.moveCamera(
						CameraUpdateFactory
								.newLatLngZoom(sydney,
										14));
			});
		}
		getChildFragmentManager ().beginTransaction ().replace (R.id.map, mapFragment).commit ();
	}

}
