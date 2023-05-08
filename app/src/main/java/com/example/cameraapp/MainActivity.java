package com.example.cameraapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadImageFromStorage();

    }
    private void loadImageFromStorage()
    {
        File[] externalStorageVolumes =
                ContextCompat.getExternalFilesDirs(getApplicationContext(), Environment.DIRECTORY_PICTURES);
// probably a partition of the device internal memory as external storage
        File pathPrimaryExternalStorage = externalStorageVolumes[0];
        File pathSecondaryExternalStorage = externalStorageVolumes[1];
        File path= getApplicationContext().getFilesDir();

        try {
            File internal=new File(path, "profile.png");
            File external= new File(pathPrimaryExternalStorage, "DemoPicture.png");
            File sd= new File(pathSecondaryExternalStorage, "DemoPicture2.png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(external));
            ImageView img=(ImageView)findViewById(R.id.imgPicker);
            img.setImageBitmap(b);
            Bitmap b2 = BitmapFactory.decodeStream(new FileInputStream(internal));
            ImageView img2=(ImageView)findViewById(R.id.imgPicker2);
            img2.setImageBitmap(b2);
            Bitmap b3 = BitmapFactory.decodeStream(new FileInputStream(sd));
            ImageView img3=(ImageView)findViewById(R.id.imgPicker3);
            img3.setImageBitmap(b3);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    public void settings(View view){
        Intent intent= new Intent(this,settings_1.class);
        startActivity(intent);
    }
    public void picture(View view){
        Intent intent= new Intent(this,picture.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        loadImageFromStorage();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
        //loadImageFromStorage();

    }
    @Override
    protected void onPause() {
        super.onPause();
        //    try {
        //     Thread.sleep(10000);
        // } catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        // }
        Log.d(LOG_TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

}