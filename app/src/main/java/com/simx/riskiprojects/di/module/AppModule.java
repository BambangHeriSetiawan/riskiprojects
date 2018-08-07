package com.simx.riskiprojects.di.module;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.helper.NetworkUtils;

import io.fabric.sdk.android.Fabric;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by simx on 14/02/18.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    NetworkUtils provideNetworkUtil(){
        return new NetworkUtils();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
    @Provides
    @Singleton Fabric provideFabric(){
        return new Fabric.Builder(MyApplication.getContext())
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
    }

    @Provides
    @Singleton
    UserModel provideUserModel(){
        return new UserModel();
    }
}
