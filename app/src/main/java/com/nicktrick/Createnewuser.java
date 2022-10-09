package com.nicktrick;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nicktrick.firebase.EditPersonActivity;
import com.nicktrick.firebase.MainActivity;
import com.nicktrick.usage.UsageActivity;

import java.util.HashMap;
import java.util.Map;

public class Createnewuser extends AppCompatActivity{


    Button b,btnscn,userbtn,usagebtn;
    ScrollView scrollview;
    LinearLayout linearLayout ;
    LinearLayout linear1;
    int i=0;
    SharedPreferences btnpref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        btnscn = findViewById(R.id.btn_scan);

        scrollview = new ScrollView(this);
        linearLayout = findViewById(R.id.btn_layout1);
        userbtn = findViewById(R.id.add_user);
        usagebtn = findViewById(R.id.add_usage);

        try{
            btnpref = getApplicationContext().getSharedPreferences("Btnpref", Context.MODE_PRIVATE);
          // if (!btnpref.equals(null)){
               Map<String,?> keys = btnpref.getAll();
            linear1= new LinearLayout(this);
            linear1.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(linear1);
               for(Map.Entry<String,?> entry : keys.entrySet()) {
                   Log.e("map values", entry.getKey() + ": " + entry.getValue().toString());
                   // String sval = btnpref.getString("0002", "");
                   String sval = entry.getValue().toString().trim();
                   int vid = Integer.parseInt(entry.getKey());
                   String[] sarray = sval.split("@");


                   b = new Button(this);
                   b.setText(sarray[0]);
                   b.setId(vid);
                   b.setTextSize(10);
                   b.setPadding(8, 3, 8, 3);
                   b.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
                   b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                   linear1.addView(b);

                   i++;
                   b.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {


                           Intent it = new Intent(getApplicationContext(), Graphview.class);
                           it.putExtra("uid", String.valueOf(b.getId()));
                           Log.e("uid-onclick", String.valueOf(b.getId()));
                           startActivity(it);
                       }
                   });
               }
                   linearLayout.addView(linear1);


        //   }











        }catch (Exception e){
            e.printStackTrace();
        }
        btnscn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),QRScanner.class);
                startActivity(it);
            }
        });
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), EditPersonActivity.class);
                startActivity(it);
            }
        });
        usagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),UsageActivity.class);
                startActivity(it);
            }
        });

    }



}
