package com.simx.riskiprojects.ui.main.places;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.di.base.BaseFragment;
import com.simx.riskiprojects.helper.ProgresUtils;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import com.simx.riskiprojects.ui.Tracker.TrackerMapsActivity;
import com.simx.riskiprojects.ui.main.AdapterPlace;
import java.util.List;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends BaseFragment implements PlacesPresenter {


	@BindView(R.id.rcv_rs)
	RecyclerView rcvRs;
	Unbinder unbinder;

	public PlacesFragment() {
		// Required empty public constructor
	}

	public static PlacesFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString("key",type);
		PlacesFragment fragment = new PlacesFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@Inject
	PlacesPresenterImpl presenter;
	@Inject AdapterPlace adapterPlace;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.rs_fragment, container, false);
		unbinder = ButterKnife.bind(this, view);
		String type = getArguments().getString("key");
		presenter.getPlaceRs(type);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rcvRs.setHasFixedSize(true);
		rcvRs.setItemAnimator(new DefaultItemAnimator());
		rcvRs.setLayoutManager(new LinearLayoutManager(getContext()));
		rcvRs.setAdapter(adapterPlace);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}


	@Override
	public void initData(List<ResponseSample> results) {
		adapterPlace.setResultsItems(results);
	}

	@Override
	public void showError(String message) {
		Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
	}

	@Override
	public void showLoading(boolean isShow) {
		if (isShow)ProgresUtils.getInstance().showLodingDialog(getActivity());
			else ProgresUtils.getInstance().dismisDialog();
	}

	@Override
	public void showDetail(ResponseSample responseSample) {
		//TrackerMapsActivity.start(getContext(), responseSample);
		openDirectionInMap(responseSample);
	}

	private void openDirectionInMap(ResponseSample responseSample) {
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("https")
				.authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("")
				.appendQueryParameter("api", "1")
				.appendQueryParameter("mode", "driving")
				.appendQueryParameter("origin", LocationPreferences.instance().read(PrefKey.USER_LAT,String.class) + "," + LocationPreferences.instance().read(PrefKey.USER_LNG,String.class))
				.appendQueryParameter("destination", responseSample.getLatitude() + "," + responseSample.getLongitude());
		String url = builder.build().toString();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		i.setPackage("com.google.android.apps.maps");
		startActivity(i);
	}

}
