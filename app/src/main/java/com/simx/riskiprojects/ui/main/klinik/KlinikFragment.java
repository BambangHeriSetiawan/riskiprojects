package com.simx.riskiprojects.ui.main.klinik;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class KlinikFragment extends BaseFragment implements KlinikPresenter {


	public KlinikFragment() {
		// Required empty public constructor
	}

	public static KlinikFragment newInstance() {
		Bundle args = new Bundle();
		KlinikFragment fragment = new KlinikFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.klinik_fragment, container, false);
	}

}
