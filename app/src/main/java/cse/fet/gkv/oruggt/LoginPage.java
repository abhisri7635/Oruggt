package cse.fet.gkv.oruggt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends Activity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    SignInButton gl;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
    EditText em, pw, cpw;
    DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference dm= FirebaseDatabase.getInstance().getReference().child("userlist");
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gl = findViewById(R.id.sgngoogle);
        gl.setSize(SignInButton.SIZE_STANDARD);
        gl.setColorScheme(SignInButton.COLOR_DARK);
        gl.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.cred)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();
        em = findViewById(R.id.email);
        pw = findViewById(R.id.password);
        cpw = findViewById(R.id.cnfpwd);
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String t=(String)dataSnapshot.getValue();
                if (t==null)
                    i=0;
                else
                i=Integer.parseInt(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void sgnem(View v) {
        String eml = em.getText().toString(), pwd = pw.getText().toString(), cpwd = cpw.getText().toString();
        if (TextUtils.isEmpty(eml) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cpwd)) {
            if (TextUtils.isEmpty(eml))
                em.setHint("Email Field cannot be left empty");
            if (TextUtils.isEmpty(pwd))
                pw.setHint("Password Field cannot be left empty");
            if (TextUtils.isEmpty(cpwd))
                cpw.setHint("Confirm Password Field cannot be left empty");
        } else {
            if (cpwd.contentEquals(pwd)) {
                auth.createUserWithEmailAndPassword(eml, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            add(user.getUid());
                            Toast.makeText(LoginPage.this, "You have created an account successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginPage.this, UserInfo.class);
                            startActivity(i);
                            startService(new Intent(LoginPage.this,LocationStarter.class));
                            finish();
                        }

                    }

                });
            }
            else Toast.makeText(this,"Password Mismatch. Please Retry",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==9001){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("Error", "Google sign in failed"+e);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            add(user.getUid());
                            Toast.makeText(LoginPage.this,"Successfully Signed up",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginPage.this, UserInfo.class);
                            startService(new Intent(LoginPage.this,LocationStarter.class));
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "signInWithCredential:failure", task.getException());

                        }


                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent stg = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(stg, 9001);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void add(String p){
        i++;
        SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        SharedPreferences.Editor e=sp.edit();
        e.putString("userid","user_"+i);
        e.apply();
        dm=dm.child("user_"+i);
      dr.setValue(""+i);
      dm.setValue(p);
    }
}