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

public class Viewvehicle extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] user_id,vehicle,  detail, fname,vehicle_id, value,img;
    public static String vid, iid, sid, amt;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewvehicle);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewvehicle.this;
        String q = "/Viewvehicle?login_id="+sh.getString("log_id","" );
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
                user_id = new String[ja1.length()];
                vehicle = new String[ja1.length()];
                detail = new String[ja1.length()];

                value = new String[ja1.length()];
                img = new String[ja1.length()];
                fname= new String[ja1.length()];
                vehicle_id= new String[ja1.length()];





                for (int i = 0; i < ja1.length(); i++) {
                    user_id[i] = ja1.getJSONObject(i).getString("user_id");
                    vehicle[i] = ja1.getJSONObject(i).getString("vehicle");
                    detail[i] = ja1.getJSONObject(i).getString("detail");

                    fname[i] = ja1.getJSONObject(i).getString("fname");
                    vehicle_id[i] = ja1.getJSONObject(i).getString("vehicle_id");

                    img[i] = ja1.getJSONObject(i).getString("image");







                    value[i] = "vehicle:" + vehicle[i] + "\ndetail: " + detail[i]  ;
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);


                l1.setAdapter(ar);

                Custimage a=new Custimage(this,vehicle,detail,fname,img);
                l1.setAdapter(a);



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
        vid=vehicle_id[i];
        final CharSequence[] items = {"Send Request","Documents details","For Sale", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewvehicle.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Send Request")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewvehicle.this;
                    String q = "/sendrequest?login_id="+sh.getString("log_id","" )+"&vid="+vid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(),"Requested successfully", Toast.LENGTH_LONG).show();


                } else if (items[item].equals("For Sale")) {
//                    startActivity(new Intent(getApplicationContext(),Insurence.class));
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewvehicle.this;
                    String q = "/sendsalerequest?login_id="+sh.getString("log_id","" )+"&vid="+vid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
                else if (items[item].equals("Documents details")) {
                  startActivity(new Intent(getApplicationContext(),Insurence.class));
                }



                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}