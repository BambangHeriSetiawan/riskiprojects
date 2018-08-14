package com.simx.riskiprojects.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocomplete.IntentBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.simx.riskiprojects.BuildConfig;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.data.remote.API.Api.Factory;
import com.simx.riskiprojects.di.base.BaseActivitySuppotFragment;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.ProgresUtils;
import com.simx.riskiprojects.helper.RoundedImageView;
import com.simx.riskiprojects.service.LocationFetchService;
import com.simx.riskiprojects.ui.main.home.HomeFragment;
import com.simx.riskiprojects.ui.main.places.PlacesFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoFirestore.CompletionListener;


public class MainActivity extends BaseActivitySuppotFragment implements MainPresenter {

	@Inject
	MainPresenterImpl presenter;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.activityMain)
	LinearLayout activityMain;
	@BindView(R.id.clRootView)
	CoordinatorLayout clRootView;
	@BindView(R.id.tvAppVersion)
	TextView tvAppVersion;
	@BindView(R.id.navigationView)
	NavigationView navigationView;
	@BindView(R.id.drawerView)
	DrawerLayout drawerView;
	TextView tvName, tvEmail;
	RoundedImageView imgProfile;
	@BindView(R.id.rcv_sample)
	RecyclerView rcvSample;
	private FirebaseFirestore db;
	private CollectionReference colRef;

	private AlertDialog mInternetDialog;
	private AlertDialog mGPSDialog;
	private boolean doubleBackToExitPressedOnce = false;
	public static void start(Context context) {
		Intent starter = new Intent(context, MainActivity.class);
		starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(starter);
	}

	private BroadcastReceiver mNetworkDetectReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
					AppConst.RC_LOCATION);
		}
	};
	private BroadcastReceiver mGPSDetectReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(LOCATION_SERVICE);
			if (locationManager != null) {
				if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					startServiceLocation();
				} else {
					showGPSDiabledDialog();
				}
			}
		}
	};
	private AdapterSample adapterSample;
	@SuppressLint("CheckResult")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		iniUI();
		adapterSample = new AdapterSample(responseSample -> {
			Log.e("MainActivity", "onCreate: " + responseSample);
		});
		rcvSample.setHasFixedSize(true);
		rcvSample.setItemAnimator(new DefaultItemAnimator());
		rcvSample.setLayoutManager(new LinearLayoutManager(this));
		rcvSample.setAdapter(adapterSample);
		db = FirebaseFirestore.getInstance();
		colRef = db.collection(AppConst.COLLECTION_REF_REV);
	}

	private void iniUI() {
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerView, toolbar,
				R.string.open_drawer, R.string.close_drawer) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);

			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};
		drawerView.addDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
		String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
		View hView = navigationView.getHeaderView(0);
		tvName = hView.findViewById(R.id.tv_name);
		tvEmail = hView.findViewById(R.id.tv_email);
		imgProfile = hView.findViewById(R.id.iv_profile_pic);
		tvAppVersion.setText(version);
		navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
		loadFragment(HomeFragment.newInstance());
	}

	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter intent = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
		registerReceiver(mGPSDetectReceiver, intent);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mGPSDetectReceiver);
		super.onDestroy();

	}

	private OnNavigationItemSelectedListener navigationItemSelectedListener = new OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			drawerView.closeDrawer(GravityCompat.START);
			switch (item.getItemId()) {

				case R.id.nav_home:
					loadFragment(HomeFragment.newInstance());
					return true;
				case R.id.nav_rumah_sakit:
					loadFragment(PlacesFragment.newInstance("rumah_sakit"));
					return true;
				case R.id.nav_puskesmas:
					loadFragment(PlacesFragment.newInstance("puskesmas"));
					return true;
				case R.id.nav_klinik:
					loadFragment(PlacesFragment.newInstance("klinik"));
					/*readDataFirestore();*/
					return true;
				case R.id.navItemAbout:
					//getDummyData();
					showDialogAbout();
					return true;
				default:
					return false;
			}
		}
	};

	private void showDialogAbout() {
		ProgresUtils.getInstance().showLodingDialogMsgBtn(this,
				"Find Near Hospital adalah aplikasi yang memudahkan dalam mencari "
						+ "tempat layanan kesehatan terdekat dan juga yang diinginkan yang "
						+ "ada di kota Pekanbaru oleh penggunanya.\" By Riski Indra Hilman, from State Islamic University of Sultan Syarif Kasim Riau");
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (drawerView != null) {
			drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		}
	}

	@Override
	public void onBackPressed() {
		if (drawerView.isDrawerOpen(GravityCompat.START)) {
			drawerView.closeDrawer(GravityCompat.START);
		} else {
			if (doubleBackToExitPressedOnce) {
				super.onBackPressed();
				return;
			}

			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.nav_search:
				openGooleMapAutoCompletePlace();
				return true;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void openGooleMapAutoCompletePlace() {
		try {
			Intent intent = new IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
			startActivityForResult(intent, AppConst.RC_PLACE_AUTO);
		} catch (GooglePlayServicesRepairableException e) {
			// TODO: Handle the error.
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO: Handle the error.
		}
	}

	private void loadFragment(Fragment fragment) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_left, R.anim.slide_right, 0, 0);
		ft.replace(R.id.frame, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.disallowAddToBackStack().commit();
		drawerView.closeDrawer(GravityCompat.START);
	}

	@Override
	public void showError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == AppConst.RC_LOCATION) {
				startServiceLocation();
			} else if (requestCode == AppConst.RC_PLACE_AUTO) {
				Place place = PlaceAutocomplete.getPlace(this, data);
				Log.e("MainActivity", "onActivityResult: " + place.toString());
			} else if (requestCode == PlaceAutocomplete.RESULT_ERROR) {
				Status status = PlaceAutocomplete.getStatus(this, data);
				Log.e("MainActivity", "onActivityResult: " + status.getStatusMessage());
			} else {
				startServiceLocation();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void startServiceLocation() {
		Intent i = new Intent(this, LocationFetchService.class);
		startService(i);
	}

	public void showGPSDiabledDialog() {
		Builder builder = new Builder(this);
		builder.setTitle("GPS Disabled");
		builder.setMessage(
				"Gps is disabled, in order to use the application properly you need to enable GPS of your device");
		builder.setPositiveButton("Settings", (dialog, which) -> {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		});
		builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
		AlertDialog mGPSDialog = builder.create();
		mGPSDialog.show();
	}

	@SuppressLint("CheckResult")
	private void getDummyData() {
		Factory.create().getAll().subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(responseSamples -> pushToFirestore(responseSamples)/*adapterSample.setResponseSamples(responseSamples)*/,
						throwable -> Log.e("MainActivity", "getDummyData: " + throwable),
						() -> Log.e("MainActivity", "getDummyData: "));
	}

	private void pushToFirestore(List<ResponseSample> responseSamples) {
		ProgresUtils.getInstance().showLodingDialog(this);
		for (int i = 0; i < responseSamples.size(); i++) {
			int finalI = i;
			colRef.add(responseSamples.get(i)).addOnCompleteListener(task -> {
				Log.e("MainActivity", "pushToFirestore: " + task.getResult().getId());
				ProgresUtils.getInstance().dismisDialog();
				/*pushGeoFire(responseSamples.get(finalI).getLatitude(),
						responseSamples.get(finalI).getLongitude(), task.getResult().getId());*/
			}).addOnSuccessListener(documentReference -> {
				Log.e("MainActivity", "pushToFirestore: " + documentReference.getId());
			}).addOnFailureListener(e -> {
				Log.e("MainActivity", "pushToFirestore: " + e);
			});
		}
	}
	private void readDataFirestore() {
		colRef.get().addOnCompleteListener(task -> {
			if (task.isSuccessful()){
				for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
					Log.e("MainActivity", "readDataFirestore: " + task.getResult().getDocuments().get(i).getId());
					Log.e("MainActivity", "readDataFirestore: " + task.getResult().getDocuments().get(i).get("latitude"));
					Log.e("MainActivity", "readDataFirestore: " + task.getResult().getDocuments().get(i).get("longitude"));
					if (
							task.getResult().getDocuments().get(i).get("latitude")!=null
							&& task.getResult().getDocuments().get(i).get("latitude")!=null
							){
						pushGeoFire(
								task.getResult().getDocuments().get(i).get("latitude").toString(),
								task.getResult().getDocuments().get(i).get("longitude").toString(),
								task.getResult().getDocuments().get(i).getId()
						);
					}
				}

			}
		});
	}

	private void pushGeoFire(String lat, String lng, String key) {
		//ProgresUtils.getInstance().showLodingDialog(this);
		GeoFirestore geoFirestore = new GeoFirestore(colRef);
		GeoPoint geoPoint;
		if (lat==null && lng==null){
			geoPoint = new GeoPoint(0.0,0.0);
		}else {
			geoPoint = new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lng));
		}
		geoFirestore.setLocation(key, geoPoint, e -> Log.e("MainActivity", "pushGeoFire: " + e.getMessage()));
	}
}
