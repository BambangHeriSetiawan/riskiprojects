package com.simx.riskiprojects.ui.main.klinik;

import android.annotation.SuppressLint;
import android.util.Log;
import com.simx.riskiprojects.data.remote.API.ApiRequset;

/**
 * User: simx Date: 07/08/18 16:52
 */
public class KlinikPresenterImpl {
private KlinikPresenter presenter;

	public KlinikPresenterImpl(KlinikPresenter presenter) {
		this.presenter = presenter;
	}
	@SuppressLint("CheckResult")
	public void getData() {
		ApiRequset.getNearbyPlaceByKeyword("klinik","-6.219266, 106.942376").subscribe(
				responsePlace -> {
					Log.e("MainActivity", "onCreate: " + responsePlace.toString());
				},throwable -> Log.e("MainActivity", "onCreate: " + throwable.getMessage())
				,() -> Log.e("MainPresenterImpl", "getData: " )
		);
	}
}
