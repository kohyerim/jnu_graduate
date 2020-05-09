package com.example.jnu_graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class libarts_page extends AppCompatActivity {

    Context context;
    public float dpToPx(float dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libarts_page);

        context=getApplicationContext();//컨텍스트 정의
        ConstraintLayout constraintLayout;
        constraintLayout=findViewById(R.id.libarts_page_layout);//앞으로 들어갈 뷰들의 레이아웃 정의
        //--------------------------------------------예시로 세부분화와 과목들을 arraylist로 만듬
        ArrayList container1subjectlist1= new ArrayList();
        container1subjectlist1.add("글쓰기");
        container1subjectlist1.add("글쓰기");

        ArrayList container1subjectlist2= new ArrayList();
        container1subjectlist2.add("외국어");
        container1subjectlist2.add("영어회화1");
        container1subjectlist2.add("영어회화2");

        ArrayList container1subjectlist3= new ArrayList();
        container1subjectlist3.add("논리적사고");
        container1subjectlist3.add("수와논리");
        container1subjectlist3.add("수와논리2");
        container1subjectlist3.add("수와논리3");

        ArrayList container1subjectlist4= new ArrayList();
        container1subjectlist4.add("안드로메다로향하는");
        container1subjectlist4.add("나의멘탈");
        container1subjectlist4.add("살려조..");
        container1subjectlist4.add("개발 넘나 어려운것");
        container1subjectlist4.add("이거한다고 12시간썻어...");


        //레이아웃을 만들어줄 객체를 생성
        addcontainer maincontainer1 = new addcontainer();
        maincontainer1.calculateheigth(container1subjectlist1);
        maincontainer1.calculateheigth(container1subjectlist2);
        maincontainer1.calculateheigth(container1subjectlist3);
        maincontainer1.calculateheigth(container1subjectlist4);

        //---------------------------------------------------------------------------여기부턴 뷰정의1
        //컨테이너 생성및 id정의
        TextView container1 = new TextView(context);
        container1.setId(R.id.libartscontainer1);  //뷰정의1 이니까 1번을쓴다
        //컨테이너가 들어가서 위치를 잡을 기준점인 이전 view의 id를 찾아내기
        int prevcontainer1=R.id.texting;
        //객체에 id와 컨텍스트, 레이아웃, 이전 컨테이너를 주고 (컨테이너 생성->containerheight계산을 위해 맨마지막으로)
        maincontainer1.setContainer(context,container1,constraintLayout,prevcontainer1);
        maincontainer1.createcontainer();
        //----------------텍스트생성
        //컨테이너에 들어갈 메인 text 생성 및 id정의
        TextView maintext1 = new TextView(context);
        maintext1.setId(R.id.libarttext1);
        //메인 텍스트에 들어갈 세부분류및 텍스트 색상 적기
        String maintext1text = "기초교양";
        String maintext1color = "#0054FF";
        maintext1.setText(maintext1text);
        maintext1.setTextColor(Color.parseColor(maintext1color));
        maintext1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
        //메인텍스트가 들어가서 위치를 잡을 기준점인 컨테이너의 id를 주기
        int container1id=R.id.libartscontainer1;//컨테이너생성및id정의2번째의 id와 같은것
        maincontainer1.setmainText(maintext1,container1id);
        //메인텍스트 생성
        maincontainer1.createmainText();
        //-------------------프로그래스바 생성
        //컨테이너에 들어갈 진행바 생성 및 id정의
        ProgressBar progressBar1 = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);//다른 위젯으로 쓸경우 진행도가 표시가 안됨.
        progressBar1.setId(R.id.libartprograssbar1);
        //프로그래스바 설정
        maincontainer1.setprogressbar(progressBar1);
        //프로그래스바 진행도 표시 후 프로그래스바 생성
        progressBar1.setProgress(40); // 40이란것은 진행도가 40% 라는뜻- 나중에 따로 학점으로 계산해서 percent를 내야함
        maincontainer1.createprogressbar();

        //-----------------------  세부과목및 과목생성
        int progressbarid1=R.id.libartprograssbar1;

        maincontainer1.setprogressbarid(progressbarid1);
        maincontainer1.createsubjectmenu(container1subjectlist1);
        maincontainer1.createsubjectmenu(container1subjectlist2);
        maincontainer1.createsubjectmenu(container1subjectlist3);
        maincontainer1.createsubjectmenu(container1subjectlist4);


    }

}
