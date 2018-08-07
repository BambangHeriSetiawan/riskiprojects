package com.simx.riskiprojects.ui.splash;


import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.base.BaseActivity;
import com.simx.riskiprojects.helper.NetworkUtils;
import com.simx.riskiprojects.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashPresenter{

    @Inject
    SplashPresenterImpl presenter;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        new Handler().postDelayed(() -> presenter.loadSplash(), 2000);
    }



    @Override
    public void gotoMain() {
        MainActivity.start(this);
        this.finish();
    }


}
