package com.nicktrick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicktrick.db.SQLite;

import java.util.List;

public class Graphview extends AppCompatActivity {
private Button pie,bar,threshold,bill;
    DatabaseReference databaseReference,databaseReference2;
    AlertDialog builder;
    String edate;
    int carid = 0;
    int val=0;
    int gunits=0;
    String uid=null;
    SQLite db = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphview);
        pie = findViewById(R.id.get_piechart);
        bar = findViewById(R.id.get_barchart);
        threshold = findViewById(R.id.set_threshold);
        bill = findViewById(R.id.bill_pay);
        uid = getIntent().getStringExtra("uid");
        databaseReference = FirebaseDatabase.getInstance().getReference(uid.trim());
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
        db = new  SQLite(getApplicationContext());

        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),Graph.class);
                it.putExtra("uid",uid);
                startActivity(it);
            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),BillPay.class);
                it.putExtra("uid",uid);
                startActivity(it);
            }
        });
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),BarGraph.class);
                it.putExtra("uid",uid);
                startActivity(it);
            }
        });

        threshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPin(uid);
            }
        });
    }
    public void verifyPin(final String meterpin) {
        LinearLayout layout = new LinearLayout(Graphview.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        builder = new AlertDialog.Builder(Graphview.this).create();
        builder.setTitle("Security Check");
        builder.setMessage("Enter Password");


        final EditText pass = new EditText(Graphview.this);

        pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pass.setHint("Enter Pass");
        layout.addView(pass);
        builder.setView(layout);

        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String str  = db.user_detail(meterpin,pass.getText().toString().trim());
                if(str.equalsIgnoreCase("Ok")){
                    Intent it = new Intent(getApplicationContext(),VerifyPass.class);
                    it.putExtra("uid",uid);
                    startActivity(it);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your credentials !",Toast.LENGTH_SHORT).show();
                }




            }
        });

        builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
