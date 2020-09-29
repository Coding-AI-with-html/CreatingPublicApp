package LoginAndRegister;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.something.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Utils.FirebaseContext;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    /**
     * Firebase variables
     */
    private FirebaseAuth mAUth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference DReference;
    private FirebaseDatabase firebaseD;
    private FirebaseContext FContext;
    //widgets
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

        mContext = Register.this;
        FContext = new FirebaseContext(mContext);
        Log.d(TAG, "onCreate: started register activity");
        initWidgets();
        Init();
        setupFirebaseAuthenticator();
    }


    private void Init() {
        
    }




}
