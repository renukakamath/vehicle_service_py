package com.example.vehicleservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class Viewbuyrequrst extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] buy_id,vehicle,  detail, fname,vehicle_id,amount,date,statu, value,image;
    public static String bid, amt, sid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbuyrequrst);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewbuyrequrst.this;
        String q = "/Viewbuyrequrst?login_id="+sh.getString("log_id","" );
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
                buy_id = new String[ja1.length()];
                vehicle = new String[ja1.length()];
                detail = new String[ja1.length()];
                amount= new String[ja1.length()];
                image= new String[ja1.length()];
                date= new String[ja1.length()];
                statu= new String[ja1.length()];
                value = new String[ja1.length()];

                fname= new String[ja1.length()];
                vehicle_id= new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    buy_id[i] = ja1.getJSONObject(i).getString("buy_id");
                    vehicle[i] = ja1.getJSONObject(i).getString("vehicle");
                    detail[i] = ja1.getJSONObject(i).getString("detail");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    fname[i] = ja1.getJSONObject(i).getString("fname");
                    vehicle_id[i] = ja1.getJSONObject(i).getString("vehicle_id");
                    image[i] = ja1.getJSONObject(i).getString("image");


                    value[i] = "vehicle:" + vehicle[i] + "\ndetail: " + detail[i]+ "\namount: " + amount[i]+ "\ndate: " + date[i]+ "\nstatus: " + statu[i]+ "\nuser: " + fname[i] ;
                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);
//
//
//                l1.setAdapter(ar);

                Custimage2 a=new Custimage2(this,vehicle,detail,amount,date,statu,fname,image);
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
        bid=buy_id[i];

        amt=amount[i];
        final CharSequence[] items = {"Accept","Reject", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewbuyrequrst.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Accept")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewbuyrequrst.this;
                    String q = "/Accept?login_id="+sh.getString("log_id","" )+"&bid="+bid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext()," Accepted successfully", Toast.LENGTH_LONG).show();


                } else if (items[item].equals("Reject")) {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewbuyrequrst.this;
                    String q = "/Reject?login_id="+sh.getString("log_id","" )+"&bid="+bid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext()," Accepted successfully", Toast.LENGTH_LONG).show();

                }



                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}