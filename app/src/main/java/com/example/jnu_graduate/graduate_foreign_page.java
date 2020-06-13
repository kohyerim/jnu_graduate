package com.example.jnu_graduate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class graduate_foreign_page extends AppCompatActivity {
    public static Activity graduate_foreignactivity;
    Context context;
    GradeParser gradeParser;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    private int detailsubjectnum=0;
    private String hakbeon = null;
    private String major = null;

    OpenJSONFile opener;
    JSONObject classJson;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduate_foreign_page);
        graduate_foreignactivity=graduate_foreign_page.this;

        Toolbar tb = findViewById(R.id.toolbar7);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("졸업 인정-외국어");
        System.out.println("이건왜안나오냐1");



        prevcontainerid=R.id.toolbar7;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.graduate_foreign_layout);//앞으로 들어갈 뷰들의 레이아웃 정의
        System.out.println("이건왜안나오냐2");

        new Thread() {
            public void run() {
                JSONObject majorInfo=null;
                gradeParser = new GradeParser();
                try {
                    opener = new OpenJSONFile(openFileInput("parsedClass.json"), getApplicationContext());
                    classJson = opener.getJSONObject();
                    majorInfo = gradeParser.eachMajor();
                    System.out.println("이건왜안나오냐3");

                } catch (FileNotFoundException | JSONException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("이건왜안나오냐4");
                    // 전공필수 과목 참고코드
                    // 해당 키값에 맞는 곳에 배열에 들어있는 과목 출력해주시면 됩니다!
                    Containerhelper containerhelper=new Containerhelper();
                    containerhelper.makeforeign(majorInfo);
                    System.out.println("이건왜안나오냐5");
                    final ArrayList<ArrayList<String>>graduate_majorarr=containerhelper.get_graduate_grouparr();
                    final addcontainer addcontainer1=new addcontainer();
                    System.out.println("이건왜안나오냐");
                    System.out.println(graduate_majorarr);
                    for(int i=0; i<graduate_majorarr.size();i++){
                        ArrayList<String> imsi=graduate_majorarr.get(i);
                        System.out.println(imsi);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            easycreatecontainer(addcontainer1,"졸업자격-외국어",graduate_majorarr);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.start();


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts, ArrayList<ArrayList<String>> lists){
        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //레이아웃을 만들어줄 객체를 생성
        //addcontainer maincontainer1 = new addcontainer(); 함수화를 위하여 add컨테이너 대신 simplecontainer로 변경
        //높이를 계산하기위해 데이터를 다집어넣어줌

        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            simplecontainer.calculateheigth2(childlist);
        }



        //---------------------------------------------------------------------------컨테이너 만들기
        //객체에 컨텍스트,들어갈 레이아웃 이전 view의id를 주고 컨테이너 생성
        simplecontainer.setContainer(context,constraintLayout,prevcontainerid,_texts);
        simplecontainer.createcontainer();
        //---------------------------------------------------------------------------프로그래스바 생성

        //-----------------------  세부과목및 과목생성

        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            simplecontainer.createsubjectmenu5(childlist);
        }

        //마지막으로 이 컨테이너가 마지막인것을 저장
        prevcontainerid=simplecontainer.getContainerid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_sample2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.graduate_lobby_btn:
                Intent golobby=new Intent(graduate_foreign_page.this, lobby.class);
                startActivity(golobby);
                return true;
            case R.id.graduate_major_btn:
                Intent gograduate_major=new Intent(graduate_foreign_page.this, graduate_major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                startActivity(gograduate_major);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
