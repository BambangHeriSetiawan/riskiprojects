package com.simx.riskiprojects.di.module;


import com.simx.riskiprojects.ui.splash.SplashActivity;
import com.simx.riskiprojects.ui.splash.SplashPresenter;
import com.simx.riskiprojects.ui.splash.SplashPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by simx on 14/02/18.
 */
@Module
public class SplashActivityModule {

    @Provides
    SplashPresenter provideSplashPresenter(SplashActivity splashActivity){
        return splashActivity;
    }


    @Provides
    SplashPresenterImpl provideSplahPresenterImpl(SplashPresenter presenter){
        return new SplashPresenterImpl(presenter);
    }

}
