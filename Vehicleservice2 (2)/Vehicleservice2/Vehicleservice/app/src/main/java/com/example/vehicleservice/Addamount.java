package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class Addamount extends AppCompatActivity  implements JsonResponse {
    EditText e1;
    Button b1;
    String amount;
    public static String ip;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addamount);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText) findViewById(R.id.ipaddress);
        b1=(Button) findViewById(R.id.ipbutton);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amount=e1.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Addamount.this;
                String q = "/Buy?login_id="+sh.getString("log_id","" )+"&amt="+amount+"&vid="+Viewothervehicle.vid;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("sell")) {

                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
    }
