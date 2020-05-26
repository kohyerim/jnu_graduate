package com.example.jnu_graduate;

import android.content.Intent;
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

import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Service service = new Service();
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                System.out.println(Calendar.getInstance().get(Calendar.YEAR));
                TextView idView = findViewById(R.id.studentNum);
                TextView pwView = findViewById(R.id.password);
                String id = idView.getText().toString();
                String pw = pwView.getText().toString();
                service.execute(getApplicationContext(), id, pw);

                //화면이동 (if 로그인이 성공한다면?)
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable()  {
                    public void run() {
                        Intent lobbyintent=new Intent(MainActivity.this, lobby.class);
                        startActivity(lobbyintent);
                    }
                }, 3000); // 1초후
            }
        });
    }
}
