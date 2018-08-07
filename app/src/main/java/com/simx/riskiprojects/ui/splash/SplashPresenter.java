package com.simx.riskiprojects.ui.splash;

/**
 * Created by simx on 14/02/18.
 */

public interface SplashPresenter {
    void gotoMain();

	void startServiceLocation();

	void onPermissionDenied(String name);

	void gotoSetting(String name);

	void showError(String message);

	void showDilogNoIntenet();

	void onPermissionGranter();
}
