package com.nicktrick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
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
import java.util.concurrent.TimeUnit;

public class QrCodeScan extends AppCompatActivity {
    public static final int MODE_PRIVATE = 0;
    public static final int RESULT_OK = -1;
    public static final int RESULT_CANCELED = 0;
    private ImageView Clickbtn;
    private Button Sendbtn;
    String getcode="", contents, format,mbl,uid;
    private DatabaseReference databaseReference;
    List<String> val =new ArrayList<>();
    Map<String,String> valmap = new HashMap<>();
    private EditText Gotp;
    private Button verify;
    private TextView SendOtp;
    String otp = "",otpset="";
    String mobilenum="",unid="";

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscan);
        Clickbtn = findViewById(R.id.btnqrs);
        SendOtp = findViewById(R.id.send_otp);
        databaseReference = FirebaseDatabase.getInstance().getReference("register");
        Gotp = findViewById(R.id.get_otp);
        verify = findViewById(R.id.btn_verify);
        fAuth = FirebaseAuth.getInstance();
        FirebaseCrash.report(new Exception("e.println"));

       getcode = getIntent().getStringExtra("code");
        //getcode = "3004";
     //   mobilenum = getIntent().getStringExtra("phn");
     //   unid = getIntent().getStringExtra("uid");
      /*  if(!mobilenum.equals(null)){
            sendVerficationCode();
        }*/


        addChildEventListener();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String myotp = Gotp.getText().toString().trim();
               verifySignInCode();
              /*  Intent it = new Intent(getApplicationContext(),NameReg.class);
                it.putExtra("uid",getcode.trim());
                startActivity(it);*/

            }
        });





        SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!valmap.equals(null)) {
                    for (Map.Entry<String, String> entry : valmap.entrySet()) {
                        String key = entry.getKey();
                        Log.e("inside qr", key);

                        if (key.trim().equalsIgnoreCase(getcode.trim())) {

                            mobilenum = entry.getValue();
                            sendVerficationCode(mobilenum);
                            Toast.makeText(getApplicationContext(),"---"+mobilenum, Toast.LENGTH_SHORT).show();


                        } else {
                           // Toast.makeText(getApplicationContext(), "Wrong Qr code", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No values in map", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void addChildEventListener(){
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

                Log.e("ehashmap---",""+valmap);
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
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


             // startActivity(new Intent(getApplicationContext(),QrCodeScan.class));

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("ERR",e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            otpset=s;
        }
    };
    private void sendVerficationCode(String mobile){

        String phone = mobile;


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,// Phone number to verify
                50,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private void verifySignInCode(){
        try {
            Log.e("otp", otpset);
            String otper=Gotp.getText().toString().trim();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpset,otper);
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(QrCodeScan.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("ERR",""+task);
                            Intent it = new Intent(getApplicationContext(),NameReg.class);
                            it.putExtra("uid",getcode.trim());
                            startActivity(it);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.e("ERR",""+task);
                                Toast.makeText(getApplicationContext(), "Incorrect Verification Code", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


}
