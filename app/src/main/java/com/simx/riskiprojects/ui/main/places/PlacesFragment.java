package com.simx.riskiprojects.ui.main.places;


import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.di.base.BaseFragment;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.ProgresUtils;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import com.simx.riskiprojects.ui.main.AdapterPlace;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends BaseFragment implements PlacesPresenter,
		OnItemSelectedListener {


	@BindView(R.id.rcv_rs)
	RecyclerView rcvRs;
	Unbinder unbinder;
	@BindView(R.id.spn_filter)
	Spinner spnFilter;
	@BindView(R.id.et_filter)
	SearchView etFilter;


	public PlacesFragment() {
	}

	public static PlacesFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString("key", type);
		PlacesFragment fragment = new PlacesFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Inject
	PlacesPresenterImpl presenter;
	@Inject
	AdapterPlace adapterPlace;
	private ArrayAdapter<String> arrayAdapter;
	private String type, spinner_selected;

	String[] rumah_sakits = new String[]{"Semua", "Umum", "Khusus Mata", "Khusus Jiwa",
			"Ibu dan Anak", "Bersalin"};
	String[] klikiks = new String[]{"Semua", "Kecantikan", "Umum", "Umum ( 24 jam )", "Gigi", "Bersalin"};
	String[] puskesmas = new String[]{"Semua", "Rawat Inap", "Non-Rawat Inap"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.rs_fragment, container, false);
		unbinder = ButterKnife.bind(this, view);
		type = getArguments().getString("key");
		if (type.equalsIgnoreCase("all")){
			spnFilter.setVisibility(View.GONE);
			presenter.getAllPlace();
		}
		else if (type.equalsIgnoreCase("rumah_sakit")) {
			arrayAdapter = new ArrayAdapter<>(getContext(),
					android.R.layout.simple_spinner_dropdown_item, rumah_sakits);
			spnFilter.setAdapter(arrayAdapter);
			spnFilter.setOnItemSelectedListener(this);
		} else if (type.equalsIgnoreCase("puskesmas")) {
			arrayAdapter = new ArrayAdapter<>(getContext(),
					android.R.layout.simple_spinner_dropdown_item, puskesmas);
			spnFilter.setAdapter(arrayAdapter);
			spnFilter.setOnItemSelectedListener(this);
		} else {
			arrayAdapter = new ArrayAdapter<>(getContext(),
					android.R.layout.simple_spinner_dropdown_item, klikiks);
			spnFilter.setAdapter(arrayAdapter);
			spnFilter.setOnItemSelectedListener(this);
		}


		etFilter.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {

				adapterPlace.getFilter().filter(s);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				adapterPlace.getFilter().filter(s);
				return false;
			}
		});
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
		List<ResponseSample> responseSampleList = new ArrayList<>();
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getLongitude() != null) {
				ResponseSample sample = new ResponseSample();

				Double distance = AppConst
						.distance(Double.parseDouble(results.get(i).getLatitude()),
								Double.parseDouble(results.get(i).getLongitude()));
				sample.setDisctance(distance);
				sample.setAlamat(results.get(i).getAlamat());
				sample.setJenis(results.get(i).getJenis());
				sample.setKelas(results.get(i).getKelas());
				sample.setLatitude(results.get(i).getLatitude());
				sample.setLongitude(results.get(i).getLongitude());
				sample.setNama(results.get(i).getNama());
				sample.setPimpinan(results.get(i).getPimpinan());
				sample.setTelpon(results.get(i).getTelpon());
				sample.setTipe(results.get(i).getTipe());
				responseSampleList.add(sample);
				adapterPlace.setResultsItems(responseSampleList);
			}

		}

	}

	@Override
	public void showError(String message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showLoading(boolean isShow) {
		if (isShow) {
			ProgresUtils.getInstance().showLodingDialog(getActivity());
		} else {
			ProgresUtils.getInstance().dismisDialog();
		}
	}

	@Override
	public void showDetail(ResponseSample responseSample) {
		//TrackerMapsActivity.start(getContext(), responseSample);
		openDirectionInMap(responseSample);
	}

	private void openDirectionInMap(ResponseSample responseSample) {
		Builder builder = new Builder();
		builder.scheme("https").authority("www.google.com").appendPath("maps").appendPath("dir")
				.appendPath("").appendQueryParameter("api", "1")
				.appendQueryParameter("mode", "driving").appendQueryParameter("origin",
				LocationPreferences.instance().read(PrefKey.USER_LAT, String.class) + ","
						+ LocationPreferences.instance().read(PrefKey.USER_LNG, String.class))
				.appendQueryParameter("destination",
						responseSample.getLatitude() + "," + responseSample.getLongitude());
		String url = builder.build().toString();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		i.setPackage("com.google.android.apps.maps");
		startActivity(i);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		spinner_selected = adapterView.getItemAtPosition(i).toString();
		presenter.getPlaceRs(type, spinner_selected, null);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		spinner_selected = adapterView.getItemAtPosition(0).toString();
		presenter.getPlaceRs(type, spinner_selected, null);
	}
	@Override
	public void getData() {
		presenter.getPlaceRs(type, spinner_selected, null);
	}
}
