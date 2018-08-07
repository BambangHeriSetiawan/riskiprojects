package com.simx.riskiprojects.ui.splash;

import javax.inject.Inject;

/**
 * Created by simx on 14/02/18.
 */

public class SplashPresenterImpl {
    private SplashPresenter presenter;

    @Inject
    public SplashPresenterImpl(SplashPresenter presenter) {
        this.presenter = presenter;

    }

    public void loadSplash() {
        presenter.gotoMain();
    }
}
