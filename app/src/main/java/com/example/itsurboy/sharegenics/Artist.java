package com.example.itsurboy.sharegenics;

/**
 * Created by ITS UR BOY on 30-04-2017.
 */

public class Artist  {
    String id,username,description,contactno;

    public Artist()
    {

    }

    public Artist(String id, String username, String description, String contactno) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.contactno = contactno;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public String getContactno() {
        return contactno;
    }
}
