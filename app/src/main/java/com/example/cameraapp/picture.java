package com.example.cameraapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class picture extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public int img=1;
    private ImageView img1;
    private ImageView img2;
    private ImageView support;
    private String timeStamp1;
    private  String timeStamp2;

    public File my_path;

    private static final String LOG_TAG = picture.class.getSimpleName();

    private View.OnLongClickListener OnclickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            support=findViewById(R.id.click_image2);
            if (support.getDrawable() != null){
                BitmapDrawable bd=(BitmapDrawable) support.getDrawable();
                Bitmap b=bd.getBitmap();
                img1 = findViewById(R.id.click_image);
                img2.setImageDrawable(null);
                img1.setImageBitmap(b);
                img ++;
                timeStamp1=timeStamp2;
            // Do something in response to button click
            //return true if the callback consumed the long click, false otherwise.
            //return boolean true at the end of OnLongClickListener to indicat you don't want further processing
            }
            else {
                img1.setImageDrawable(null);
                Log.d("valore img: ", String.valueOf(img));
                img --;
            }
            return true;
            //return false;
        }
    };

    private View.OnLongClickListener OnclickListener2 = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            img2.setImageDrawable(null);
            img --;
            return true;
            //return false;
        }
    };

    private View.OnClickListener Onclick2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            img2 = findViewById(R.id.click_image2);
            if (img2.getDrawable() != null) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;
                Toast toast =
                        Toast.makeText(context, timeStamp2, duration);
                toast.show();
                //return false;
            }
        }
    };

    private View.OnClickListener Onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            img1=findViewById(R.id.click_image);
            if (img1.getDrawable() != null) {
                Context context = getApplicationContext();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;
                Toast toast =
                        Toast.makeText(context, timeStamp1, duration);
                toast.show();
            }
            //return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        img1 = findViewById(R.id.click_image);
        img2 = findViewById(R.id.click_image2);
        img1.setOnLongClickListener(OnclickListener);
        img2.setOnLongClickListener(OnclickListener2);
        img1.setOnClickListener(Onclick);
        img2.setOnClickListener(Onclick2);
    }



    public void take_picture(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView click_image_id = findViewById(R.id.click_image);
        ImageView click_image_id2 = findViewById(R.id.click_image2);
        Bitmap imageBitmap =null;
        Bitmap imageBitmap2= null;
        if (img==1) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                click_image_id.setImageBitmap(imageBitmap);
                timeStamp1 = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
               // saveToInternalStorage(imageBitmap);
            }
            img++;
        }
        else {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap2 = (Bitmap) extras.get("data");
                BitmapDrawable bd=(BitmapDrawable) click_image_id.getDrawable();
                Bitmap b=bd.getBitmap();
                click_image_id2.setImageBitmap(b);
                click_image_id.setImageBitmap(imageBitmap2);
                timeStamp2 = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
                String swap=timeStamp2;
                timeStamp2=timeStamp1;
                timeStamp1=swap;
            }
            img--;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
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
    public void saveToInternalStorage(View view){
        ImageView click_image_id = findViewById(R.id.click_image);
        BitmapDrawable bd=(BitmapDrawable) click_image_id.getDrawable();
        Bitmap b=bd.getBitmap();
       // ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        Log.d("saveToInternalStorage: ",getApplicationContext().getFilesDir().toString());
        //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File path= getApplicationContext().getFilesDir();
        // Create imageDir
        File my_path=new File(path,"profile.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(my_path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //return path.getAbsolutePath();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Checks if external storage is available for read and write */
    public void isExternalStorageWritable(View view) throws InterruptedException {
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), Environment.DIRECTORY_PICTURES);
// probably a partition of the device internal memory as external storage
        File pathPrimaryExternalStorage = externalStorageVolumes[0];
        Log.d("", ""+pathPrimaryExternalStorage);
// probably this is the SD card
//        File pathSecondaryExternalStorage = externalStorageVolumes[1];
        ImageView click_image_id = findViewById(R.id.click_image);
        BitmapDrawable bd=(BitmapDrawable) click_image_id.getDrawable();
        Bitmap b=bd.getBitmap();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Context context = getApplicationContext();
            CharSequence text = "Ready!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Thread.sleep(3000);
            // add memory left
            StatFs stat = new StatFs(externalStorageVolumes[0].getPath());
            long bytesAvailable;
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            long megAvailable = bytesAvailable / (1024 * 1024);
            text = "Available External free Space: " + megAvailable +" MB!";
            duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            // search img
            //and save it
            File file = new File(pathPrimaryExternalStorage, "DemoPicture.png");
            Log.d("path", ""+pathPrimaryExternalStorage);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                // Use the compress method on the BitMap object to write image to the OutputStream
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //return path.getAbsolutePath();
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "No Space left!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    public void isExternalStorageWritableSD(View view) throws InterruptedException {
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), Environment.DIRECTORY_PICTURES);
// probably a partition of the device internal memory as external storage
        File pathSecondaryExternalStorage = externalStorageVolumes[1];
        Log.d("", "" + pathSecondaryExternalStorage);
// probably this is the SD card
//        File pathSecondaryExternalStorage = externalStorageVolumes[1];
        ImageView click_image_id = findViewById(R.id.click_image);
        BitmapDrawable bd = (BitmapDrawable) click_image_id.getDrawable();
        Bitmap b = bd.getBitmap();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Context context = getApplicationContext();
            CharSequence text = "Ready!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Thread.sleep(3000);
            // add memory left
            StatFs stat = new StatFs(externalStorageVolumes[1].getPath());
            long bytesAvailable;
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            long megAvailable = bytesAvailable / (1024 * 1024);
            text = "Available External free Space: " + megAvailable + " MB!";
            duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            // search img
            //and save it
            File file = new File(pathSecondaryExternalStorage, "DemoPicture2.png");
            Log.d("path", "" + pathSecondaryExternalStorage);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                // Use the compress method on the BitMap object to write image to the OutputStream
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //return path.getAbsolutePath();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "No Space left!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}