package com.example.itsurboy.sharegenics;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import dmax.dialog.SpotsDialog;

public class UserPanel extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText memail;
    private EditText mpassword;
    private Button mloginbtn;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LinearLayout ll;
    private CardView g;
    private static final int RC_SIGN_IN =1;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        final AlertDialog dialog = new SpotsDialog(UserPanel.this,R.style.welcome);

        mAuth = FirebaseAuth.getInstance();
        memail = (EditText)findViewById(R.id.emailetu);
        mpassword = (EditText)findViewById(R.id.passwordetu);
        mloginbtn = (Button)findViewById(R.id.loginbtn);
        ll = (LinearLayout)findViewById(R.id.activity_user_panel);
        g = (CardView)findViewById(R.id.googlecard);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Snackbar s = Snackbar.make(ll, "Error Occur...", Snackbar.LENGTH_SHORT);
                        View sbView = s.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.GREEN);
                        s.show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                dialog.show();
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                dialog.dismiss();
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(UserPanel.this,Post.class));
                }
            }
        };
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSignIn();
                String email = memail.getText().toString();
                String password = mpassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                {
                    Snackbar s = Snackbar.make(ll, "Some Field Is Empty..", Snackbar.LENGTH_SHORT);
                    View sbView = s.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.GREEN);
                    s.show();

                }else {
                    final AlertDialog dialog = new SpotsDialog(UserPanel.this,R.style.welcome);
                    dialog.show();
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar s = Snackbar.make(ll, "Authentication Problem...", Snackbar.LENGTH_SHORT);
                            View sbView = s.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.GREEN);
                            s.show();

                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }
    private void startSignIn() {
        String email = memail.getText().toString();
        String password = mpassword.getText().toString();


        if(  TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Snackbar s = Snackbar.make(ll, "Some Field Is Empty..", Snackbar.LENGTH_SHORT);
            View sbView = s.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            s.show();
        }
        else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mAuth.getCurrentUser();
                    }else {
                        Snackbar s = Snackbar.make(ll, "Login Failed..!!", Snackbar.LENGTH_SHORT);
                        View sbView = s.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.GREEN);
                        s.show();
                    }

                }
            });
        }

    }

    public void sign (View view){

        startActivity(new Intent(UserPanel.this,Signup.class));
    }


}
