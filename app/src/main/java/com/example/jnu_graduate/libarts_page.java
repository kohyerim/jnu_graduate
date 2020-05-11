package com.example.jnu_graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class libarts_page extends AppCompatActivity {

    Context context;
    private int prevcontainerid;
    ConstraintLayout constraintLayout;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libarts_page);

        //--------------------------------------------예시로 세부분화와 과목들을 arraylist로 만듬
        ArrayList basiclibartsmenu= new ArrayList();
        basiclibartsmenu.add("글쓰기:");
        basiclibartsmenu.add("글쓰기");

        ArrayList basiclibartsmenu2= new ArrayList();
        basiclibartsmenu2.add("외국어:");
        basiclibartsmenu2.add("영어회화2");

        ArrayList basiclibartsmenu3= new ArrayList();
        basiclibartsmenu3.add("논리적사고:");
        basiclibartsmenu3.add("수와논리");

        ArrayList majorartssearch1= new ArrayList();
        majorartssearch1.add("공학 계열:");
        majorartssearch1.add("정보통신과 컴퓨터개론");
        majorartssearch1.add("물과산업");
        majorartssearch1.add("C프로그래밍 및 실습");

        ArrayList majorartssearch2= new ArrayList();
        majorartssearch2.add("타 계열:");
        majorartssearch2.add("전쟁과 평화");

        ArrayList juninarts1= new ArrayList();
        juninarts1.add("언어와 문학:");
        juninarts1.add("멀티미디어와 영어 콘텐츠");

        ArrayList juninarts2= new ArrayList();
        juninarts2.add("역사와 철학:");
        juninarts2.add("");

        ArrayList juninarts3= new ArrayList();
        juninarts3.add("사회와 문학:");
        juninarts3.add("인권과 법");

        ArrayList juninarts4= new ArrayList();
        juninarts4.add("과학과 기술:");
        juninarts4.add("발명과 특허속 생활 속 프로그래밍 수학");

        ArrayList juninarts5= new ArrayList();
        juninarts5.add("예술과 건강:");
        juninarts5.add("");

        ArrayList juninarts6= new ArrayList();
        juninarts6.add("인성과 융,복합사고:");
        juninarts6.add("문화광장");

        ArrayList Jnuarts= new ArrayList();
        Jnuarts.add("멀티미디어 제작:");
        Jnuarts.add("");
        //----------------------------------------------------------------기초정의-한 액티비티당 한번만
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기-기본적으로 미리 설정되어있는 레이아웃의 맨위쪽에 잇는 텍스트박스id
        prevcontainerid=R.id.texting;
        context=getApplicationContext();//컨텍스트 정의
        constraintLayout=findViewById(R.id.libarts_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의

        addcontainer container1=new addcontainer();
        easycreatecontainer(container1,"기초교양",20, basiclibartsmenu,basiclibartsmenu2,basiclibartsmenu3);
        addcontainer container2=new addcontainer();
        easycreatecontainer(container2,"전공탐색",30, majorartssearch1,majorartssearch2);
        addcontainer container3=new addcontainer();
        easycreatecontainer(container3,"전인교양",40, juninarts1,juninarts2,juninarts3,juninarts4,juninarts5,juninarts6);
        addcontainer container4=new addcontainer();
        easycreatecontainer(container4,"JNU특성교양",50, Jnuarts);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void easycreatecontainer(addcontainer simplecontainer ,String _texts, int _progress, ArrayList...lists){
        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //레이아웃을 만들어줄 객체를 생성
        //addcontainer maincontainer1 = new addcontainer(); 함수화를 위하여 add컨테이너 대신 simplecontainer로 변경
        //높이를 계산하기위해 데이터를 다집어넣어줌
        for(ArrayList menu:lists){
            simplecontainer.calculateheigth(menu);
        }
        //---------------------------------------------------------------------------컨테이너 만들기

        //객체에 컨텍스트,들어갈 레이아웃 이전 view의id를 주고 컨테이너 생성
        simplecontainer.setContainer(context,constraintLayout,prevcontainerid);
        simplecontainer.createcontainer();
        //---------------------------------------------------------------------------------텍스트생성
        //컨테이너에 들어갈 메인 text 생성 및 id정의
        String maintext=_texts;
        simplecontainer.setmainText(maintext);
        //메인텍스트 생성
        simplecontainer.createmainText();
        //---------------------------------------------------------------------------프로그래스바 생성
        //프로그래스바 설정
        int progress=_progress;
        simplecontainer.setprogressbar(progress);
        //프로그래스바 생성
        simplecontainer.createprogressbar();
        //-----------------------  세부과목및 과목생성
        for(ArrayList menu:lists){
            simplecontainer.createsubjectmenu(menu);
        }
        //마지막으로 이 컨테이너가 마지막인것을 저장
        prevcontainerid=simplecontainer.getContainerid();





    }


}
