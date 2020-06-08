package com.example.jnu_graduate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
public class libarts_page extends AppCompatActivity {
    public static Activity libartsactivity;
    Context context;
    GradeParser gradeParser;
    OpenJSONFile opener;
    JSONObject classJson;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    private int detailsubjectnum=0;
    private String hakbeon = null;
    private String major = null;
    private String min_libartscredit = null;
    private String max_libartscredit = null;
    private String max_majorcredit = null;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libarts_page);
        libartsactivity=libarts_page.this;

        Toolbar tb = findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("교양 학점");
        Intent intent = getIntent();
        hakbeon = intent.getExtras().getString("hakbeon");
        major = intent.getExtras().getString("major");
        min_libartscredit = intent.getExtras().getString("min_libartscredit");
        max_libartscredit = intent.getExtras().getString("max_libartscredit ");
        max_majorcredit = intent.getExtras().getString("max_majorcredit");
        System.out.println(hakbeon+"교양");


        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.toolbar2;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.libarts_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의


        new Thread() {
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
                    JSONObject hakbeonInfo = (JSONObject) majorInfo.get(hakbeon);
                    JSONObject cultureInfo = (JSONObject) hakbeonInfo.get("교양필수");
                    System.out.println(cultureInfo);

                    // 교양세부과목
                    JSONObject gradeInfo = gradeParser.getMajor();
                    JSONObject gradePoint = (JSONObject) gradeInfo.get("교양학점");
                    JSONObject cultureGradePoint = (JSONObject) gradePoint.get(hakbeon);
                    JSONObject division = (JSONObject) cultureGradePoint.get("구분");


                    // progress bar하실때 세부학점 받아오는 부분 참고 코드입니다.
                    JSONObject gradePointDetail = (JSONObject) cultureGradePoint.get("세부학점");
                    Iterator divisionI=division.keys();
                    System.out.println("division : " + division);
                    while(divisionI.hasNext()){
                        final String title=divisionI.next().toString();
                        System.out.println(title+"타이틀이 몇개냐");
                        final addcontainer container=new addcontainer();
                        final Containerhelper containerhelper=new Containerhelper();
                        containerhelper.setStartSetting(title,hakbeon,classJson,majorInfo,gradeInfo);
                        containerhelper.cultureContainerCreate();
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

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 교양 구분 db
    // ex . getFiled("기초교양", "수와논리")
    public String getField(String classification, String subjectName) throws JSONException, IOException {
        JSONObject subjectInfo = gradeParser.getCulture(Integer.parseInt(hakbeon));
        JSONObject cultureSub = (JSONObject) subjectInfo.get("교양");
        JSONObject mainDivision = (JSONObject) cultureSub.get(classification);
        Object field = mainDivision.get(subjectName);

        return field.toString();
    }
    public JSONObject getCultureDB(String classification, String _hakbeon) throws JSONException, IOException {
        JSONObject subjectInfo = gradeParser.getCulture(Integer.parseInt(_hakbeon));
        JSONObject cultureSub = (JSONObject) subjectInfo.get("교양");
        JSONObject mainDivision = (JSONObject) cultureSub.get(classification);
        return mainDivision;
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
                Intent golobby = new Intent(libarts_page.this, lobby.class);
                startActivity(golobby);
                return true;
            case R.id.major_btn:
                Intent gomajor = new Intent(libarts_page.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon", hakbeon);
                gomajor.putExtra("major", major);
                startActivity(gomajor);
                return true;
            case R.id.whole_btn:
                Intent gowhole=new Intent(libarts_page.this, whole_page.class);
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