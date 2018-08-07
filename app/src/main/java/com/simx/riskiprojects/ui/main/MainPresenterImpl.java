package com.simx.riskiprojects.ui.main;

import android.content.Context;

import com.google.firebase.firestore.DocumentSnapshot;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.data.remote.FirebaseAuthService;

/**
 * Created by simx on 14/02/18.
 */

public class MainPresenterImpl {
    MainView mainView;


    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;

    }


    public void logout(Context context){

    }
}
