package com.nicktrick;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nicktrick.db.CreateDb;


public class UserRegis extends AppCompatActivity{
   private EditText ucode,phn,uname,password;
   private String unic="",phne="",user="", pwd ="";
   private Button regis;
   FirestoreUserModel firestoreUserModel;


   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regname);
       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);


        FirebaseCrash.report(new Exception("e.println"));


        ucode = findViewById(R.id.getunicode);
        phn = findViewById(R.id.getphn);
        uname = findViewById(R.id.getuser);
        password = findViewById(R.id.getpass);
        regis = findViewById(R.id.btn_registerdb);

       CreateDb cdb=new CreateDb();

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unic=ucode.getText().toString().trim();
                phne=phn.getText().toString().trim();
                user=uname.getText().toString().trim();
                pwd =password.getText().toString().trim();
                Log.e("strings",unic+"--"+phne+"--"+user+"--"+ pwd);
                try {


                    CreateDb.register.child("register").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean test=true;
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                Log.e("TEST","Inside");
                                FirestoreUserModel firestoreUserModel = dataSnapshot1.getValue(FirestoreUserModel.class);
                              /*  if(details.getUniquecode().equals(unic))
                                {
                                    Log.e("TEST","Inside the if");
                                    ucode.setError("EmailId is Already Exist..");
                                    ucode.requestFocus();
                                    test=false;
                                }
                                else
                                {
                                }
                            }
                            if(test)
                            {*/
                           //   FirestoreUserModel firestoreUserModel = new FirestoreUserModel();
                                firestoreUserModel.setPassword(pwd);
                                firestoreUserModel.setPhonenum(phne);
                                firestoreUserModel.setUniquecode(unic);
                                firestoreUserModel.setUsername(user);

                                CreateDb.register.child("register").push().setValue(firestoreUserModel);
                                Intent intent=new Intent(getApplicationContext(),Createnewuser.class);
                                startActivity(intent);
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                   // addNewUser(firestoreUserModel);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }



}
