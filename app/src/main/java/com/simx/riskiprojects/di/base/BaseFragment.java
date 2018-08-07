package com.simx.riskiprojects.di.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

/**
 * User: simx Date: 07/08/18 16:05
 */
public class BaseFragment extends Fragment implements HasSupportFragmentInjector {
	@Inject
	DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

	@Override
	public void onAttach (Activity activity) {
		AndroidSupportInjection.inject (this);
		super.onAttach (activity);
	}

	@Override
	public void onAttach (Context context) {
		AndroidSupportInjection.inject (this);
		super.onAttach (context);
	}
	@Override
	public AndroidInjector<Fragment> supportFragmentInjector() {
		return dispatchingAndroidInjector;
	}
}
