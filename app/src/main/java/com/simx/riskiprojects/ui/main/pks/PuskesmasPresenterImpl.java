package com.simx.riskiprojects.ui.main.pks;

import android.annotation.SuppressLint;
import android.util.Log;
import com.simx.riskiprojects.data.remote.API.ApiRequset;

/**
 * User: simx Date: 07/08/18 16:50
 */
public class PuskesmasPresenterImpl {
private PuskesmasPresenter presenter;

	public PuskesmasPresenterImpl(PuskesmasPresenter presenter) {
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
