package com.example.jnu_graduate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class whole_page extends AppCompatActivity {
    public static Activity wholeactivity;
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
    private String max_wholecredit;
    private String here_libartscredit =null;
    private String here_majorscredit =null;
    private String here_wholecredit =null;
    JSONObject classJson;
    OpenJSONFile opener;
    ClassParser classParser;
    String linkedmajor=null;
    boolean onoffLinked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_page);
        wholeactivity=whole_page.this;

        Toolbar tb = findViewById(R.id.toolbar4);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        ab.setTitle("전체 학점");
        Intent intent=getIntent();
        hakbeon = intent.getExtras().getString("hakbeon");
        major = intent.getExtras().getString("major");
        linkedmajor=intent.getExtras().getString("linkedmajor");
        onoffLinked=intent.getBooleanExtra("onofflinked",false);


        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.toolbar4;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.whole_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의



        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            public void run() {
                gradeParser = new GradeParser();
                JSONObject gradeInfo = null;
                JSONObject majorInfo = null;
                try {
                    classParser = new ClassParser(openFileInput("class.json"), getApplicationContext());
                    classParser.createParsedClass();
                    opener = new OpenJSONFile(openFileInput("parsedClass.json"), getApplicationContext());
                    classJson = opener.getJSONObject();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }


                try {
                    // 학점 db 불러오기
                    gradeInfo = gradeParser.getMajor();

                    // 교양학점 db조회
                    JSONObject gradePoint = (JSONObject) gradeInfo.get("교양학점");
                    JSONObject cultureGradePoint = (JSONObject) gradePoint.get(hakbeon);
                    Object minGrade = cultureGradePoint.get("min");
                    Object maxGrade = cultureGradePoint.get("max");
                    min_libartscredit=minGrade.toString();
                    max_libartscredit=maxGrade.toString();
                    // 졸업학점 db 조회
                    majorInfo = gradeParser.eachMajor();
                    JSONObject majorGradePoint = (JSONObject) majorInfo.get(hakbeon);
                    Object jolupGP = majorGradePoint.get("졸업학점");
                    Object major = majorGradePoint.get("심화전공");
                    max_wholecredit=jolupGP.toString();
                    max_majorcredit=major.toString();




                    here_libartscredit=String.valueOf(opener.getCultureGP());
                    here_majorscredit=String.valueOf(opener.getMajorGP());
                    here_wholecredit=String.valueOf(opener.getTotalGP());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                gradeParser = new GradeParser();
                //학점지정
                ArrayList<String> libartscredit=new ArrayList<String>();
                libartscredit.add("교양 학점");
                libartscredit.add(here_libartscredit+"/"+min_libartscredit+"-"+max_libartscredit);
                ArrayList<String> majorcredit=new ArrayList<String>();
                majorcredit.add("전공 학점");
                majorcredit.add(here_majorscredit+"/"+max_majorcredit+"~");
                final ArrayList<ArrayList<String>> totallist=new ArrayList<ArrayList<String>>();
                totallist.add(libartscredit);
                totallist.add(majorcredit);
                //일반 과목지정
                final String title="일반선택";
                final addcontainer wholecontainer = new addcontainer();
                final Containerhelper containerhelper=new Containerhelper();
                containerhelper.setStartSetting(title,hakbeon,classJson);
                containerhelper.wholeContainerCreate();
                final ArrayList<ArrayList<String>> normallist=containerhelper.getGrouparr();
                //

                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        easycreatecontainer(wholecontainer,"전체 학점", here_wholecredit,max_wholecredit, totallist,normallist);


                    }
                });
            }
        }.start();









    }





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts ,String now_credit,String max_credit, ArrayList<ArrayList<String>> lists, ArrayList<ArrayList<String>> lists2 ){
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
        //프로그래스바 설정
        simplecontainer.setprogressbar(now_credit,max_credit);
        //프로그래스바 생성
        simplecontainer.createprogressbar();
        //프로그래스바 생성
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
        getMenuInflater().inflate(R.menu.menu_sample1, menu);
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
                golibarts.putExtra("linkedmajor",linkedmajor);
                golibarts.putExtra("onofflinked", onoffLinked);
                startActivity(golibarts);
                return true;
            case R.id.major_btn:
                Intent gomajor=new Intent(whole_page.this, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon",hakbeon);
                gomajor.putExtra("major",major);
                gomajor.putExtra("linkedmajor",linkedmajor);
                gomajor.putExtra("onofflinked", onoffLinked);
                startActivity(gomajor);
                return true;
            case R.id.pilsu_btn:
                Intent gopisu=new Intent(whole_page.this, PilsuSubject_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gopisu.putExtra("hakbeon",hakbeon);
                gopisu.putExtra("major",major);
                gopisu.putExtra("linkedmajor",linkedmajor);
                gopisu.putExtra("onofflinked", onoffLinked);
                startActivity(gopisu);
            case R.id.linked_major_btn:
                if(onoffLinked) {
                    Intent golinked = new Intent(context, Linkedmajor_page.class);
                    // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                    golinked.putExtra("hakbeon", hakbeon);
                    golinked.putExtra("major", major);
                    golinked.putExtra("linkedmajor", linkedmajor);
                    golinked.putExtra("onofflinked", onoffLinked);
                    startActivity(golinked);
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
