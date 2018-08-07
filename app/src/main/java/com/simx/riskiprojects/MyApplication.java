package com.simx.riskiprojects;

import android.app.Activity;
import android.app.Application;


import android.content.Context;
import android.support.multidex.MultiDex;
import com.crashlytics.android.Crashlytics;
import com.simx.riskiprojects.di.component.DaggerAppComponent;
import com.simx.riskiprojects.di.module.NetModule;
import io.fabric.sdk.android.Fabric;
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

    @Inject Fabric fabric;
    private static Context context;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (RuntimeException multiDexException) {
            multiDexException.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DaggerAppComponent.builder().application(this).net(new NetModule()).build().inject(this);
        CalligraphyConfig.initDefault(mCalligraphyConfig);
        Fabric.with(fabric);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public static Context getContext() {
        return context;
    }
}
