package com.example.cameraapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class settings_1 extends AppCompatActivity {
    private static final String LOG_TAG = settings_1.class.getSimpleName();
    private Button button;
    private File path;

    private View.OnLongClickListener OnclickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            TextView event=findViewById(R.id.event_detected);
            event.setText("Long CLick Event");
            // Do something in response to button click
            //return true if the callback consumed the long click, false otherwise.
            //return boolean true at the end of OnLongClickListener to indicat you don't want further processing
            return true;
            //return false;
        }
    };
    private View.OnClickListener Onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView event=findViewById(R.id.event_detected);
            event.setText("Short CLick Event");

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings1);
        button = findViewById(R.id.test);
        button.setOnLongClickListener(OnclickListener);
        button.setOnClickListener(Onclick);


    }
    public void settings(View v){
        Intent intent= new Intent(this,settings_2.class);
        startActivity(intent);
    }
    public void back(View v){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
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
    public void save_file(View view) throws IOException {
        path = getApplicationContext().getFilesDir();

        File file = new File(path, "file.txt");
        EditText val=findViewById(R.id.plain_text);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            stream.write(val.getText().toString().getBytes());
        } finally {
            stream.close();
        }
    }
    public void read_file(View view) throws IOException {
        path = getApplicationContext().getFilesDir();

        File file = new File(path,"file.txt");
        int length= (int) file.length();
        byte[] bytes= new byte[length];
        FileInputStream in = new FileInputStream(file);
        try{
            in.read(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
        String contents= new String(bytes);
        TextView val=findViewById(R.id.file_value);
        val.setText(contents);

    }
}