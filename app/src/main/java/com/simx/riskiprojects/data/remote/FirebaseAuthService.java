package com.simx.riskiprojects.data.remote;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.simx.riskiprojects.data.model.UserModel;
import com.simx.riskiprojects.helper.AppConst;

import java.util.Arrays;
import java.util.List;

/**
 * Created by simx on 14/02/18.
 */

public class FirebaseAuthService {

    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage storage;
    private Query query;
    public FirebaseAuthService(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.rootRef = firebaseDatabase.getReference();
        this.firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public  Intent auth(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
        );
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

    }
    /*AUTH*/
    public  FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public Task<Void> logout(Context context){
        return AuthUI.getInstance().signOut(context);
    }

    public Task<Void> delete(Context context){
        return AuthUI.getInstance().delete(context);
    }

    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }


    /*DATABASE FIRESTORE TYPE*/

    public DocumentReference getDocumentUser(String uid){
        return firebaseFirestore.collection("Users").document(uid);
    }
    public Task<Void> frCreateUser(UserModel userModel){
        return firebaseFirestore.collection("Users").document(userModel.getUid()).set(userModel);
    }
}
