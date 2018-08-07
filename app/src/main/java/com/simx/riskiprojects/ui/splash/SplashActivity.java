package com.simx.riskiprojects.ui.splash;


import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.base.BaseActivity;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.helper.NetworkUtils;
import com.simx.riskiprojects.service.LocationFetchService;
import com.simx.riskiprojects.ui.main.MainActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashPresenter{

    @Inject
    SplashPresenterImpl presenter;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private RxPermissions rxPermissions;
    private AlertDialog mInternetDialog;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(AppConst.PERMISSION_LOCATION_NETWORK).subscribe(
                permission ->presenter.handlePermission(permission),
                throwable -> presenter.handleErrorPermission(throwable)
        );

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
    public void gotoMain() {
        MainActivity.start(this);
        this.finish();
    }

    @Override
    public void onPermissionGranter() {
        new Handler().postDelayed(() -> presenter.loadSplash(), 2000);
    }

    @Override
    public void startServiceLocation() {
        startService(new Intent(this,LocationFetchService.class));
    }

    @Override
    public void onPermissionDenied(String name) {
        Toast.makeText(this,"Permission for " + name+ "is Denied, thats is make app not working",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoSetting(String name) {
        if (name.equalsIgnoreCase(permission.ACCESS_CHECKIN_PROPERTIES))
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),AppConst.RC_LOCATION);
        else onPermissionDenied(name);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDilogNoIntenet() {
        if (mInternetDialog != null && mInternetDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Internet Disabled!");
        builder.setMessage("No active Internet connection found.");
        builder.setPositiveButton("Turn On", (dialog, which) -> {
            Intent gpsOptionsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivityForResult(gpsOptionsIntent, AppConst.DATA_ENABLE_REQUEST);
        }).setNegativeButton("No, Just Exit", (dialog, which) -> {
            dialog.dismiss();
        });
        mInternetDialog = builder.create();
        mInternetDialog.show();
    }
}
