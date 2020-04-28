package com.example.jnu_graduate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView id;
    TextView pw;
    Service service = new Service();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.loginBtn);
        this.id = findViewById(R.id.studentNum);
        this.pw = findViewById(R.id.password);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                TextView idView = findViewById(R.id.studentNum);
                TextView pwView = findViewById(R.id.password);
                String id = idView.getText().toString();
                String pw = pwView.getText().toString();
                service.execute(id, pw);
            }
        });
    }
}
