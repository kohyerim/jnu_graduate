package com.example.jnu_graduate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class whole_page extends AppCompatActivity {

    Context context;
    GradeParser gradeParser;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    Intent intent = getIntent();
    private int detailsubjectnum=0;
    private String hakbeon = null;
    private String major = null;
    private String min_libartscredit = null;
    private String max_libartscredit = null;
    private String max_majorcredit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_page);
        Toolbar tb = findViewById(R.id.toolbar4);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("전체 학점");

        Intent intent=getIntent();
        hakbeon = intent.getExtras().getString("hakbeon");

        major = intent.getExtras().getString("major");

        min_libartscredit = intent.getExtras().getString("min_libartscredit");

        max_libartscredit = intent.getExtras().getString("max_libartscredit ");

        max_majorcredit = intent.getExtras().getString("max_majorcredit");




        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.toolbar4;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.whole_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의



        new Thread() {
            public void run() {
                gradeParser = new GradeParser();
                //학점지정
                ArrayList<String> libartscredit=new ArrayList<String>();
                libartscredit.add("교양 학점");
                libartscredit.add("???"+"/"+min_libartscredit+"-"+max_libartscredit);
                ArrayList<String> majorcredit=new ArrayList<String>();
                majorcredit.add("전공 학점");
                majorcredit.add("???"+"/"+max_majorcredit);
                ArrayList<String> normalcredit=new ArrayList<String>();
                normalcredit.add("일반 선택");
                normalcredit.add("???");
                final ArrayList<ArrayList<String>> totallist=new ArrayList<ArrayList<String>>();
                totallist.add(libartscredit);
                totallist.add(majorcredit);
                totallist.add(normalcredit);
                //일반 과목지정
                ArrayList<String> normalsubject=new ArrayList<String>();
                normalsubject.add("20XX-1");
                normalsubject.add("과목1");
                normalsubject.add("과목2");
                ArrayList<String> normalsubject2=new ArrayList<String>();
                normalsubject2.add("20XX-2");
                normalsubject2.add("과목3");
                normalsubject2.add("과목4");
                final ArrayList<ArrayList<String>> normallist=new ArrayList<ArrayList<String>>();
                normallist.add(normalsubject);
                normallist.add(normalsubject2);
                //
                final addcontainer wholecontainer = new addcontainer();


                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        easycreatecontainer(wholecontainer,"전체 학점", 20, totallist,normallist);


                    }
                });
            }
        }.start();









    }





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts ,int _progress, ArrayList<ArrayList<String>> lists, ArrayList<ArrayList<String>> lists2 ){
        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //레이아웃을 만들어줄 객체를 생성
        //addcontainer maincontainer1 = new addcontainer(); 함수화를 위하여 add컨테이너 대신 simplecontainer로 변경
        //높이를 계산하기위해 데이터를 다집어넣어줌

        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            simplecontainer.calculateheigth(childlist);
        }
        for(int i=0; i<lists2.size();i++){
            ArrayList childlist= lists2.get(i);
            simplecontainer.calculateheigth(childlist);
        }
        //---------------------------------------------------------------------------컨테이너 만들기

        //객체에 컨텍스트,들어갈 레이아웃 이전 view의id를 주고 컨테이너 생성
        simplecontainer.setContainer(context,constraintLayout,prevcontainerid,_texts);
        simplecontainer.createcontainer();

        //---------------------------------------------------------------------------프로그래스바 생성
        //프로그래스바 설정
        int progress=_progress;
        simplecontainer.setprogressbar(progress);
        //프로그래스바 생성
        simplecontainer.createprogressbar();
        //-----------------------  세부과목및 과목생성
        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            simplecontainer.createsubjectmenu(childlist);
        }
        for(int i=0; i<lists2.size();i++){
            ArrayList childlist= lists2.get(i);
            simplecontainer.createsubjectmenu2(childlist);
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
                Intent golobby=new Intent(whole_page.this, lobby.class);
                startActivity(golobby);
            case R.id.libarts_btn:
                Intent golibarts=new Intent(whole_page.this, libarts_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                golibarts.putExtra("hakbeon",hakbeon);
                golibarts.putExtra("major",major);
                golibarts.putExtra("min_libartscredit",min_libartscredit);
                golibarts.putExtra("max_libartscredit",max_libartscredit);
                golibarts.putExtra("max_majorcredit",max_majorcredit);
                startActivity(golibarts);
                return true;
            case R.id.major_btn:
                Intent gomajor=new Intent(whole_page.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon",hakbeon);
                gomajor.putExtra("major",major);
                gomajor.putExtra("min_libartscredit",min_libartscredit);
                gomajor.putExtra("max_libartscredit",max_libartscredit);
                gomajor.putExtra("max_majorcredit",max_majorcredit);
                startActivity(gomajor);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
