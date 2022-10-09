package com.nicktrick.usage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicktrick.R;
import com.nicktrick.db.CreateDb;
import com.nicktrick.firebase.Person;

import org.parceler.Parcels;

import java.util.Random;

public class EditUsageActivity extends AppCompatActivity {

    private EditText editUsage;
    private EditText editUid;
    private EditText editdate;

    private Button button;

    private DatabaseReference databaseReference;

    private Usage usage = new Usage();

    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person1);
        CreateDb cdc =new CreateDb();
        databaseReference = FirebaseDatabase.getInstance().getReference("usage");

        initUI();
        setButtonOnClickListener();
        handleBundle();
        initUIFromPerson();
    }

    private void initUI(){
        editUsage = findViewById(R.id.get_usage);
        editUid = findViewById(R.id.get_unicode1);
        editdate = findViewById(R.id.get_date);

        button = findViewById(R.id.btn_save);
    }

    private void initUIFromPerson(){
        editUsage.setText(usage.getUsage());
        editUid.setText(usage.getUniqid());
        editdate.setText(usage.getDater());

    }

    private void setButtonOnClickListener(){
        button.setOnClickListener(e -> {
            String uusage = editUsage.getText().toString();
            String unid = editUid.getText().toString();
            String pdate = editdate.getText().toString();


            usage.setUsage(uusage);
            usage.setUniqid(unid);
            usage.setDater(pdate);




            if(edit){
                databaseReference.child(usage.getKey()).setValue(usage);
            }else{
                String key = databaseReference.push().getKey();
                usage.setKey(usage.getUniqid());
                //here i set key nto uniqid
                databaseReference.child(key).setValue(usage);
            }
            finish();
        });
    }

    private void handleBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            edit = bundle.getBoolean("edit");
            if(edit){
                usage = Parcels.unwrap(bundle.getParcelable("usage"));
            }
        }
    }
}