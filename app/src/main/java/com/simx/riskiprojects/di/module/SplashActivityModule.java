package com.simx.riskiprojects.di.module;


import com.simx.riskiprojects.data.remote.FirebaseAuthService;
import com.simx.riskiprojects.ui.splash.SplashActivity;
import com.simx.riskiprojects.ui.splash.SplashPresenterImpl;
import com.simx.riskiprojects.ui.splash.SplashView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by simx on 14/02/18.
 */
@Module
public class SplashActivityModule {

    @Provides
    SplashView splashView(SplashActivity splashActivity){
        return splashActivity;
    }


    @Provides
    SplashPresenterImpl provideSplahPresenter(SplashView splashView, FirebaseAuthService firebaseAuthService){
        return new SplashPresenterImpl(splashView,firebaseAuthService);
    }

}
