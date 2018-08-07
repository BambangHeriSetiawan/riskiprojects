package com.simx.riskiprojects.ui.main.home;

import android.annotation.SuppressLint;
import android.util.Log;
import com.simx.riskiprojects.data.remote.API.ApiRequset;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;

/**
 * User: simx Date: 07/08/18 16:09
 */
public class HomePresenterImpl {
	private HomePresenter presenter;
	public HomePresenterImpl(HomePresenter presenter) {
		this.presenter = presenter;
	}
	@SuppressLint("CheckResult")
	public void getData() {
		ApiRequset.getall(LocationPreferences.instance().read(PrefKey.USER_LATLNG,String.class)).subscribe(
				responsePlace -> {
					if (responsePlace.getResults().size()!=0) presenter.initMarkerToMap(responsePlace.getResults());
						else presenter.showError(responsePlace.getError_message());
				},throwable -> Log.e("MainActivity", "onCreate: " + throwable.getMessage())
				,() -> Log.e("MainPresenterImpl", "getData: " )
		);
	}
}
