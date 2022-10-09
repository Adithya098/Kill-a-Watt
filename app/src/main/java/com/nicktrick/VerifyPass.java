package com.nicktrick;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nicktrick.firebase.Person;
import com.nicktrick.usage.Usage;
import com.nicktrick.usage.UsageAdapter;
import com.nicktrick.usage.Usagebean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyPass extends AppCompatActivity {
    ListView listView;
    private DatabaseReference databaseReference;
    private UsageAdapter usageAdapter;
   // List<String> val = new ArrayList<>();
    Map<String, String> valmap = new HashMap<>();
    private List<Usagebean> listPerson = new ArrayList<>();
    String uid="",pws="";
    EditText getvpass;
    Button verify;
    int carid = 0;
    int val=0;
    int gunits=0;




    public static final String SHARED_PREF_NAME = "mysharedpref";
    public static final String KEY_NAME = "keyname";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifypassword);
        listView = findViewById(R.id.usagelistView);
        verify = findViewById(R.id.btn_verifypass);
        getvpass = findViewById(R.id.get_vfpass);
       // databaseReference = FirebaseDatabase.getInstance().getReference("usage");
        usageAdapter = new UsageAdapter(this, listPerson);


       // SharedPreferences btnpref = getApplicationContext().getSharedPreferences("Btnpref", Context.MODE_PRIVATE);

        uid=getIntent().getStringExtra("uid");
        Log.e("print uid",uid);
       // addChildEventListener();
        verify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        String name = getvpass.getText().toString();
        SharedPreferences sp =getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_NAME,name);
        editor.apply();
        getvpass.setText("");


    }
});

    }



    }
