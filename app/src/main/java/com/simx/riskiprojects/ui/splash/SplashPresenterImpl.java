package com.simx.riskiprojects.ui.splash;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.simx.riskiprojects.MyApplication;
import com.tbruyelle.rxpermissions2.Permission;
import javax.inject.Inject;

/**
 * Created by simx on 14/02/18.
 */

public class SplashPresenterImpl {
    private SplashPresenter presenter;

    @Inject
    public SplashPresenterImpl(SplashPresenter presenter) {
        this.presenter = presenter;

    }

    public void loadSplash() {
        presenter.gotoMain();
    }
    public void handlePermission(Permission permission) {
        Log.e("MainPresenterImpl", "handlePermission: " );
        if (permission.granted){
            presenter.onPermissionGranter();
            presenter.startServiceLocation();
            if (!isConected())presenter.showDilogNoIntenet();
        }else if (permission.shouldShowRequestPermissionRationale){
            presenter.onPermissionDenied(permission.name);
        }else {
            presenter.gotoSetting(permission.name);
        }
    }

    public void handleErrorPermission(Throwable throwable) {
        presenter.showError(throwable.getMessage());
    }
    private Boolean isConected() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo ni = manager.getActiveNetworkInfo();
        return ni.isConnectedOrConnecting();
    }
}
