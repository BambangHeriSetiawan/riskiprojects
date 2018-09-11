package com.simx.riskiprojects.ui.main;

/**
 * Created by simx on 14/02/18.
 */

public interface MainPresenter {

	void showError(String message);

	void startServiceLocation();

	void onAdapterMenuClicked(MenuDrawer menuDrawer);
}
