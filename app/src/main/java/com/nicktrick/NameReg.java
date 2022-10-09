package com.nicktrick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nicktrick.db.SQLite;

import java.util.HashSet;
import java.util.Set;

public class NameReg extends AppCompatActivity {
    private EditText name,pass;
    private Button okbtn;
    String nicknm="",pwd="";
    Set<Integer> nset = new HashSet<>();
    int i=1;
    String uid="";
    static int j=0;
    SharedPreferences btnpref;
    SQLite db =null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namereg);

        name = findViewById(R.id.enter_name);
        pass = findViewById(R.id.enter_pwd);
        okbtn = findViewById(R.id.btn_ok);
        uid=getIntent().getStringExtra("uid");
        Log.e("uid----",uid);
        db = new  SQLite(getApplicationContext());
        btnpref = getApplicationContext().getSharedPreferences("Btnpref",Context.MODE_PRIVATE);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Shared pref content update
                nicknm=name.getText().toString().trim();
                pwd=pass.getText().toString().trim();
                db.user_register(nicknm,pwd,uid,"0");
                nset.add(i);
                SharedPreferences.Editor editor = btnpref.edit();
                editor.putString(uid,nicknm+"@"+pwd);
                editor.commit();

                Log.e("shared",btnpref.getAll().toString());
              //  if(!nicknm.equalsIgnoreCase("")&&(pwd.equalsIgnoreCase(""))){
                    Intent it = new Intent(NameReg.this,Createnewuser.class);
                    startActivity(it);
               // }


            }
        });



    }
}
