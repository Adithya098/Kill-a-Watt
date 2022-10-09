package com.nicktrick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BillPay extends AppCompatActivity {
    TextView previous,current,savings;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pay);
        uid =getIntent().getStringExtra("uid");
        previous = findViewById(R.id.textView9);
        current = findViewById(R.id.textView11);
        savings = findViewById(R.id.textView13);



    }
}
