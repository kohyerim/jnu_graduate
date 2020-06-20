package com.example.jnu_graduate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
    TextView pilsu;
    TextView linked;
    TextView graduate_major;
    TextView graduate_foreign;
    TextView pilsuGP;
    TextView cultureGP;
    TextView majorGP;
    TextView totalGP;
    TextView LinkedGP;



    private String jsonString;
    private JSONObject jsonObject;

    GradeParser gradeParser;
    ClassParser classParser;
    OpenJSONFile opener;

    String myHakbeon = "";
    String majorClass = "";

    String min_libartscredit;
    String max_libartscredit;
    String max_majorcredit;
    String max_wholecredit;
    JSONObject classJson;
    String linkedmajor=null;
    boolean onoffLinked;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        //로비 진입시 로그인 액티비티 종료
        //
        Toolbar tb = findViewById(R.id.toolbar1);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("나의 정보");
        cultureGP = findViewById(R.id.liberal_arts_credit);
        majorGP = findViewById(R.id.major_credit);
        totalGP = findViewById(R.id.whole_credit);
        pilsuGP = findViewById(R.id.pilsu_credit);
        LinkedGP = findViewById(R.id.Linked_Major_credit);
        try {
            setProfile();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(){
            public void run(){
                gradeParser = new GradeParser();
                JSONObject gradeInfo = null;
                JSONObject majorInfo = null;
                try {
                    classParser = new ClassParser(openFileInput("class.json"), getApplicationContext());
                    classParser.createParsedClass();
                    opener = new OpenJSONFile(openFileInput("parsedClass.json"), getApplicationContext());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }


                try {
                    // 학점 db 불러오기
                    gradeInfo = gradeParser.getMajor();

                    // 교양학점 db조회
                    JSONObject gradePoint = (JSONObject) gradeInfo.get("교양학점");
                    JSONObject cultureGradePoint = (JSONObject) gradePoint.get(myHakbeon);
                    Object minGrade = cultureGradePoint.get("min");
                    Object maxGrade = cultureGradePoint.get("max");
                    cultureGP.setText(opener.getCultureGP()+"/"+minGrade.toString()+'-'+maxGrade.toString());
                    min_libartscredit=minGrade.toString();
                    max_libartscredit=maxGrade.toString();
                    // 졸업학점 db 조회
                    majorInfo = gradeParser.eachMajor();
                    JSONObject majorGradePoint = (JSONObject) majorInfo.get(myHakbeon);
                    Object jolupGP = majorGradePoint.get("졸업학점");
                    Object major = majorGradePoint.get("심화전공");
                    Object Linked = majorGradePoint.get("연계전공");

                    classJson = opener.getJSONObject();
                    Containerhelper containerhelper=new Containerhelper();
                    containerhelper.setnewStartSetting(myHakbeon,classJson);
                    containerhelper.libartsContainercreate();
                    containerhelper=new Containerhelper();

                    String title="전공";
                    containerhelper.setMajorStartSetting(title,myHakbeon,classJson);
                    containerhelper.majorContainerCreate();
                    totalGP.setText(opener.getTotalGP()+"/"+jolupGP.toString());
                    majorGP.setText(containerhelper.get_herecredit()+"/"+major.toString()+"~");
                    max_wholecredit=jolupGP.toString();
                    max_majorcredit=major.toString();
                    title="연계전공";
                    containerhelper=new Containerhelper();
                    containerhelper.setlinkedmajorStartSetting(title,myHakbeon,classJson);
                    containerhelper.LinkedmajorContainerCreate(linkedmajor);
                    String herecredit=String.valueOf(containerhelper.get_herecredit());
                    LinkedGP.setText(herecredit+"/"+Linked.toString());
                    containerhelper=new Containerhelper();
                    containerhelper.makePilsuStartSetting(myHakbeon,classJson);
                    pilsuGP.setText(containerhelper.get_pilsuherecount()+"/"+containerhelper.get_pilsumaxcount());
                    if(!onoffLinked){
                        TextView linktext=findViewById(R.id.Linked_Major_text);
                        LinkedGP.setText("내역없음");
                        String color="#FCFCFC";
                        linktext.setTextColor(Color.parseColor(color));
                        LinkedGP.setTextColor(Color.parseColor(color));
                    }
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
        gopilsu_page();
        gograduate_foreign();
        gograduate_major();
        if(onoffLinked){
            goLinked_page();
        }
        else{
            linked=findViewById(R.id.Linked_Major_container);
            linked.setBackground(ContextCompat.getDrawable(this, R.drawable.lobby_disable));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_sample1, menu);
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
                golibarts.putExtra("linkedmajor",linkedmajor);
                golibarts.putExtra("onofflinked",onoffLinked);
                startActivity(golibarts);
                return true;
            case R.id.major_btn:
                Intent gomajor=new Intent(lobby.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon",myHakbeon);
                gomajor.putExtra("major",majorClass);
                gomajor.putExtra("linkedmajor",linkedmajor);
                gomajor.putExtra("onofflinked",onoffLinked);
                startActivity(gomajor);
                return true;
            case R.id.whole_btn:
                Intent gowhole=new Intent(lobby.this, whole_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gowhole.putExtra("hakbeon",myHakbeon);
                gowhole.putExtra("major",majorClass);
                gowhole.putExtra("linkedmajor",linkedmajor);
                gowhole.putExtra("onofflinked",onoffLinked);
                startActivity(gowhole);
                return true;
            case R.id.pilsu_btn:
                Intent gopisu=new Intent(lobby.this, PilsuSubject_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gopisu.putExtra("hakbeon",myHakbeon);
                gopisu.putExtra("major",majorClass);
                gopisu.putExtra("linkedmajor",linkedmajor);
                gopisu.putExtra("onofflinked",onoffLinked);
                startActivity(gopisu);
                return true;

            case R.id.linked_major_btn:
                if(onoffLinked) {
                    Intent golinked = new Intent(lobby.this, Linkedmajor_page.class);
                    // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                    golinked.putExtra("hakbeon", myHakbeon);
                    golinked.putExtra("major", majorClass);
                    golinked.putExtra("linkedmajor", linkedmajor);
                    golinked.putExtra("onofflinked", onoffLinked);
                    startActivity(golinked);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gologout(){
        logout=findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logout logic
                getApplicationContext().deleteFile("class.json");
                getApplicationContext().deleteFile("parsedClass.json");
                getApplicationContext().deleteFile("profile.json");
                getApplicationContext().deleteFile("origin.json");
                Intent loginintent=new Intent(lobby.this, MainActivity.class);
                startActivity(loginintent);
//                Activity libarts_activity=(Activity)libarts_page.libartsactivity;
//                libarts_activity.finish();
//                Activity major_activity=(Activity)major_page.majoractivity;
//                major_activity.finish();
//                Activity whole_activity=(Activity)whole_page.wholeactivity;
//                whole_activity.finish();
                finish();
            }
        });
    }

    public void gograduate_major(){
        graduate_major=findViewById(R.id.lobby_graduate_major_inner_btn);
        graduate_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goGD_major=new Intent(lobby.this, graduate_major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                goGD_major.putExtra("hakbeon",myHakbeon);
                goGD_major.putExtra("major",majorClass);
                startActivity(goGD_major);
            }
        });

    }

    public void gograduate_foreign(){
        graduate_foreign=findViewById(R.id.lobby_graduate_foreign_inner_btn);
        graduate_foreign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goGD_foreign=new Intent(lobby.this, graduate_foreign_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                goGD_foreign.putExtra("hakbeon",myHakbeon);
                goGD_foreign.putExtra("major",majorClass);
                startActivity(goGD_foreign);
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
                golibarts.putExtra("linkedmajor",linkedmajor);
                startActivity(golibarts);
            }
        });

    }

    public void gopilsu_page(){
        pilsu=findViewById(R.id.pilsu_container);
        pilsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopilsu=new Intent(lobby.this, PilsuSubject_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gopilsu.putExtra("hakbeon",myHakbeon);
                gopilsu.putExtra("major",majorClass);
                gopilsu.putExtra("linkedmajor",linkedmajor);
                startActivity(gopilsu);
            }
        });
    }
    public void goLinked_page(){
        linked=findViewById(R.id.Linked_Major_container);
        linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent golinked=new Intent(lobby.this, Linkedmajor_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                golinked.putExtra("hakbeon",myHakbeon);
                golinked.putExtra("major",majorClass);
                golinked.putExtra("linkedmajor",linkedmajor);
                startActivity(golinked);
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
                gomajor.putExtra("linkedmajor",linkedmajor);
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
                gowhole.putExtra("linkedmajor",linkedmajor);
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
        if(jsonObject.has("dbl_dept_nm")){
            linkedmajor =jsonObject.get("dbl_dept_nm").toString();
            dept.setText(jsonObject.get("major").toString() + "  /  " + linkedmajor);
            onoffLinked=true;

        }
        else{
            dept.setText(jsonObject.get("major").toString());
            onoffLinked=false;
        }

        myHakbeon = jsonObject.get("student_num").toString().substring(0,4);
        majorClass = jsonObject.get("major").toString();
    }

    public void onBackPressed(){
        // super.onBackPressed();
    }
}
