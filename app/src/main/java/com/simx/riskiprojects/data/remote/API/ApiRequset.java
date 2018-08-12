package com.simx.riskiprojects.data.remote.API;

import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponsePlace;
import com.simx.riskiprojects.helper.AppConst;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * User: simx Date: 08/08/18 1:05
 */
public class ApiRequset {
	public static Observable<ResponsePlace> getNearbyPlaceByKeyword(String keyword, String location){
		return Api.Factory.create().getPlaceNearby("AIzaSyBW81faw8grMBm972NxliQ7fKjAzRTKmdw",AppConst.KEY_TYPE, AppConst.RADIUS,location,keyword) .subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());

	}

}
