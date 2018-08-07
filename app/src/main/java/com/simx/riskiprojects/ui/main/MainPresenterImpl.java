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
    FirebaseAuthService firebaseAuthService;


    public MainPresenterImpl(MainView mainView, FirebaseAuthService firebaseAuthService) {
        this.mainView = mainView;
        this.firebaseAuthService = firebaseAuthService;
    }

    public void geDataUser(String uid) {
        firebaseAuthService
                .getDocumentUser(uid)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()){
                        UserModel userModel = snapshot.toObject(UserModel.class);
                        mainView.initProfile(userModel);
                    }
                });
    }
    public void logout(Context context){

    }
}
