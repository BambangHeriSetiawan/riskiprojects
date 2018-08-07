package com.simx.riskiprojects.ui.splash;

/**
 * Created by simx on 14/02/18.
 */

public interface SplashView {

    void onSplashLoad();

    void gotoLogin();

    void showProgress(boolean isShow);

    void gotoMain(String uid);

    void showError(String message);
}
