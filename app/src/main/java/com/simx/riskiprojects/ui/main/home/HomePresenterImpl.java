package com.simx.riskiprojects.ui.main.home;

import android.annotation.SuppressLint;
import android.util.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import javax.annotation.Nullable;
import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQueryEventListener;

/**
 * User: simx Date: 07/08/18 16:09
 */
public class HomePresenterImpl {
	private HomePresenter presenter;
	private CollectionReference reference;
	private CollectionReference referenceGeo;
	private GeoFirestore geoFirestore;
	public HomePresenterImpl(HomePresenter presenter) {
		this.presenter = presenter;
		this.reference = MyApplication.getFirestore().collection(AppConst.COLLECTION_REF_REV);
		this.geoFirestore = new GeoFirestore(reference);
		/*this.geoFire = new GeoFire(referenceGeo);
		this.geoQuery = this.geoFire.queryAtLocation(new GeoLocation(
				Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LAT,String.class)),
				Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LNG,String.class))
		),0.5);*/
	}
	@SuppressLint("CheckResult")
	public void getData() {
		presenter.showLoading(true);
		/*GeoPoint geoPoint = new GeoPoint(0.5128211,101.464679);*/
		GeoPoint geoPoint = new GeoPoint(Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LAT,String.class)),
				Double.parseDouble(LocationPreferences.instance().read(PrefKey.USER_LNG,String.class)));
		geoFirestore.queryAtLocation(geoPoint,6).addGeoQueryEventListener(new GeoQueryEventListener() {
			@Override
			public void onKeyEntered(String s, GeoPoint geoPoint) {
				getDataFromIdLocation(s);
			}

			@Override
			public void onKeyExited(String s) {
				Log.e("HomePresenterImpl", "onKeyExited: " + s);
			}

			@Override
			public void onKeyMoved(String s, GeoPoint geoPoint) {
				Log.e("HomePresenterImpl", "onKeyMoved: " + s);
				Log.e("HomePresenterImpl", "onKeyMoved: " + geoPoint);
			}

			@Override
			public void onGeoQueryReady() {
				presenter.showLoading(false);
			}

			@Override
			public void onGeoQueryError(Exception e) {
				presenter.showError(e.getMessage());
			}
		});

	}

	private void getDataFromIdLocation(String key) {
		Log.e("HomePresenterImpl", "getDataFromIdLocation: " + key);
		reference.document(key).addSnapshotListener((documentSnapshot, e) -> {
			if (documentSnapshot!=null){
				ResponseSample responseSample  = documentSnapshot.toObject(ResponseSample.class);
				presenter.createMarkerInMap(responseSample);
			}else {
				presenter.showError(e.getMessage());
			}
		});

	}
}
