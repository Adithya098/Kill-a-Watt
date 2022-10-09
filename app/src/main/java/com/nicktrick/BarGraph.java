package com.nicktrick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BarGraph extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    BarChart barChart;
    int i=0;
    int keys;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        barChart = findViewById(R.id.barchart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("5555");



       /* BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Set Bar Chart Description");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);
      */


    }
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //getting artist
                if (dataSnapshot.exists()) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){


                        keys= Integer.parseInt(datas.child("value").getValue().toString());
                        date = datas.child("date").getValue().toString();

                        Log.e("test", "value "+String.valueOf(keys)+"date "+date+"index "+i);
                        labels.add(date);
                        entries.add(new BarEntry(keys,i));
                        i++;
                    }

                    BarDataSet bardataset = new BarDataSet(entries, "Cells");
                    BarData data = new BarData(labels, bardataset);
                    barChart.setData(data); // set the data and list of lables into chart

                    barChart.setDescription("Set Bar Chart Description");  // set the description

                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                    barChart.animateY(5000);

                    Log.e("test", "value "+entries.get(0)+"date "+entries.get(1)+"index "+i);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
