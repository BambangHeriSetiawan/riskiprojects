package com.simx.riskiprojects.helper;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.R;

/**
 * Created by simx on 14/02/18.
 */

public class AppConst {
    public static final int RC_SIGN_IN = 123;
    public static final int RC_LOCATION = 234;
    public static final int DATA_ENABLE_REQUEST= 456;
    public static final int RC_NETWORK = 345;
    public static final int RC_PLACE_AUTO = 456;

    public static final int RADIUS = 5000;
    public static final String KEY_HOSPITAL = "rimah sakit";
    public static final String KEY_PUSKESMAS = "puskesmas";
    public static final String KEY_KLINIK = "klinik";
    public static final String KEY_TYPE = "hospital";
    public static final String [] PERMISSION_LOCATION_NETWORK = new String[]{permission.ACCESS_COARSE_LOCATION,permission.ACCESS_FINE_LOCATION,permission.ACCESS_NETWORK_STATE};
    public static String URL_MAP_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?";
    public static String URL_MAP_PHOTO_KEY = "key="+ MyApplication.getContext().getString(R.string.google_maps_key);
    public static String URL_MAP_PHOTO_WIDTH = "maxwidth=400";
    public static String URL_MAP_PHOTO_REF = "photoreference=";

    public static BitmapDescriptor createMarkerGreen(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location_on_green_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(80, 40, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static BitmapDescriptor createMarkerBlue(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location_on_blue_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(80, 40, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public static BitmapDescriptor createMarkerRed(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location_on_red_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(80, 40, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
