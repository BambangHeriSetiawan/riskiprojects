package com.simx.riskiprojects.helper;

import android.Manifest;
import android.Manifest.permission;

/**
 * Created by simx on 14/02/18.
 */

public class AppConst {
    public static final int RC_SIGN_IN = 123;
    public static final int RC_LOCATION = 234;
    public static final int DATA_ENABLE_REQUEST= 456;
    public static final int RC_NETWORK = 345;
    public static final int RADIUS = 5000;
    public static final String KEY_HOSPITAL = "rimah sakit";
    public static final String KEY_PUSKESMAS = "puskesmas";
    public static final String KEY_KLINIK = "klinik";
    public static final String KEY_TYPE = "hospital";
    public static final String [] PERMISSION_LOCATION_NETWORK = new String[]{permission.ACCESS_COARSE_LOCATION,permission.ACCESS_FINE_LOCATION,permission.ACCESS_NETWORK_STATE};

}
