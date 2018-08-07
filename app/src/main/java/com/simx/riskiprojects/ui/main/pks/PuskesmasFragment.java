package com.simx.riskiprojects.ui.main.pks;


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
public class PuskesmasFragment extends BaseFragment implements PuskesmasPresenter {


	public PuskesmasFragment() {
		// Required empty public constructor
	}

	public static PuskesmasFragment newInstance() {
		Bundle args = new Bundle();
		PuskesmasFragment fragment = new PuskesmasFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.puskesmas_fragment, container, false);
	}

}
