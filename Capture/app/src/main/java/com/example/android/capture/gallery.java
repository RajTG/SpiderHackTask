package com.example.android.capture;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class gallery extends AppCompatActivity {
    ImageView imgv;
    String passedurl;
    Uri imguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imgv = (ImageView)findViewById(R.id.imgv);
        Bundle passedData = getIntent().getExtras();
        passedurl = passedData.getString("location");
        //imguri = Uri.parse(passedurl);
        Uri imguri = Uri.parse(MainActivity.pass.getStringExtra("location"));
        imgv.setImageURI(imguri);





    }
}
