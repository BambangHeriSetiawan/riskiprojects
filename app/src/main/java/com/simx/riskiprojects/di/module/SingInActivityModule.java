package com.simx.riskiprojects.di.module;

import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.data.remote.FirebaseAuthService;
import com.simx.riskiprojects.ui.singin.SingInActivity;
import com.simx.riskiprojects.ui.singin.SingInPresenterImp;
import com.simx.riskiprojects.ui.singin.SinginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by simx on 14/02/18.
 */
@Module
public class SingInActivityModule {

    @Provides

    SinginView provideSinginActivity(SingInActivity activity){
        return activity;
    }

    @Provides

    SingInPresenterImp provideSinginPresenter(SinginView singinView, FirebaseAuthService firebaseAuthService, UserModel userModel){
        return new SingInPresenterImp(singinView, firebaseAuthService,userModel);
    }
}
