package com.simx.riskiprojects.di.component;

import android.app.Application;

import com.simx.riskiprojects.MyApplication;
import com.simx.riskiprojects.di.module.ActivityBuilder;
import com.simx.riskiprojects.di.module.AppModule;
import com.simx.riskiprojects.di.module.GlideAppModules;
import com.simx.riskiprojects.di.module.NetModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by simx on 14/02/18.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        NetModule.class, GlideAppModules.class
})
public interface AppComponent  {

    @Component.Builder
    interface Builder {

        @BindsInstance Builder application(Application application);
        AppComponent build();
        Builder net(NetModule netModule);
        Builder glide(GlideAppModules glideAppModules);
    }

    void inject(MyApplication application);

}
