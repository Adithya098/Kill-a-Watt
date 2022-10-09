package com.nicktrick.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *
 */

public class CreateDb
{
    public static DatabaseReference root,register,usage;
    public CreateDb()
    {
        root= FirebaseDatabase.getInstance().getReference();
        usage=root.child("usage");
        register=root.child("register");

    }
}
