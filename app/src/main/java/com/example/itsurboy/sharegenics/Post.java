package com.example.itsurboy.sharegenics;


import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Post extends AppCompatActivity {

    private Button postbtn;
    private LinearLayout ll;
    private EditText name,details,contact;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postbtn = (Button) findViewById(R.id.pbtn);
        ll = (LinearLayout)findViewById(R.id.activity_post);
        name = (EditText)findViewById(R.id.pname);
        details = (EditText)findViewById(R.id.pdetails);
        contact = (EditText)findViewById(R.id.pcno);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               addArtist();
            }
        });



    }

    public void addArtist()
    {
        String sname = name.getText().toString();
        String sdetails = details.getText().toString();
        String scontacts = contact.getText().toString();
        if (TextUtils.isEmpty(sdetails) || TextUtils.isEmpty(scontacts))
        {
            Snackbar s = Snackbar.make(ll, "Some Field is Empty...!!", Snackbar.LENGTH_SHORT);
            View sbView = s.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            s.show();
        }
        else {
            String id = mDatabase.push().getKey();
            Artist artist = new Artist(id,sname,sdetails,scontacts);
            mDatabase.child(id).setValue(artist);
            Snackbar s = Snackbar.make(ll, "Thank You For DONATION...", Snackbar.LENGTH_SHORT);
            View sbView = s.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            s.show();
        }
    }
    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Post.this,home.class));
        finish();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Post.this,home.class));
    }


}
