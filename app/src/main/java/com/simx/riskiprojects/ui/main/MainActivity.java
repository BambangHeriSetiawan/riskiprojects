package com.simx.riskiprojects.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;
import com.simx.riskiprojects.BuildConfig;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.base.BaseActivitySuppotFragment;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.RoundedImageView;

import com.simx.riskiprojects.service.LocationFetchService;
import com.simx.riskiprojects.ui.main.home.HomeFragment;
import com.simx.riskiprojects.ui.main.places.PlacesFragment;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    private AlertDialog mInternetDialog;
    private AlertDialog mGPSDialog;
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }
    private BroadcastReceiver mNetworkDetectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),AppConst.RC_LOCATION);
        }
    };
    private BroadcastReceiver mGPSDetectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocationManager locationManager = (LocationManager) context.getSystemService(
                    LOCATION_SERVICE);
            if (locationManager != null) {
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					startServiceLocation();
				} else {
					showGPSDiabledDialog();
				}
            }
        }
    };
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        iniUI();
    }
    private void iniUI() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerView,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
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
        View hView =  navigationView.getHeaderView(0);
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
        registerReceiver(mGPSDetectReceiver,intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mGPSDetectReceiver);
        super.onDestroy();

    }

    private OnNavigationItemSelectedListener navigationItemSelectedListener  = new OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerView.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {

                case R.id.nav_home:
                    loadFragment(HomeFragment.newInstance());
                    return true;
                case R.id.nav_rumah_sakit:
                    loadFragment(PlacesFragment.newInstance("rumah sakit"));
                    return true;
                case R.id.nav_puskesmas:
                    loadFragment(PlacesFragment.newInstance("puskesmas"));
                    return true;
                case R.id.nav_klinik:
                    loadFragment(PlacesFragment.newInstance("klinik"));
                    return true;
                case R.id.navItemAbout:
                    return true;
                default:
                    return false;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        if (drawerView != null)
            drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();ft.setCustomAnimations(R.anim.slide_left,R.anim.slide_right,0,0);
        ft.replace(R.id.frame, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).disallowAddToBackStack().commit();
        drawerView.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == AppConst.RC_LOCATION){
                startServiceLocation();
            }
            else if (requestCode == AppConst.DATA_ENABLE_REQUEST)startServiceLocation();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startServiceLocation() {
        Intent i= new Intent(this, LocationFetchService.class);
        startService(i);
    }

    public void showGPSDiabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Disabled");
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");
        builder.setPositiveButton("Settings", (dialog, which) -> {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		});
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog mGPSDialog = builder.create();
        mGPSDialog.show();
    }
}
