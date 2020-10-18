package com.example.something;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import LoginAndRegister.Login;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;

    /**
     * firebase
     */
    private FirebaseAuth FAuth;
    private FirebaseAuth.AuthStateListener FAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

        setupFirebaseAuthentication();
    }

    /**
     * Scan user local data if he had already acc, if not, go to register, if logged out, go loggin system, if still logged stay at main activity
     *
     */
    public void checkCurrentUser(FirebaseUser Fuser){

        Log.d(TAG, "checkCurrentUser: checkign if there is firebae user");
        if(Fuser == null) {
            Intent intent = new Intent(mContext, Login.class);
            startActivity(intent);
            
        }
    }
    
    private void setupFirebaseAuthentication(){

        Log.d(TAG, "setupFirebaseAuthentication: starting authentication");

        FAuth = FirebaseAuth.getInstance();

        FAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                //check if user logged in
                checkCurrentUser(currentUser);
                if(currentUser != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in" + currentUser.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        FAuth.addAuthStateListener(FAuthListener);
        checkCurrentUser(FAuth.getCurrentUser());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(FAuthListener != null){
            FAuth.removeAuthStateListener(FAuthListener);
        }
    }



}