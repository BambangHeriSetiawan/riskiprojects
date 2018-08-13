package com.simx.riskiprojects.ui.main.places;

import android.annotation.SuppressLint;
import android.util.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.helper.AppConst;
import java.util.List;

/**
 * User: simx Date: 07/08/18 16:48
 */
public class PlacesPresenterImpl {
	private PlacesPresenter presenter;
	private FirebaseFirestore fb;
	private CollectionReference colRef;
	public PlacesPresenterImpl(PlacesPresenter presenter) {
		this.colRef = MyApplication.getFirestore().collection(AppConst.COLLECTION_REF_REV);
		this.presenter = presenter;

	}
	@SuppressLint("CheckResult")
	public void getPlaceRs(String type, String spinner_selected, String name) {
		String end = name+ '\uf8ff';
		presenter.showLoading(true);
		Query query = null;
		if (name!=null){
			if (type.equalsIgnoreCase("puskesmas")){
				switch (spinner_selected) {
					case "Semua":query = colRef.whereEqualTo("tipe", type).orderBy("name").startAt(name).endAt(end);
						break;
					case "Rawat Inap":
						query = colRef.whereEqualTo("tipe", type).whereEqualTo("rawatInap","Ya").orderBy("name").startAt(name).endAt(end);
						break;
					case "Non-Rawat Inap":
						query = colRef.whereEqualTo("tipe", type).whereEqualTo("rawatInap","Tidak").orderBy("name").startAt(name).endAt(end);
						break;
					default:query = colRef.whereEqualTo("tipe", type).whereGreaterThanOrEqualTo("name",name);

				}
			}else {
				switch (spinner_selected) {
					case "Semua":
						query = colRef.whereEqualTo("tipe", type).orderBy("name").startAt(name.toUpperCase()).endAt(end.toUpperCase());
						break;
					default:
						query = colRef.whereEqualTo("tipe", type).whereEqualTo("jenis",spinner_selected).orderBy("name").startAt(name).endAt(end);
						break;
				}
			}
		}else {
			if (type.equalsIgnoreCase("puskesmas")){
				switch (spinner_selected) {
					case "Semua":query = colRef.whereEqualTo("tipe", type);
						break;
					case "Rawat Inap":
						query = colRef.whereEqualTo("tipe", type).whereEqualTo("rawatInap","Ya");
						break;
					case "Non-Rawat Inap":
						query = colRef.whereEqualTo("tipe", type).whereEqualTo("rawatInap","Tidak");
						break;
					default:query = colRef.whereEqualTo("tipe", type);

				}
			}else {
				switch (spinner_selected) {
					case "Semua":
						query = colRef.whereEqualTo("tipe", type);
						break;
					default:
						query = colRef.whereEqualTo("tipe", type).whereEqualTo("jenis",spinner_selected);
						break;
				}
			}
		}

		query.get().addOnCompleteListener(task -> {
			List<ResponseSample> responseSamples = task.getResult().toObjects(ResponseSample.class);
			Log.e("PlacesPresenterImpl", "getPlaceRs: " + responseSamples.size());
			presenter.initData(responseSamples);
			presenter.showLoading(false);
		}).addOnFailureListener(e -> {
			presenter.showError(e.getMessage());
		});
	}
}
