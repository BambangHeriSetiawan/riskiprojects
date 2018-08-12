package com.simx.riskiprojects.ui.Tracker;

import android.annotation.SuppressLint;
import android.util.Log;
import com.simx.riskiprojects.BuildConfig;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.remote.API.Api;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.ProgresUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * User: simx Date: 09/08/18 12:04
 */
public class TrackerMapPresenterImpl {
	private TrackerMapPresenter presenter;

	public TrackerMapPresenterImpl(TrackerMapPresenter presenter) {
		this.presenter = presenter;
	}

	@SuppressLint("CheckResult")
	public void getPolyline(String origin, String desti) {
		presenter.showLoading(true);
		Api.Factory.create().getPolyline(MyApplication.getContext().getString(R.string.google_maps_key),origin,desti,"false","driving","false")
				.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
						responseWaypoint -> {
							presenter.showLoading(false);
							if (responseWaypoint.getRoutes()!=null)presenter.initMapLineTracker(responseWaypoint);
								else presenter.showError(responseWaypoint.getStatus());
						},throwable -> presenter.showError(throwable.getMessage())
		);
	}
}
