package com.example.jnu_graduate;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import org.json.JSONException;
import org.json.JSONObject;

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
    String linkedmajor=null;
    boolean onoffLinked;
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
        linkedmajor=intent.getExtras().getString("linkedmajor");
        onoffLinked=intent.getExtras().getBoolean("onofflinked",false);
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

                    // 전공필수 과목 참고코드
                    // 해당 키값에 맞는 곳에 배열에 들어있는 과목 출력해주시면 됩니다!
                    // 교양세부과목

                    Containerhelper containerhelper=new Containerhelper();
                    containerhelper.setnewStartSetting(hakbeon,classJson);
                    containerhelper.libartsContainercreate();
                    ArrayList<ArrayList<ArrayList<String>>> libartsarraylist=containerhelper.getlibartsArraylist();
                    ArrayList<ArrayList<String>> libartsarraylistmenu=containerhelper.getlibartsArraylistmenu();
                    for(int i=0; i<libartsarraylistmenu.size(); i++){
                        ArrayList<String> containerpackage=libartsarraylistmenu.get(i);
                        final String title=containerpackage.get(0);
                        final String herecredit=containerpackage.get(1);
                        final String maxcredit=containerpackage.get(2);
                        final ArrayList<ArrayList<String>> containercontent=libartsarraylist.get(i);
                        final addcontainer addcontainer1=new addcontainer();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                easycreatecontainer(addcontainer1,title,herecredit,maxcredit,containercontent);
                            }
                        });

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
        getMenuInflater().inflate(R.menu.menu_sample1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.lobby_btn:
                Intent golobby = new Intent(context, lobby.class);
                startActivity(golobby);
                return true;
            case R.id.major_btn:
                Intent gomajor = new Intent(context, major_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gomajor.putExtra("hakbeon", hakbeon);
                gomajor.putExtra("major", major);
                gomajor.putExtra("linkedmajor",linkedmajor);
                gomajor.putExtra("onofflinked", onoffLinked);
                startActivity(gomajor);
                return true;
            case R.id.whole_btn:
                Intent gowhole=new Intent(context, whole_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gowhole.putExtra("hakbeon",hakbeon);
                gowhole.putExtra("major",major);
                gowhole.putExtra("linkedmajor",linkedmajor);
                gowhole.putExtra("onofflinked", onoffLinked);
                startActivity(gowhole);
                return true;
            case R.id.pilsu_btn:
                Intent gopisu=new Intent(context, PilsuSubject_page.class);
                // 학번(2017)하고 전공(컴퓨터공학전공)값 넘겨주기
                gopisu.putExtra("hakbeon",hakbeon);
                gopisu.putExtra("major",major);
                gopisu.putExtra("linkedmajor",linkedmajor);
                gopisu.putExtra("onofflinked", onoffLinked);
                startActivity(gopisu);
                return true;
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