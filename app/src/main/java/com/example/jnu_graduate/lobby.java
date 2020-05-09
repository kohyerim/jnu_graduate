package com.example.jnu_graduate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class lobby extends AppCompatActivity {

    Button logout;
    TextView libarts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        //왜인진 모르겟지만 이안에 뭔가 코드를 작성하려고 들면 액티비티가 실행이 안됨...
        gologout();
        golibarts_page();

    }
    public void gologout(){
        logout=findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent=new Intent(lobby.this, MainActivity.class);
                startActivity(loginintent);
                finish();
            }
        });
    }

    public void golibarts_page(){
        libarts=findViewById(R.id.liberal_arts_credit_container);
        libarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent golibarts=new Intent(lobby.this, libarts_page.class);
                startActivity(golibarts);

            }
        });

    }
}
