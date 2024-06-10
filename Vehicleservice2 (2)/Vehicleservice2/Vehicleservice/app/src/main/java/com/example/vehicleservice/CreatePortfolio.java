package com.example.vehicleservice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CreatePortfolio extends AppCompatActivity implements JsonResponse {
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9;
    Button b1;

    String fname, lname, city, hname, street, username, password, email, phone;
    SharedPreferences sh;
    public  static String log_id;

    ImageButton i1;

    String amount,quantity,total;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_portfolio);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = (EditText) findViewById(R.id.fname);
        e2 = (EditText) findViewById(R.id.lname);
        e9 = (EditText) findViewById(R.id.phone);

        e5 = (EditText) findViewById(R.id.street);
        e6 = (EditText) findViewById(R.id.uname);
        e7 = (EditText) findViewById(R.id.password);
        e8 = (EditText) findViewById(R.id.email);


        b1 = (Button) findViewById(R.id.signup);

        i1 = (ImageButton) findViewById(R.id.imageButton);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageOption();
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();


                phone = e9.getText().toString();

                street = e5.getText().toString();
                username = e6.getText().toString();
                password = e7.getText().toString();
                email = e8.getText().toString();


                sendAttach();
            }
        });



    }
        private void sendAttach() {

            try {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String q = "http://" +Ipsettings.ip+"/api/Registartion";

                Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();


                Map<String, byte[]> aa = new HashMap<>();




                aa.put("image", byteArray);
//                aa.put("pid",pid.getBytes());
                aa.put("fname", fname.getBytes());
                aa.put("login_id", sh.getString("log_id", "").getBytes());
                aa.put("lname", lname.getBytes());
                aa.put("street", street.getBytes());
                aa.put("username", username.getBytes());
                aa.put("password", password.getBytes());
                aa.put("email", email.getBytes());

                aa.put("phone", phone.getBytes());

//                aa.put("log_id",sh.getString("log_id","").getBytes());
//            aa.put("house",house.getBytes());

                FileUploadAsync fua = new FileUploadAsync(q);
                fua.json_response = (JsonResponse) CreatePortfolio.this;
                fua.execute(aa);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
            }
        }


        private void selectImageOption() {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

            final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePortfolio.this);
            builder.setTitle("Take Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Capture Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        startActivityForResult(intent, CAMERA_PIC_REQUEST);

                    } else if (items[item].equals("Choose from Gallery")) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, GALLERY_CODE);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
//                path = FileUtils.getPath(this, uri);
                path=FileUtils.getPath(this,uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                i1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                i1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}