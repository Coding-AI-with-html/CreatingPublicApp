package com.example.something.singup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.something.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Utils.FirebaseContext;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    /**
     * Firebase variables
     */
    private FirebaseContext firebaseContext;
    private FirebaseAuth mAUth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference DReference;
    private FirebaseDatabase firebaseD;
    private FirebaseContext FContext;
    //widgets
    private TextView loadingRegisterText;
    private Context mContext;
    private EditText mEmail,mUsername,mPassword;
    private String email, username, password;
    private Button btnForRegister;
    private ProgressBar RegisterprogressBar;

    private String append = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mContext = getApplicationContext();
        FContext = new FirebaseContext(mContext);
        Log.d(TAG, "onCreate: started register activity");
        initWidgets();
        Init();
        setupFirebaseAuthentication();
    }


    private void Init() {
        btnForRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Newveikia", Toast.LENGTH_SHORT).show();
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                username = mUsername.getText().toString();

                if(checkInputs(email, password, username)){
                    RegisterprogressBar.setVisibility(View.VISIBLE);
                    FContext.registerNewUser(email, password, username);
                }
            }
        });

    }



    private boolean checkInputs(String email, String password, String username) {
        Log.d(TAG, "checkInputs: Checking user inputs");
        if(email.equals("") || password.equals("") || username.equals("")){
            Toast.makeText(mContext, "All fields shouldin't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return false;

    }
    
    //instaliasing widgets
    private void initWidgets(){
        Log.d(TAG, "initWidgets: initializing widgets");
        btnForRegister = (Button) findViewById(R.id.btn_register);
        RegisterprogressBar = (ProgressBar) findViewById(R.id.progres_bar_register);
        loadingRegisterText = (TextView) findViewById(R.id.please_whait_sign);
        mEmail = (EditText) findViewById(R.id.input_email);
        mUsername = (EditText) findViewById(R.id.input_username);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = Register.this;
        RegisterprogressBar.setVisibility(View.GONE);
        loadingRegisterText.setVisibility(View.GONE);

    }


    /*
    ---------------------FIREBASE CONTENT---------------------
     */
    
    private boolean checkingString(String email, String password, String username) {
        Log.d(TAG, "String: checking if string are not epemty");

        if(email.equals("") || password.equals("") || username.equals("")){
            return true;

        } else {
         return false;
        }
    }

    private void setupFirebaseAuthentication() {
        Log.d(TAG, "setupFirebaseAuthentication: starting authentication");

        mAUth = FirebaseAuth.getInstance();
        firebaseD = FirebaseDatabase.getInstance();

        DReference = firebaseD.getReference();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if(currentUser != null) {
                    Log.d(TAG, "onAuthStateChanged: Signed_in" + currentUser.getUid());


                    DReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //checking if user not already taked
                            if(firebaseContext.checkFusernameExist(username, dataSnapshot)){
                                append = DReference.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: Username already exist" + append);

                            }
                            username = username+append;

                            firebaseContext.addNewUser(email, username, "");
                            Log.d(TAG, "onDataChange: Signed in sucssefully" + username);
                            Toast.makeText(mContext, "Signed in!", Toast.LENGTH_SHORT).show();

                            mAUth.signOut();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    finish();
                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAUth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    mAUth.addAuthStateListener(mAuthStateListener);
    }
}
