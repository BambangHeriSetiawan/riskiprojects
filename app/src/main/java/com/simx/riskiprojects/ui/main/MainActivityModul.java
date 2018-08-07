package com.simx.riskiprojects.ui.main;


import com.simx.riskiprojects.ui.main.MainActivity;
import com.simx.riskiprojects.ui.main.MainPresenter;
import com.simx.riskiprojects.ui.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by simx on 14/02/18.
 */
@Module
public class MainActivityModul {
    @Provides
    MainPresenter provideMainPresenter(MainActivity mainActivity){
        return mainActivity;
    }
    @Provides
    MainPresenterImpl provideMainPresenterImp(MainPresenter mainView){
        return new MainPresenterImpl(mainView);
    }
}
