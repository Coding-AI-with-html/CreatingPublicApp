package LoginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.something.MainActivity;
import com.example.something.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView PleaseWhait;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mProgressBar = (ProgressBar) findViewById(R.id.progres_bar_login);
        PleaseWhait = (TextView) findViewById(R.id.please_whait_sign);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = Login.this;
        //Firebase
        Init();
        setupFirebaseAuth();
        Log.d(TAG, "onCreate: started");

        PleaseWhait.setVisibility(View.GONE);

        mProgressBar.setVisibility(View.GONE);

    }

    /*---------------------------------------------------------
                                  FIREBASE
    -------------------------------------------------------------*/

    private boolean checkingString(String string) {
        Log.d(TAG, "checkingString: checking if fields are not epemty");

        if(string.equals("")){
            return true;
        } else {
            return false;
        }
    }

    private void Init() {

        //intializing button for loggin
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: going into loggin in");

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(checkingString(email) && checkingString(password)){
                    Toast.makeText(mContext, "These fields can't be epemty", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    PleaseWhait.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser Fuser = mAuth.getCurrentUser();




                                    if(task.isSuccessful()){
                                        try {
                                            Log.d(TAG, "onComplete: succses with meila verification");

                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);

                                            
                                        } catch (Exception e){
                                            Log.d(TAG, "onComplete: NullPointerException" + e.getMessage());
                                        }
                                    } else {

                                        Log.d(TAG, "SignWithEmail:failed", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        TextView signOutLink = (TextView) findViewById(R.id.link_signup);
        signOutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Signing_out");
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }


    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
        }

    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: starting authenticator");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in" + firebaseUser.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
