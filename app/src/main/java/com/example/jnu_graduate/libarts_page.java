package com.example.jnu_graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class libarts_page extends AppCompatActivity {

    Context context;
    GradeParser gradeParser;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    private int detailsubjectnum=0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libarts_page);

        Intent intent = getIntent();
        final String hakbeon = intent.getExtras().getString("hakbeon");
        final String major = intent.getExtras().getString("major");

        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.libartspage_main_title;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.libarts_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의


        new Thread() {
            public void run() {
                gradeParser = new GradeParser();
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
                    System.out.println(gradePointDetail);

                    Iterator i = division.keys();

                    ArrayList<addcontainer> containerarr=new ArrayList<addcontainer>();
                    while(i.hasNext())
                    {
                        final ArrayList<ArrayList<String>> grouparr=new ArrayList<ArrayList<String>>();
                        final String b = i.next().toString();

                        JSONArray arr = (JSONArray) cultureGradePoint.get(b.toString());
                        for(int x=0; x<arr.length(); x++){
                            ArrayList<String> childarr = new ArrayList<String>();
                            childarr.add(arr.get(x).toString());
                            childarr.add("음시");
                            grouparr.add(childarr);
                        }
                        final addcontainer container=new addcontainer();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                easycreatecontainer(container,b,20, grouparr);
                            }
                        });

                        containerarr.add(container);
                    }

                    // 교양 구분 db
                    Integer year = 2017;
                    String classification = "기초교양";
                    String subjectName = "수와논리";
                    JSONObject subjectInfo = gradeParser.getCulture(year);
                    JSONObject cultureSub = (JSONObject) subjectInfo.get("교양");
                    JSONObject mainDivision = (JSONObject) cultureSub.get(classification);
                    Object field = mainDivision.get(subjectName);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts ,int _progress, ArrayList<ArrayList<String>> lists){
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
        int progress=_progress;
        simplecontainer.setprogressbar(progress);
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



}
