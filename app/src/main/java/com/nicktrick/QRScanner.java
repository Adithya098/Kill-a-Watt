package com.nicktrick;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicktrick.firebase.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QRScanner extends AppCompatActivity {
    private Button newuser;
    private String contents,format;
    private DatabaseReference databaseReference;
    Map<String,String> valmap = new HashMap<>();
    List<String> val =new ArrayList<>();
    String getcode,mbl,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanner);
        newuser = findViewById(R.id.btn_newuser);
       // databaseReference = FirebaseDatabase.getInstance().getReference();

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }
        });

    }
   /* private void addChildEventListener() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Person person = dataSnapshot.getValue(Person.class);
                if (person != null) {
                    person.setKey(dataSnapshot.getKey());
                    if(val.contains(person.getUniqid())){

                    }else{
                        val.add( person.getUniqid());
                        valmap.put(person.getUniqid(),person.getMobile());
                        uid = person.getUniqid();
                        mbl = person.getMobile();
                        Log.e("res--mob--num---uid", mbl + "-----" + uid);

                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            String dates = "";
            String time = "";
            if (requestCode == 0) {
                if (resultCode == RESULT_OK) {
                    contents = intent.getStringExtra("SCAN_RESULT");
                    format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                   // addChildEventListener();

                    String pnum = contents;
                    Log.e("code", pnum);
                    Intent it = new Intent(getApplicationContext(),QrCodeScan.class);
                    it.putExtra("code",pnum);
                    startActivity(it);
                  //  Toast.makeText(getApplicationContext(),"qr "+pnum,Toast.LENGTH_SHORT).show();
                   /* for(Map.Entry<String,String> entry : valmap.entrySet()) {
                        String key = entry.getKey();
                        Log.e("inside qr",key);

                        if(key.trim().equals(pnum.trim())) {

                            String phnval = entry.getValue();
                            Intent it = new Intent(getApplicationContext(),QrCodeScan.class);
                            //it.putExtra("phn",phnval);
                            it.putExtra("uid",pnum);
                            startActivity(it);
                        }else{
                            // Toast.makeText(getApplicationContext(),"Wrong Qr code",Toast.LENGTH_SHORT).show();
                        }


                        // do what you have to do here
                        // In your case, another loop.
                    }*/


                }
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast.makeText(getApplicationContext(),
                        "Sorry Scan cancelled", Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}



