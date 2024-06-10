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

public class Insurence extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] details,file,  type,upload_id,value;
    public static String  uid, iid, sid, amt;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurence);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Insurence.this;
        String q = "/Insurence?login_id="+sh.getString("log_id","" )+"&vid="+Viewvehicle.vid;
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
                details = new String[ja1.length()];
                file = new String[ja1.length()];
                type = new String[ja1.length()];

                upload_id = new String[ja1.length()];


                value= new String[ja1.length()];





                for (int i = 0; i < ja1.length(); i++) {
                    details[i] = ja1.getJSONObject(i).getString("detail");
                    file[i] = ja1.getJSONObject(i).getString("file");
                    type[i] = ja1.getJSONObject(i).getString("type");


                    upload_id[i] = ja1.getJSONObject(i).getString("upload_id");




                    value[i] = "details:" + details[i] + "\nfile: " + file[i] +"\ntype:" + type[i]  ;
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);


                l1.setAdapter(ar);

                Custimage3 a=new Custimage3(this,details,file,type);
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
        uid=upload_id[i];

        final CharSequence[] items = {"Image", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Insurence.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Image")) {

                    startActivity(new Intent(getApplicationContext(),vehicle.class));

                }

               else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}