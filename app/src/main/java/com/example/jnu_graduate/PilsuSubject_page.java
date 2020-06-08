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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PilsuSubject_page extends AppCompatActivity {

    Context context;
    GradeParser gradeParser;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    private int detailsubjectnum=0;
    private String hakbeon = null;
    private String major = null;
    OpenJSONFile opener;
    JSONObject classJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilsu_subject_page);
        Toolbar tb = findViewById(R.id.toolbar5);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("필수 학점");
        Intent intent = getIntent();
        hakbeon = intent.getExtras().getString("hakbeon");
        major = intent.getExtras().getString("major");


        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.toolbar5;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.Pilsu_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의


        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            public void run() {
                gradeParser = new GradeParser();
                try {
                    opener = new OpenJSONFile(openFileInput("parsedClass.json"), getApplicationContext());
                    classJson = opener.getJSONObject();
                } catch (FileNotFoundException | JSONException e) {
                    e.printStackTrace();
                }

                try {
                    // 전공필수 과목 참고코드
                    // 해당 키값에 맞는 곳에 배열에 들어있는 과목 출력해주시면 됩니다!
                    JSONObject majorInfo = gradeParser.eachMajor();
                    Containerhelper containerhelper=new Containerhelper();
                    containerhelper.makePilsuStartSetting(hakbeon,classJson,majorInfo);
                    final ArrayList<ArrayList<String>> libarts_grouparr=containerhelper.get_libartsgrouparr();
                    final ArrayList<ArrayList<String>> major_grouparr=containerhelper.get_majorgrouparr();
                    final addcontainer addcontainer1=new addcontainer();
                    final String title="필수학점";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            easycreatecontainer(addcontainer1,title,libarts_grouparr,major_grouparr);
                        }
                    });

                }  catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 교양 구분 db
    // ex . getFiled("기초교양", "수와논리")

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts , ArrayList<ArrayList<String>> lists, ArrayList<ArrayList<String>> lists2){
        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //레이아웃을 만들어줄 객체를 생성
        //addcontainer maincontainer1 = new addcontainer(); 함수화를 위하여 add컨테이너 대신 simplecontainer로 변경
        //높이를 계산하기위해 데이터를 다집어넣어줌
        ArrayList<String> imsilist1=new ArrayList<>();
        imsilist1.add("교양필수");
        imsilist1.add("");
        ArrayList<String> imsilist2=new ArrayList<>();
        imsilist2.add("전공필수");
        imsilist2.add("");
        simplecontainer.calculateheigth(imsilist1);
        simplecontainer.calculateheigth(imsilist2);

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

        //-----------------------  세부과목및 과목생성

        simplecontainer.createsubjectmenu3(imsilist1);
        for(int i=0; i<lists.size();i++){
            ArrayList childlist= lists.get(i);
            System.out.println("가져온 배열을 봅시다");
            System.out.println(childlist);
            System.out.println("가져온 배열을 봅시다");
            simplecontainer.createsubjectmenu4(childlist);
        }

        simplecontainer.createsubjectmenu3(imsilist2);
        for(int i=0; i<lists2.size();i++){
            ArrayList childlist= lists2.get(i);
            System.out.println("가져온 배열을 봅시다");
            System.out.println(childlist);
            System.out.println("가져온 배열을 봅시다");

            simplecontainer.createsubjectmenu4(childlist);
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
                Intent golobby=new Intent(PilsuSubject_page.this, lobby.class);
                startActivity(golobby);
            case R.id.libarts_btn:
                Intent golibarts=new Intent(PilsuSubject_page.this, libarts_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                golibarts.putExtra("hakbeon",hakbeon);
                golibarts.putExtra("major",major);
                startActivity(golibarts);
                return true;
            case R.id.major_btn:
                Intent gomajor=new Intent(PilsuSubject_page.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon",hakbeon);
                gomajor.putExtra("major",major);
                startActivity(gomajor);
                return true;

            case R.id.whole_btn:
                Intent gowhole=new Intent(PilsuSubject_page.this, whole_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gowhole.putExtra("hakbeon",hakbeon);
                gowhole.putExtra("major",major);
                startActivity(gowhole);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
