package com.simx.riskiprojects.ui.splash;

import com.google.firebase.auth.FirebaseUser;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.data.remote.FirebaseAuthService;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by simx on 14/02/18.
 */

public class SplashPresenterImpl implements SplashPresenter {
    SplashView splashView;
    FirebaseAuthService firebaseAuthService;
    @Inject
    public SplashPresenterImpl(SplashView splashView, FirebaseAuthService firebaseAuthService) {
        this.splashView = splashView;
        this.firebaseAuthService = firebaseAuthService;
    }

    @Override
    public void loadSplash() {
        splashView.showProgress(true);
        FirebaseUser user = firebaseAuthService.getCurrentUser();
        if (user == null){
            splashView.showProgress(false);
            splashView.gotoLogin();
        }else {
            Calendar calendar = Calendar.getInstance();
            calendar.getTimeInMillis();
            long createAt = calendar.getTimeInMillis();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            String createAtFormated = mYear+"_"+mMonth+"_"+mDay;
            UserModel userModel = new UserModel();
            userModel.setUid(user.getUid());
            userModel.setEmail(user.getEmail());
            userModel.setName(user.getDisplayName());
            userModel.setPhone(user.getPhoneNumber());
            userModel.setProviderId(user.getProviderId());
            userModel.setPhotoUri(user.getPhotoUrl().toString());
            userModel.setCreateAt(createAt);
            userModel.setCreateAtFormated(createAtFormated);
            firebaseAuthService.getDocumentUser(user.getUid()).get().addOnCompleteListener(task -> {
                if (!task.getResult().exists()){
                    createUserDatbase(userModel);
                }else {
                    splashView.showProgress(false);
                    splashView.gotoMain(user.getUid());
                }
            });


        }
    }

    private void createUserDatbase(UserModel user) {
        firebaseAuthService.frCreateUser(user).addOnCompleteListener(task -> {
            splashView.showProgress(false);
            splashView.gotoMain(user.getUid());
        }).addOnFailureListener(e -> {
            splashView.showError(e.getMessage());
        });
    }
}
