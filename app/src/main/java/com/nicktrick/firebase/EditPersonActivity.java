package com.nicktrick.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicktrick.R;
import com.nicktrick.db.CreateDb;

import org.parceler.Parcels;

public class EditPersonActivity extends AppCompatActivity {

    private EditText editUserName;
    private EditText editUid;
    private EditText editmobile;
    private EditText editpass;
    private EditText editusage;
    private EditText editdate;
    private Button button;

    private DatabaseReference databaseReference;

    private Person person = new Person();

    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        CreateDb cdc = new CreateDb();


        databaseReference = FirebaseDatabase.getInstance().getReference("register");

        initUI();
        setButtonOnClickListener();
        handleBundle();
        initUIFromPerson();
    }

    private void initUI(){
        editUserName = findViewById(R.id.get_user);
        editUid = findViewById(R.id.get_unicode);
        editmobile = findViewById(R.id.get_phn);
        editpass = findViewById(R.id.get_pass);
       // editusage=findViewById(R.id.get_usage1);
       // editdate=findViewById(R.id.get_date1);
        button = findViewById(R.id.button1);
    }

    private void initUIFromPerson(){
        editUserName.setText(person.getUsername());
        editUid.setText(person.getUniqid());
        editmobile.setText(person.getMobile());
        editpass.setText(person.getEmail());
      //  editusage.setText(person.getUsage());
       // editdate.setText(person.getDate());
    }

    private void setButtonOnClickListener(){
        button.setOnClickListener(e -> {
            String uname = editUserName.getText().toString();
            String unid = editUid.getText().toString();
            String phn = editmobile.getText().toString();
            String pass= editpass.getText().toString();
         //   String usg= editusage.getText().toString();
        //    String dat= editdate.getText().toString();

            person.setUsername(uname);
            person.setUniqid(unid);
            person.setMobile(phn);
            person.setEmail(pass);
         //   person.setUsage(usg);
         //   person.setDate(dat);

            if(edit){
                databaseReference.child(person.getKey()).setValue(person);
            }else{
                String key = databaseReference.push().getKey();
                person.setKey(key);
                //changes
                databaseReference.child(key).setValue(person);
            }
            finish();
        });
    }

    private void handleBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            edit = bundle.getBoolean("edit");
            if(edit){
                person = Parcels.unwrap(bundle.getParcelable("person"));
            }
        }
    }
}