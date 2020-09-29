package Utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}
