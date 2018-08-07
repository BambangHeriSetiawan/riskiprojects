package com.simx.riskiprojects.di.module;


import com.simx.riskiprojects.ui.main.MainActivity;
import com.simx.riskiprojects.ui.main.MainPresenterImpl;
import com.simx.riskiprojects.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by simx on 14/02/18.
 */
@Module
public class MainActivityModul {
    @Provides
    MainView mainView(MainActivity mainActivity){
        return mainActivity;
    }
    @Provides
    MainPresenterImpl provideMainPresenterImp(MainView mainView){
        return new MainPresenterImpl(mainView);
    }
}
