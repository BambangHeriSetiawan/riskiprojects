package com.simx.dagger2starter.di.module;

import android.app.Application;
import android.content.Context;

import com.simx.dagger2starter.R;
import com.simx.dagger2starter.data.model.UserModel;
import com.simx.dagger2starter.helper.NetworkUtils;

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
    @Singleton
    UserModel provideUserModel(){
        return new UserModel();
    }
}
