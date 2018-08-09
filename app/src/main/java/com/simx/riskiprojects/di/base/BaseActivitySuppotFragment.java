package com.simx.riskiprojects.di.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

/**
 * User: simx Date: 07/08/18 16:17
 */
public class BaseActivitySuppotFragment extends AppCompatActivity implements HasActivityInjector,
		HasSupportFragmentInjector {
	@Inject
	DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
	@Inject DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;



	@Override
	public void onAttachFragment (Fragment fragment) {
		AndroidSupportInjection.inject (fragment);
		super.onAttachFragment (fragment);
	}

	@Override
	protected void onCreate (@Nullable Bundle savedInstanceState) {
		AndroidInjection.inject (this);
		super.onCreate (savedInstanceState);
	}

	@Override
	public AndroidInjector<Activity> activityInjector () {
		return activityDispatchingAndroidInjector;
	}

	@Override
	public AndroidInjector<Fragment> supportFragmentInjector () {
		return fragmentDispatchingAndroidInjector;
	}
}
