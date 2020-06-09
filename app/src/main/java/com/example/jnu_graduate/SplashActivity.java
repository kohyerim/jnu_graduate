package com.example.jnu_graduate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash();
    }

    public void splash(){
        final Intent loginIntent = new Intent(this, MainActivity.class);
        final Intent lobbyIntent = new Intent(this, lobby.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                File profile = new File("/data/data/com.example.jnu_graduate/files/profile.json");
                if(profile.exists()){
                    startActivity(lobbyIntent);
                }
                else{
                    startActivity(loginIntent);
                }
            }
        }, 2000);



    }
}
