package com.simx.riskiprojects.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.simx.riskiprojects.BuildConfig;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.di.base.BaseActivity;
import com.simx.riskiprojects.di.base.BaseActivitySuppotFragment;
import com.simx.riskiprojects.helper.RoundedImageView;

import com.simx.riskiprojects.ui.main.klinik.KlinikFragment;
import com.simx.riskiprojects.ui.main.maps.MapsFragment;
import com.simx.riskiprojects.ui.main.pks.PuskesmasFragment;
import com.simx.riskiprojects.ui.main.rs.RSFragment;
import com.simx.riskiprojects.ui.main.rs.RsFragmentModule;
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
        Fragment fragment = MapsFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment).commit();

    }



    private OnNavigationItemSelectedListener navigationItemSelectedListener  = new OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerView.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {

                case R.id.nav_home:
                    loadFragment(MapsFragment.newInstance());
                    return true;
                case R.id.nav_rumah_sakit:
                    loadFragment(RSFragment.newInstance());
                    return true;
                case R.id.nav_puskesmas:
                    loadFragment(PuskesmasFragment.newInstance());
                    return true;
                case R.id.nav_klinik:
                    loadFragment(KlinikFragment.newInstance());
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
}
