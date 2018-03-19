package com.example.itsurboy.sharegenics;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;


public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText memail;
    private EditText mpassword;
    private Button msignbtn;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        memail = (EditText)findViewById(R.id.siemail);
        mpassword = (EditText)findViewById(R.id.sipassword);
        ll = (LinearLayout)findViewById(R.id.activity_signup);
        msignbtn= (Button)findViewById(R.id.signbtn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Signup.this,DataBase.class));
                }
            }
        };


        msignbtn.setOnClickListener(new View.OnClickListener() {
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
                    final AlertDialog dialog = new SpotsDialog(Signup.this,R.style.acc_create);
                    dialog.show();
                }
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


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                mAuth.getCurrentUser();
                            }
                            else {

                                Snackbar s = Snackbar.make(ll, "Error Dear...!!", Snackbar.LENGTH_SHORT);
                                View sbView = s.getView();
                                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                textView.setTextColor(Color.GREEN);
                                s.show();
                            }
                        }
                    });


        }

    }

}
