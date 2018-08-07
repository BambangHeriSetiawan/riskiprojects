package com.simx.riskiprojects;

import android.app.Activity;
import android.app.Application;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by simx on 14/02/18.
 */

public class MyApplication extends Application implements HasActivityInjector{
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
