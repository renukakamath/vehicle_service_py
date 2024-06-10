package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Userhome extends AppCompatActivity {
    Button b2,b3,b4,b5,b6,b7,b8,b9,b10;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

        b2=(Button)findViewById(R.id.Addvehicle);

        b2=(Button)findViewById(R.id.prediction);
        b3=(Button)findViewById(R.id.Addwallet);
        b4=(Button)findViewById(R.id.viewcycle);
        b5=(Button)findViewById(R.id.viewbook);
        b10=(Button)findViewById(R.id.Addvehicle);

        t1=(TextView) findViewById(R.id.textView6);

        t1.setText("welcome "+Login.name);

        b7=(Button)findViewById(R.id.logout);

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Addvehicle.class));
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Viewvehicle.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Viewrequest.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Viewothervehicle.class));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Viewbuyrequrst.class));
            }
        });


        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Ipsettings.class));
            }
        });
    }
}