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

public class Viewothervehicle extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] user_id,vehicle,  detail, fname,vehicle_id,amount,date,statu, value,image;
    public static String vid, amt, sid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewothervehicle);


        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewothervehicle.this;
        String q = "/Viewothervehicle?login_id="+sh.getString("log_id","" );
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
//                amount= new String[ja1.length()];
                image= new String[ja1.length()];
//                date= new String[ja1.length()];
                statu= new String[ja1.length()];
                value = new String[ja1.length()];

                fname= new String[ja1.length()];
                vehicle_id= new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    user_id[i] = ja1.getJSONObject(i).getString("user_id");
                    vehicle[i] = ja1.getJSONObject(i).getString("vehicle");
                    detail[i] = ja1.getJSONObject(i).getString("detail");
//                    amount[i] = ja1.getJSONObject(i).getString("amount");
//                    date[i] = ja1.getJSONObject(i).getString("date");
                    statu[i] = ja1.getJSONObject(i).getString("vstatus");
                    fname[i] = ja1.getJSONObject(i).getString("fname");
                    vehicle_id[i] = ja1.getJSONObject(i).getString("vehicle_id");
                    image[i] = ja1.getJSONObject(i).getString("vimage");
//                    amount[i]=
//                    value[i] = "vehicle:" + vehicle[i] + "\ndetail: " + detail[i]+ "\namount: " + amount[i]+ "\ndate: " + date[i]+ "\nstatus: " + statu[i]+ "\nuser: " + fname[i] ;
                }


                Custimage1 a=new Custimage1(this,vehicle,detail,fname,image);
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
        String ss=statu[i];
        if(ss.equalsIgnoreCase("b"))
        {
//        amt=amount[i];
            final CharSequence[] items = {"Buy", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewothervehicle.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Buy")) {

                       startActivity(new Intent(getApplicationContext(),Addamount.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
        }
    }
}