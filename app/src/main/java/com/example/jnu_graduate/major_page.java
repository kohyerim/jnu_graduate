package com.example.jnu_graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class major_page extends AppCompatActivity {

    Context context;
    GradeParser gradeParser;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    private int detailsubjectnum=0;
    private String hakbeon = null;
    private String major = null;
    private String min_libartscredit = null;
    private String max_libartscredit = null;
    private String max_majorcredit = null;
    OpenJSONFile opener;
    JSONObject classJson;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_page);
        Toolbar tb = findViewById(R.id.toolbar3);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("전공 학점");



        Intent intent = getIntent();
        hakbeon = intent.getExtras().getString("hakbeon");
        major = intent.getExtras().getString("major");
        min_libartscredit = intent.getExtras().getString("min_libartscredit");
        max_libartscredit = intent.getExtras().getString("max_libartscredit ");
        max_majorcredit = intent.getExtras().getString("max_majorcredit");
        System.out.println(hakbeon+"전공");


        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.toolbar3;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.major_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의


        new Thread() {
            public void run() {
                JSONObject majorInfo=null;
                JSONObject gradeInfo=null;
                gradeParser = new GradeParser();
                try {
                    opener = new OpenJSONFile(openFileInput("parsedClass.json"), getApplicationContext());
                    classJson = opener.getJSONObject();
                    majorInfo = gradeParser.eachMajor();
                    gradeInfo = gradeParser.getMajor();
                } catch (FileNotFoundException | JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 교양세부과목
                final String title="전공";
                final addcontainer container=new addcontainer();
                final Containerhelper containerhelper=new Containerhelper();
                containerhelper.setBasicSetting(constraintLayout,context,prevcontainerid);
                containerhelper.setMajorStartSetting(title,hakbeon,classJson,majorInfo,gradeInfo,container);
                containerhelper.majorContainerCreate();
                final String herecredit=String.valueOf(containerhelper.get_herecredit());
                final String maxcredit=containerhelper.get_maxcredit();
                final addcontainer addcontainer1=new addcontainer();
                final ArrayList<ArrayList<String>> grouparr=containerhelper.getGrouparr();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        easycreatecontainer(addcontainer1,title,herecredit,maxcredit,grouparr);
                    }
                });
            }
        }.start();


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts ,String now_credit,String max_credit, ArrayList<ArrayList<String>> lists){
        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //레이아웃을 만들어줄 객체를 생성
        //addcontainer maincontainer1 = new addcontainer(); 함수화를 위하여 add컨테이너 대신 simplecontainer로 변경
        //높이를 계산하기위해 데이터를 다집어넣어줌

        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            simplecontainer.calculateheigth(childlist);
        }
        //---------------------------------------------------------------------------컨테이너 만들기

        //객체에 컨텍스트,들어갈 레이아웃 이전 view의id를 주고 컨테이너 생성
        simplecontainer.setContainer(context,constraintLayout,prevcontainerid,_texts);
        simplecontainer.createcontainer();

        //---------------------------------------------------------------------------프로그래스바 생성
        //프로그래스바 설정
        simplecontainer.setprogressbar(now_credit,max_credit);
        //프로그래스바 생성
        simplecontainer.createprogressbar();
        //-----------------------  세부과목및 과목생성
        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            simplecontainer.createsubjectmenu(childlist);
        }
        //마지막으로 이 컨테이너가 마지막인것을 저장
        prevcontainerid=simplecontainer.getContainerid();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void pilsumajorcreatecontainer(addcontainer simplecontainer ,String _texts ,String now_credit,String max_credit, ArrayList<ArrayList<String>> lists){
        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //레이아웃을 만들어줄 객체를 생성
        //addcontainer maincontainer1 = new addcontainer(); 함수화를 위하여 add컨테이너 대신 simplecontainer로 변경
        //높이를 계산하기위해 데이터를 다집어넣어줌

        for(int i=0; i<lists.size();i++) {
            ArrayList childlist= lists.get(i);
            simplecontainer.onlymajorcalculateheigth(childlist);
        }
        //---------------------------------------------------------------------------컨테이너 만들기

        //객체에 컨텍스트,들어갈 레이아웃 이전 view의id를 주고 컨테이너 생성
        simplecontainer.setContainer(context,constraintLayout,prevcontainerid,_texts);
        simplecontainer.createcontainer();

        //---------------------------------------------------------------------------프로그래스바 생성
        //프로그래스바 설정

        simplecontainer.setprogressbar(now_credit,max_credit);
        //프로그래스바 생성
        simplecontainer.createprogressbar();
        //-----------------------  세부과목및 과목생성
        simplecontainer.createnodivisionsubjectmenu(lists);

        for(int i=0; i<lists.size();i++) {
            ArrayList childlist= lists.get(i);
            simplecontainer.createnodivisionsubjectmenu(childlist);
        }
        //마지막으로 이 컨테이너가 마지막인것을 저장
        prevcontainerid=simplecontainer.getContainerid();
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
            case R.id.lobby_btn:
                Intent golobby=new Intent(major_page.this, libarts_page.class);
                startActivity(golobby);
                return true;
            case R.id.libarts_btn:
                Intent golibarts=new Intent(major_page.this, libarts_page.class);

                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                golibarts.putExtra("hakbeon",hakbeon);
                golibarts.putExtra("major",major);
                golibarts.putExtra("min_libartscredit",min_libartscredit);
                golibarts.putExtra("max_libartscredit",max_libartscredit);
                golibarts.putExtra("max_majorcredit",max_majorcredit);
                startActivity(golibarts);
                return true;

            case R.id.whole_btn:
                Intent gowhole=new Intent(major_page.this, whole_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gowhole.putExtra("hakbeon",hakbeon);
                gowhole.putExtra("major",major);
                gowhole.putExtra("min_libartscredit",min_libartscredit);
                gowhole.putExtra("max_libartscredit",max_libartscredit);
                gowhole.putExtra("max_majorcredit",max_majorcredit);
                startActivity(gowhole);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
