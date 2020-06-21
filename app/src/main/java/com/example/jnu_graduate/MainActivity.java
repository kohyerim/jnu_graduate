package com.example.jnu_graduate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Service service;
    Button loginBtn;
    public static Activity loginactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginactivity=MainActivity.this;
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                loginBtn.setEnabled(false);
                service = new Service();
                TextView idView = findViewById(R.id.studentNum);
                TextView pwView = findViewById(R.id.password);
                String id = idView.getText().toString();
                String pw = pwView.getText().toString();
                service.execute(getApplicationContext(), id, pw, MainActivity.this, loginBtn);
            }
        });
    }
}
