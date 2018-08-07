package com.simx.riskiprojects.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentSnapshot;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.data.remote.API.ApiRequset;
import com.simx.riskiprojects.data.remote.FirebaseAuthService;
import com.simx.riskiprojects.helper.preference.LocationPreferences;
import com.simx.riskiprojects.helper.preference.PrefKey;
import com.tbruyelle.rxpermissions2.Permission;

/**
 * Created by simx on 14/02/18.
 */

public class MainPresenterImpl {
    private MainPresenter presenter;

    public MainPresenterImpl(MainPresenter presenter) {
        this.presenter = presenter;
    }

}
