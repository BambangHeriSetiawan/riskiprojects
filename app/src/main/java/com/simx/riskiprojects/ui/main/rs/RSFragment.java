package com.simx.riskiprojects.ui.main.rs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RSFragment extends BaseFragment implements RsPresenter {


	@BindView(R.id.rcv_rs)
	RecyclerView rcvRs;
	Unbinder unbinder;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.rs_fragment, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
