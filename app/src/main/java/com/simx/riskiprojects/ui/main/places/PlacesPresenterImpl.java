package com.simx.riskiprojects.ui.main.places;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.data.remote.API.ApiRequset;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import java.util.List;

/**
 * User: simx Date: 07/08/18 16:48
 */
public class PlacesPresenterImpl {
	private PlacesPresenter presenter;
	private FirebaseFirestore fb;
	private CollectionReference colRef;
	public PlacesPresenterImpl(PlacesPresenter presenter) {
		this.colRef = MyApplication.getFirestore().collection("DATA_PROJECTS");
		this.presenter = presenter;
	}
	@SuppressLint("CheckResult")
	public void getPlaceRs(String type) {
		presenter.showLoading(true);
		Query query = colRef.whereEqualTo("tipe", type);
		query.get().addOnCompleteListener(task -> {
			List<ResponseSample> responseSamples = task.getResult().toObjects(ResponseSample.class);
			presenter.initData(responseSamples);
			presenter.showLoading(false);
		}).addOnFailureListener(e -> {
			presenter.showError(e.getMessage());
		});
	}
}
