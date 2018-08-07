package com.simx.riskiprojects.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simx.riskiprojects.BuildConfig;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.di.base.BaseActivity;
import com.simx.riskiprojects.helper.RoundedImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainPresenter {

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
    public static String KEY_ID = "id";
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

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
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerView.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        View hView =  navigationView.getHeaderView(0);
        tvName = hView.findViewById(R.id.tv_name);
        tvEmail = hView.findViewById(R.id.tv_email);
        imgProfile = hView.findViewById(R.id.iv_profile_pic);
        tvAppVersion.setText(version);
        navigationView.setNavigationItemSelectedListener(item -> {
			drawerView.closeDrawer(GravityCompat.START);
			switch (item.getItemId()) {
				case R.id.navItemAbout:

					return true;
				case R.id.navItemRateUs:

					return true;
				case R.id.navItemFeed:

					return true;
				case R.id.navItemLogout:

					return true;
				default:
					return false;
			}
		});
    }

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

    private void hideKeyboard() {

    }

    private void setupNavMenu() {

    }
    private void lockDrawer() {
        if (drawerView != null)
            drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unlockDrawer() {
        if (drawerView != null)
            drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
