package com.nicktrick.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by redhat on 2/8/2018.
 */

public class FetchDbData
{
    static DatabaseReference root,dbName;
    public static String register(String tableName, String columnName, String databaseName)
    {
        root= FirebaseDatabase.getInstance().getReference();
        dbName=root.child(databaseName);

        return "";
    }
}
