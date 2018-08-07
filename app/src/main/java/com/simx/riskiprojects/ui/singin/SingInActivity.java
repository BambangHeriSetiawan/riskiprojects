package com.simx.riskiprojects.ui.singin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.di.base.BaseActivity;
import com.simx.riskiprojects.helper.AppConst;

import java.util.Map;

import javax.inject.Inject;

public class SingInActivity extends BaseActivity implements SinginView {

    @Inject SingInPresenterImp presenterImp;
    @Inject UserModel userModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, SingInActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       presenterImp.singinUser();

    }

    @Override
    public void onLoadSingin(Intent intent) {
        startActivityForResult(intent, AppConst.RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConst.RC_SIGN_IN && resultCode == RESULT_OK){
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            presenterImp.checkInfoUserInDatabase(mUser);
        }
    }
}
