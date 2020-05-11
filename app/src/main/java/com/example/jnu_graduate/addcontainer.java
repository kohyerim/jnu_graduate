package com.example.jnu_graduate;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class addcontainer {

    Context context;
    TextView container;
    ConstraintLayout constraintLayout;
    TextView maintext;
    ProgressBar progressbar;

    private int prevcontainerid;
    private int containerid;
    private int prevviewid;
    private int progressbarid;


    private int menucounter=0;
    private int detailsubjectcounter=0;
    private int detailsubjectnumber=0;
    private int subjectmenunumber=0;
    private float containerHeight=80;

    public float dpToPx(float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    //컨테이너 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createcontainer(){
        Constraints.LayoutParams params = new Constraints.LayoutParams((int) dpToPx(375), (int) dpToPx(containerHeight));
        params.startToStart = Constraints.LayoutParams.PARENT_ID;
        params.topToBottom = prevcontainerid;
        params.setMarginStart((int)(dpToPx(16)));
        params.leftMargin = (int)(dpToPx(16));
        params.topMargin = (int)(dpToPx(20));
        container.setLayoutParams(params);
        container.setBackground(ContextCompat.getDrawable(context,R.drawable.libart_innercontain));
        constraintLayout.addView(container);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setContainer(Context context, ConstraintLayout constraintLayout, int prevcontainerid) {
        this.context = context;
        this.containerid=View.generateViewId();
        TextView imsicontainer=new TextView(context);
        imsicontainer.setId(containerid);
        this.container= imsicontainer;
        this.constraintLayout = constraintLayout;
        this.prevcontainerid = prevcontainerid;
    }

    public int getContainerid(){
        return containerid;
    }


    //텍스트 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createmainText(){
        Constraints.LayoutParams params = new Constraints.LayoutParams((int) dpToPx(77), (int) dpToPx(18));
        params.startToStart = containerid;
        params.topToTop = containerid;
        params.setMarginStart((int)(dpToPx(16)));
        params.leftMargin = (int)(dpToPx(16));
        params.topMargin = (int)(dpToPx(12));
        maintext.setLayoutParams(params);
        constraintLayout.addView(maintext);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setmainText (String intoText) {
        String intotext2=intoText;
        //컨테이너에 들어갈 메인 text 생성 및 id정의
        TextView imsiTextview = new TextView(context);
        int imsiTextid=View.generateViewId();
        imsiTextview.setId(imsiTextid);
        //메인 텍스트에 들어갈 세부분류및 텍스트 색상 적기
        String maintext1text = intoText;
        String maintext1color = "#0054FF";
        imsiTextview.setText(maintext1text);
        imsiTextview.setTextColor(Color.parseColor(maintext1color));
        imsiTextview.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
        this.maintext=imsiTextview;
    }



    //프로그래스바 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createprogressbar(){
        Constraints.LayoutParams params = new Constraints.LayoutParams((int) dpToPx(356), (int) dpToPx(13));
        params.startToStart = containerid;
        params.topToTop = containerid;
        params.setMarginStart((int)(dpToPx(12)));
        params.leftMargin = (int)(dpToPx(12));
        params.topMargin = (int)(dpToPx(40));
        progressbar.setBackground(ContextCompat.getDrawable(context,R.drawable.progress_bar));
        progressbar.setLayoutParams(params);

        constraintLayout.addView(progressbar);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setprogressbar (int progress) {
        ProgressBar imsiprogressbar = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);//다른 위젯으로 쓸경우 진행도가 표시가 안됨.
        progressbarid=View.generateViewId();
        imsiprogressbar.setId(progressbarid);
        imsiprogressbar.setProgress(progress);
        this.progressbar=imsiprogressbar;
    }

    //세부과목 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu(ArrayList subjectlist){
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        TextView detailsubject= new TextView(context);
        TextView subjectmenu= new TextView(context);
        Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Constraints.LayoutParams params2 = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //글자색 정의
        String defaulttextcolor = "#0054FF";
        detailsubject.setTextColor(Color.parseColor(defaulttextcolor));
        subjectmenu.setTextColor(Color.parseColor(defaulttextcolor));

        //세부과목부터
        params.startToStart = containerid;
        params.topToBottom = progressbarid;
        prevviewid= View.generateViewId();
        detailsubject.setId(prevviewid);
        detailsubjectnumber++;
        String text1=subjectlist.get(0).toString();
        detailsubject.setText(text1);
        params.setMarginStart((int)(dpToPx(16)));
        params.leftMargin = (int)(dpToPx(16));
        params.topMargin = (int)(dpToPx(24*detailsubjectnumber+18*subjectmenunumber));
        detailsubject.setLayoutParams(params);
        constraintLayout.addView(detailsubject);
        //교과목
        params2.setMarginStart((int)(dpToPx(8)));
        params2.leftMargin = (int)(dpToPx(8));
        params2.topMargin = (int)(dpToPx(24*detailsubjectnumber+18*subjectmenunumber));
        params2.startToEnd = prevviewid;
        params2.topToBottom = progressbarid;
        String text2="";
        for(int i=1; i<subjectlist.size();i++){
            text2 += subjectlist.get(i).toString();
            text2 += "\n";
            subjectmenunumber++;
        }
        subjectmenu.setText(text2);
        subjectmenu.setLayoutParams(params2);
        constraintLayout.addView(subjectmenu);
    }

    public void calculateheigth(ArrayList arrayList){
        detailsubjectcounter++;
        for(int i=1; i<arrayList.size();i++){
            menucounter++;
        }

        containerHeight=80+detailsubjectcounter*24+menucounter*18;
    }
}
