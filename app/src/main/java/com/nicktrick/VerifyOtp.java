package com.nicktrick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {
    private EditText Gotp;
    private Button verify;
    String otp = "",otpset="";
    String mobilenum="",unid="";

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifyotp);
        Gotp = findViewById(R.id.get_otp);
        verify = findViewById(R.id.btn_verify);
        fAuth = FirebaseAuth.getInstance();


        FirebaseCrash.report(new Exception("e.println"));

        SharedPreferences preferences = getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        mobilenum = getIntent().getStringExtra("phn");
        unid = getIntent().getStringExtra("uid");


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String myotp = Gotp.getText().toString().trim();
              // verifySignInCode();
                Intent it = new Intent(getApplicationContext(),NameReg.class);
                it.putExtra("uid",unid);
                startActivity(it);

            }
        });


    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


            //  startActivity(new Intent(getApplicationContext(),LoginActivity.class));

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
    private void sendVerficationCode(){
        String phone = mobilenum;


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,// Phone number to verify
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
                .addOnCompleteListener(VerifyOtp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("ERR",""+task);
                            Intent it = new Intent(getApplicationContext(),NameReg.class);
                            it.putExtra("uid",unid);
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
