package com.example.jnu_graduate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class lobby extends AppCompatActivity {

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        //왜인진 모르겟지만 이안에 뭔가 코드를 작성하려고 들면 액티비티가 실행이 안됨...
    }
    public void gologin(){

        logout.findViewById(R.id.btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent=new Intent(lobby.this, MainActivity.class);
                startActivity(loginintent);
            }
        });
    }
}
