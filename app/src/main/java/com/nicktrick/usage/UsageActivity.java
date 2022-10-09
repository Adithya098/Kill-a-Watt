package com.nicktrick.usage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nicktrick.R;
import com.nicktrick.firebase.Person;


import org.json.simple.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class UsageActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private FloatingActionButton fab;
    private ListView listView;
    private ListViewUsageAdapter listViewUsageAdapter;
    private List<Usage> listPerson = new ArrayList<>();

    private ProgressBar progressBar;
    JSONObject jsonObject;
    ArrayList<String> aar =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        databaseReference = FirebaseDatabase.getInstance().getReference("usage");

        initUI();
        setListViewAdapter();

        addSingleEventListener();
        addChildEventListener();

        setFabClickListener();
        setListViewItemListener();
        setListViewLongClickListener();
    }

    private void initUI(){
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab1);
        listView = findViewById(R.id.listView1);
    }

    private void setListViewAdapter(){
        listViewUsageAdapter = new ListViewUsageAdapter(this, listPerson);
        listView.setAdapter(listViewUsageAdapter);
    }

    private void addChildEventListener() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Usage usage = dataSnapshot.getValue(Usage.class);
                if(usage != null){
                    usage.setKey(dataSnapshot.getKey());

                    listPerson.add(dataSnapshot.getValue(Usage.class));
                    listViewUsageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Usage person = dataSnapshot.getValue(Usage.class);
                if(person != null){
                    String key = dataSnapshot.getKey();
                    for(int i=0;i<listPerson.size();i++){
                        Usage person1 = listPerson.get(i);
                        if(person1.getKey().equals(key)){
                            listPerson.set(i, person);

                            listViewUsageAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listPerson.remove(dataSnapshot.getValue(Usage.class));
                listViewUsageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void addSingleEventListener(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setListViewItemListener(){
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);
            bundle.putParcelable("usage", Parcels.wrap(listPerson.get(i)));
            Intent intent = new Intent(this, EditUsageActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void setListViewLongClickListener(){
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Usage usage = listPerson.get(i);
            new AlertDialog.Builder(this)
                    .setTitle("Delete " + usage.getUsage() + " " + usage.getUniqid())
                    .setMessage("Do you want to delete the selected record?")
                    .setPositiveButton("Delete", (dialogInterface, i1) -> {
                        databaseReference.child(usage.getKey()).removeValue();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i12) -> {
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
            return true;
        });
    }

    private void setFabClickListener() {
        fab.setOnClickListener(e -> {
            startActivity(new Intent(this, EditUsageActivity.class));
        });
    }
}