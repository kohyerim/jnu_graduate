package com.example.jnu_graduate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class lobby extends AppCompatActivity {

    Button logout;
    TextView libarts;
    TextView major;
    TextView whole;
    TextView cultureGP;
    TextView majorGP;
    TextView totalGP;
    private String jsonString;
    private JSONObject jsonObject;

    GradeParser gradeParser;

    String myHakbeon = "";
    String majorClass = "";

    String min_libartscredit;
    String max_libartscredit;
    String max_majorcredit;
    String max_wholecredit;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Toolbar tb = findViewById(R.id.toolbar1);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("나의 정보");

        cultureGP = findViewById(R.id.liberal_arts_credit);
        majorGP = findViewById(R.id.major_credit);
        totalGP = findViewById(R.id.whole_credit);

        // 제 생각에는 아마 manifests에 laylout 추가 안해서 그런것 같아요!!

        new Thread(){
            public void run(){
                gradeParser = new GradeParser();
                JSONObject gradeInfo = null;
                JSONObject majorInfo = null;
                try {
                    try {
                        setProfile();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // 학점 db 불러오기
                    gradeInfo = gradeParser.getMajor();

                    // 교양학점 db조회
                    JSONObject gradePoint = (JSONObject) gradeInfo.get("교양학점");
                    JSONObject cultureGradePoint = (JSONObject) gradePoint.get(myHakbeon);
                    Object minGrade = cultureGradePoint.get("min");
                    Object maxGrade = cultureGradePoint.get("max");
                    cultureGP.setText("??"+"/"+minGrade.toString()+'-'+maxGrade.toString());
                    min_libartscredit=minGrade.toString();
                    max_libartscredit=maxGrade.toString();
                    // 졸업학점 db 조회
                    majorInfo = gradeParser.eachMajor();
                    JSONObject majorGradePoint = (JSONObject) majorInfo.get(myHakbeon);
                    Object jolupGP = majorGradePoint.get("졸업학점");
                    Object major = majorGradePoint.get("심화전공");
                    totalGP.setText("??"+"/"+jolupGP.toString());
                    majorGP.setText("??"+"/"+major.toString());

                    max_wholecredit=jolupGP.toString();
                    max_majorcredit=major.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        gologout();
        golibarts_page();
        gomajor_page();
        gowhole_page();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.libarts_btn:
                Intent golibarts=new Intent(lobby.this, libarts_page.class);

                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                golibarts.putExtra("hakbeon",myHakbeon);
                golibarts.putExtra("major",majorClass);
                golibarts.putExtra("min_libartscredit",min_libartscredit);
                golibarts.putExtra("max_libartscredit",max_libartscredit);
                golibarts.putExtra("max_majorcredit",max_majorcredit);
                startActivity(golibarts);
                return true;
            case R.id.major_btn:
                Intent gomajor=new Intent(lobby.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon",myHakbeon);
                gomajor.putExtra("major",majorClass);
                gomajor.putExtra("min_libartscredit",min_libartscredit);
                gomajor.putExtra("max_libartscredit",max_libartscredit);
                gomajor.putExtra("max_majorcredit",max_majorcredit);
                startActivity(gomajor);
                return true;
            case R.id.whole_btn:
                Intent gowhole=new Intent(lobby.this, whole_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gowhole.putExtra("hakbeon",myHakbeon);
                gowhole.putExtra("major",majorClass);
                gowhole.putExtra("min_libartscredit",min_libartscredit);
                gowhole.putExtra("max_libartscredit",max_libartscredit);
                gowhole.putExtra("max_majorcredit",max_majorcredit);
                startActivity(gowhole);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gologout(){
        logout=findViewById(R.id.logout_btn);
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

                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                golibarts.putExtra("hakbeon",myHakbeon);
                golibarts.putExtra("major",majorClass);
                startActivity(golibarts);
            }
        });

    }

    public void gomajor_page(){
        major=findViewById(R.id.major_credit_container);
        major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomajor=new Intent(lobby.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon",myHakbeon);
                gomajor.putExtra("major",majorClass);
                startActivity(gomajor);
            }
        });

    }

    public void gowhole_page(){
        whole=findViewById(R.id.whole_credit_container);
        whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gowhole=new Intent(lobby.this, whole_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gowhole.putExtra("hakbeon",myHakbeon);
                gowhole.putExtra("major",majorClass);
                gowhole.putExtra("min_libartscredit",min_libartscredit);
                gowhole.putExtra("max_libartscredit",max_libartscredit);
                gowhole.putExtra("max_majorcredit",max_majorcredit);
                startActivity(gowhole);
            }
        });

    }

    public void setProfile() throws JSONException {
        TextView student_name = findViewById(R.id.st_Name);
        TextView dept = findViewById(R.id.st_Department);

        try {
            InputStream inputStream = openFileInput("profile.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                jsonString = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        jsonObject = new JSONObject(jsonString);

        student_name.setText(jsonObject.get("student_name").toString());
        dept.setText(jsonObject.get("major").toString());

        myHakbeon = jsonObject.get("student_num").toString().substring(0,4);
        majorClass = jsonObject.get("major").toString();
    }
}
