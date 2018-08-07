package com.simx.riskiprojects.di.module;

import com.simx.riskiprojects.ui.main.MainActivity;
import com.simx.riskiprojects.ui.singin.SingInActivity;
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

    @ContributesAndroidInjector (modules = SingInActivityModule.class)
    abstract SingInActivity singInActivity();

    @ContributesAndroidInjector(modules = MainActivityModul.class)
    abstract MainActivity mainActivity();

}
