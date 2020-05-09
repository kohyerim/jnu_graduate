package com.example.jnu_graduate;


import android.content.Context;
import android.os.Build;
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

    private int menucounter=0;
    private int detailsubjectcounter=0;
    private int prevviewid;
    private int detailsubjectnumber=0;
    private int subjectmenunumber=0;
    private int progressbarid;
    private int prevcontainer;
    private int containerid;
    private float containerHeight=80;

    private float dpToPx(float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    //컨테이너 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createcontainer(){
        Constraints.LayoutParams params = new Constraints.LayoutParams((int) dpToPx(375), (int) dpToPx(containerHeight));
        params.startToStart = Constraints.LayoutParams.PARENT_ID;
        params.topToBottom = prevcontainer;
        params.setMarginStart((int)(dpToPx(16)));
        params.leftMargin = (int)(dpToPx(16));
        params.topMargin = (int)(dpToPx(20));
        container.setLayoutParams(params);
        container.setBackground(ContextCompat.getDrawable(context,R.drawable.libart_innercontain));
        constraintLayout.addView(container);
    }

    public void setContainer(Context context, TextView container, ConstraintLayout constraintLayout, int prevcontainer) {
        this.context = context;
        this.container =container;
        this.constraintLayout = constraintLayout;
        this.prevcontainer = prevcontainer;
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
    public void setmainText (TextView maintext, int containerid) {
        this.maintext=maintext;
        this.containerid=containerid;
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
    public void setprogressbar (ProgressBar progressbar) {
        this.progressbar=progressbar;
    }

    //세부과목 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu(ArrayList subjectlist){
        //
        TextView detailsubject= new TextView(context);
        TextView subjectmenu= new TextView(context);
        Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Constraints.LayoutParams params2 = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //
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
        //이제 메뉴 만들기
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
    public void setprogressbarid (int progressbarid) {
        this.progressbarid=progressbarid;
    }

    public void calculateheigth(ArrayList arrayList){
        detailsubjectcounter++;
        for(int i=1; i<arrayList.size();i++){
            menucounter++;
        }

        containerHeight=80+detailsubjectcounter*24+menucounter*18;
    }
}
