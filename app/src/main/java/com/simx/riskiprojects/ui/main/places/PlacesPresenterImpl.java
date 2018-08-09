package com.simx.riskiprojects.ui.main.places;

import android.annotation.SuppressLint;
import com.simx.riskiprojects.data.remote.API.ApiRequset;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;

/**
 * User: simx Date: 07/08/18 16:48
 */
public class PlacesPresenterImpl {
	private PlacesPresenter presenter;

	public PlacesPresenterImpl(PlacesPresenter presenter) {
		this.presenter = presenter;
	}
	@SuppressLint("CheckResult")
	public void getPlaceRs(String type) {
		presenter.showLoading(true);
		ApiRequset.getNearbyPlaceByKeyword(type, LocationPreferences.instance().read(
				PrefKey.USER_LATLNG,String.class)).subscribe(
						responsePlace -> {
							if (responsePlace.getResults().size()!=0)presenter.initData(responsePlace.getResults());
								else presenter.showError(responsePlace.getError_message());
						},
				throwable -> presenter.showError(throwable.getMessage()),
				() -> presenter.showLoading(false)
		);
	}
}
