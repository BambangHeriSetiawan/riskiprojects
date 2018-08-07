package com.simx.riskiprojects.ui.singin;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.data.remote.FirebaseAuthService;

/**
 * Created by simx on 14/02/18.
 */

public class SingInPresenterImp {
    SinginView singinView;
    FirebaseAuthService firebaseAuthService;
    UserModel userModel;
    public SingInPresenterImp(SinginView singinView, FirebaseAuthService firebaseAuthService, UserModel userModel) {
        this.singinView = singinView;
        this.firebaseAuthService = firebaseAuthService;
        this.userModel = userModel;
    }

    public void singinUser() {
        Intent intent = firebaseAuthService.auth();
        singinView.onLoadSingin(intent);
    }

    public void checkInfoUserInDatabase(FirebaseUser mUser) {
        Log.e("SingInPresenterImp", "checkInfoUserInDatabase: " + mUser.toString());
    }
}
