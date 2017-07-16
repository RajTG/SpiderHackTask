package com.example.android.capture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static int Image_Capture = 1;
    Button photoBtn, gallery;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    public ImageView img;
    TextView textView;
    ListView lv;
    Uri got;
    public ArrayList<Uri> uris;
    public ArrayAdapter adapter;
    public static Intent pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoBtn = (Button) findViewById(R.id.photoBtn);
        gallery = (Button)findViewById(R.id.gallery);
        photoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                opencamera();
            }
        });

        textView = (TextView)findViewById(R.id.textView);
        uris = new ArrayList<Uri>();
        lv = (ListView)findViewById(R.id.lv);
        adapter = new Cadapter(this,uris);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                toas();


            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pass = new Intent(MainActivity.this, gallery.class);
                pass.putExtra("location",uris.get(i).toString());
                startActivity(pass);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                uris.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });


    }
    public void toas(){
        Toast.makeText(this, "Long Click on Image", Toast.LENGTH_LONG).show();

    }

    public void opencamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                // Error occurred while creating the File
                e.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                uris.add(photoURI);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, Image_Capture);
                //galleryAddPic();
            }

        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDir = this.getExternalFilesDir("raj");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK && null != data) {
                    try {
                        //Bundle extras = data.getExtras();
                        //Bitmap mImageBitmap = (Bitmap) extras.get("data");
                        adapter.notifyDataSetChanged();
                        mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                        img.setImageBitmap(mImageBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

        }

    }
}

