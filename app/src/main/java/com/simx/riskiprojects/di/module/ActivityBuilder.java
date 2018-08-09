package com.simx.riskiprojects.di.module;

import com.simx.riskiprojects.ui.Tracker.TrackerMapModule;
import com.simx.riskiprojects.ui.Tracker.TrackerMapsActivity;
import com.simx.riskiprojects.ui.main.MainActivity;
import com.simx.riskiprojects.ui.main.MainActivityModul;
import com.simx.riskiprojects.ui.main.MainActivityProvideModule;
import com.simx.riskiprojects.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by simx on 14/02/18.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector(modules = {MainActivityModul.class, MainActivityProvideModule.class})
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = TrackerMapModule.class) abstract TrackerMapsActivity trackerMapsActivity();
}
