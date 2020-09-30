package Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class FirebaseContext {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  String userUID;
    private Context mContext;
    private FirebaseDatabase FData;
    private DatabaseReference DataRefs;

    public  FirebaseContext(Context context){
        FData = FirebaseDatabase.getInstance();
        DataRefs = FData.getReference();
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        if(mAuth.getCurrentUser() != null){
            userUID = mAuth.getCurrentUser().getUid();
        }
    }

    public void registerNewUser(final String email, String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //sending email verification
                            sentVerification();

                            Log.d(TAG, "onComplete: CreateUserWithEmail");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            Log.d(TAG, "failure while registering new user", task.getException());
                            Toast.makeText(mContext, "Authentication failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void updateUI(FirebaseUser user) {
        userUID = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "updateUI: " + userUID);
    }

    public void sentVerification() {
        FirebaseUser Fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(Fuser != null) {
            Fuser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext,"Couldint sent email verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
