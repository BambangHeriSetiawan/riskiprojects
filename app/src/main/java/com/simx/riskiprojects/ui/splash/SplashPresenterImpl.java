package com.simx.riskiprojects.ui.splash;

import com.simx.riskiprojects.data.model.UserModel;


import javax.inject.Inject;

/**
 * Created by simx on 14/02/18.
 */

public class SplashPresenterImpl implements SplashPresenter {
    SplashView splashView;

    @Inject
    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;

    }

    @Override
    public void loadSplash() {


    }

    private void createUserDatbase(UserModel user) {

    }
}
