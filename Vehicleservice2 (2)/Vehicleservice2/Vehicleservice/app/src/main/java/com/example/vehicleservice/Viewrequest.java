package com.example.vehicleservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] vehicle,statu,  date, time,request_id, value,vehicle_id;
    public static String rid, vid, sid, amt;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewrequest.this;
        String q = "/Viewrequest?login_id="+sh.getString("log_id","" );
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                statu= new String[ja1.length()];
                vehicle = new String[ja1.length()];
                date = new String[ja1.length()];
                vehicle_id = new String[ja1.length()];
                value = new String[ja1.length()];

                time= new String[ja1.length()];
                request_id= new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    vehicle[i] = ja1.getJSONObject(i).getString("vehicle");

                    vehicle_id[i] = ja1.getJSONObject(i).getString("vehicle_id");
                    date[i] = ja1.getJSONObject(i).getString("date");

                    time[i] = ja1.getJSONObject(i).getString("time");
                    request_id[i] = ja1.getJSONObject(i).getString("request_id");




                    value[i] = "vehicle:" + vehicle[i] + "\ndate: " + date[i]+"\ntime:"+time[i]+"\nstatus:"+statu[i] ;
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);


                l1.setAdapter(ar);


            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        rid=request_id[i];
        vid=vehicle_id[i];
        final CharSequence[] items = {"Send Service","sell", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewrequest.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Send Service")) {

                    startActivity(new Intent(getApplicationContext(),Sendservice.class));

                }

                else if (items[item].equals("sell")) {


                    startActivity(new Intent(getApplicationContext(),sell.class));


                }else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
    }
