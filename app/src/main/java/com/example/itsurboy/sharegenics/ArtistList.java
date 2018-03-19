package com.example.itsurboy.sharegenics;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ITS UR BOY on 01-05-2017.
 */

public class ArtistList extends ArrayAdapter<Artist>{
   private Activity context;
    private List<Artist> artistList;

    public ArtistList (Activity context, List<Artist> artistList)
    {
        super(context,R.layout.model,artistList);
        this.context=context;
        this.artistList=artistList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.model,null,true);

        TextView txtname = (TextView) listViewItem.findViewById(R.id.valuename);
        TextView txtdetail = (TextView) listViewItem.findViewById(R.id.valuedetails);
        TextView txtcno = (TextView) listViewItem.findViewById(R.id.valuecontact);

        Artist artist = artistList.get(position);
        txtname.setText(artist.getUsername());
        txtdetail.setText(artist.getDescription());
        txtcno.setText(artist.getContactno());

        return listViewItem;


    }
}
