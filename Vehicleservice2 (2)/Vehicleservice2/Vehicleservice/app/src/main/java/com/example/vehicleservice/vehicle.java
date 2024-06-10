package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class vehicle extends AppCompatActivity implements JsonResponse {
    ListView l1;
    String[] file,value;
    public static String vid, iid, sid, amt;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        l1=(ListView) findViewById(R.id.list);
//        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) vehicle.this;
        String q = "/vehicle?login_id="+sh.getString("log_id","" )+"&uid="+Insurence.uid;
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

                file = new String[ja1.length()];



                value= new String[ja1.length()];





                for (int i = 0; i < ja1.length(); i++) {

                    file[i] = ja1.getJSONObject(i).getString("file");









//                    value[i] =  "file: " + file[i]  ;
                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);
//
//
//                l1.setAdapter(ar);

                Custimage4 a=new Custimage4(this,file);
                l1.setAdapter(a);



            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}