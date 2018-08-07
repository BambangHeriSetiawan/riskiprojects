package com.simx.riskiprojects.ui.main.rs;


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
public class RSFragment extends BaseFragment implements RsPresenter {


	public RSFragment() {
		// Required empty public constructor
	}

	public static RSFragment newInstance() {
		Bundle args = new Bundle();
		RSFragment fragment = new RSFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.rs_fragment, container, false);
	}

}
