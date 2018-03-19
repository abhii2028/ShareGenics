package com.example.itsurboy.sharegenics;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import dmax.dialog.SpotsDialog;

public class DataBase extends AppCompatActivity {

    private DatabaseReference mdatabase;
    ListView lv;
    List<Artist> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        lv = (ListView)findViewById(R.id.list_view);
        user = new ArrayList<>();

        final AlertDialog dialog = new SpotsDialog(DataBase.this,R.style.data);
        dialog.show();
       mdatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               dialog.dismiss();
               fetchData(dataSnapshot);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }



    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DataBase.this,home.class));
        finish();
    }

 @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(DataBase.this,home.class));
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        user.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Artist a = ds.getValue(Artist.class);
            user.add(a);

        }
        ArtistList adapter = new ArtistList(DataBase.this,user);
        lv.setAdapter(adapter);
    }

}
