package com.example.jnu_graduate;


import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class addcontainer {

    Context context;
    RecyclerView container;
    ConstraintLayout constraintLayout;
    RecyclerView progressbar_r_c;

    private int prevcontainerid;
    private int containerid;
    private int prevviewid;
    private int progressbarid;


    private int menucounter=0;
    private int detailsubjectcounter=0;
    private int detailsubjectnumber=0;
    private int subjectmenunumber=0;
    private float containerHeight=80;

    progressbar_r_c_adapter p_Adapter = null;
    container_r_c_adapter c_Adapter = null ;
    ArrayList<containeritem> containerlist = new ArrayList<containeritem>();
    ArrayList<progressbar_item> progressbarlist = new ArrayList<progressbar_item>();
    private String defaulttextcolor = "#0054FF";
    private String warningtextcolor = "#FF0000";
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
        c_Adapter = new container_r_c_adapter(containerlist, (int) dpToPx(containerHeight),context);
        container.setAdapter(c_Adapter) ;
        container.setLayoutParams(params);
        container.setLayoutManager(new LinearLayoutManager(context)) ;
        constraintLayout.addView(container);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setContainer(Context context, ConstraintLayout constraintLayout, int prevcontainerid, String intotext) {
        this.context = context;
        this.containerid=View.generateViewId();
        RecyclerView imsicontainer=new RecyclerView(context);
        imsicontainer.setId(containerid);
        this.container= imsicontainer;
        this.constraintLayout = constraintLayout;
        this.prevcontainerid = prevcontainerid;
        containeritem item = new containeritem();
        item.setText(intotext);
        containerlist.add(item);
    }

    public int getContainerid(){
        return containerid;
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
        p_Adapter=new progressbar_r_c_adapter(progressbarlist);
        progressbar_r_c.setAdapter(p_Adapter);
        progressbar_r_c.setLayoutParams(params);
        progressbar_r_c.setLayoutManager(new LinearLayoutManager(context)) ;
        constraintLayout.addView(progressbar_r_c);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setprogressbar (int progress) {
        progressbarid=View.generateViewId();
        RecyclerView imsicontainer=new RecyclerView(context);
        imsicontainer.setId(progressbarid);
        progressbar_r_c=imsicontainer;
        progressbar_item item = new progressbar_item();
        item.setProgress(progress);
        progressbarlist.add(item);
        prevviewid=progressbarid;
    }

    //세부과목 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu(ArrayList subjectlist){
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        int detailmarginstart= (int)(dpToPx(16));
        int detailleftmargin= (int)(dpToPx(16));
        int detailtopmargin= (int)(dpToPx(24*detailsubjectnumber+18*subjectmenunumber));
        subjectmenuresource(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu2(ArrayList subjectlist){
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        int detailmarginstart= (int)(dpToPx(40));
        int detailleftmargin= (int)(dpToPx(40));
        int detailtopmargin= (int)(dpToPx(24*detailsubjectnumber+18*subjectmenunumber));
        subjectmenuresource(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void subjectmenuresource(ArrayList subjectlist, int detailmarginstart, int detailleftmargin, int detailtopmargin) {
        onlysubject_r_c_adapter adapter1  = null ;
        onlysubject_r_c_adapter adapter2  = null ;
        RecyclerView detailsubject= new RecyclerView(context);
        RecyclerView subjectmenu= new RecyclerView(context);
        Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Constraints.LayoutParams params2 = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //세부과목부터
        params.startToStart = containerid;
        params.topToBottom = progressbarid;
        prevviewid= View.generateViewId();
        detailsubject.setId(prevviewid);
        detailsubjectnumber++;

        ArrayList<String> onlydetail=new ArrayList<String>();
        String text1=subjectlist.get(0).toString()+":";
        onlydetail.add(text1);
        //
        detailsubject.setLayoutManager(new LinearLayoutManager(context));
        if(subjectlist.size()>=2){
            adapter1 = new onlysubject_r_c_adapter(onlydetail,true);
        }

        if(subjectlist.size()<2){
            adapter1 = new onlysubject_r_c_adapter(onlydetail,false);
        }
        adapter1.notifyDataSetChanged();
        detailsubject.setAdapter(adapter1);

        //
        params.setMarginStart(detailmarginstart);
        params.leftMargin = detailleftmargin;
        params.topMargin = (detailtopmargin);
        detailsubject.setLayoutParams(params);
        constraintLayout.addView(detailsubject);

        //교과목

        params2.setMarginStart((int)(dpToPx(8)));
        params2.leftMargin = (int)(dpToPx(8));
        params2.topMargin = (int)(dpToPx(24*(detailsubjectnumber-1)+18*subjectmenunumber ));
        params2.startToEnd = prevviewid;
        params2.topToBottom = progressbarid;

        ArrayList<String> onlysubject=new ArrayList<String>();
        if(subjectlist.size()==1){
            String imsi="";
            onlysubject.add(imsi);
            subjectmenunumber++;
        }
        for(int i=1; i<subjectlist.size();i++){
            String imsi= subjectlist.get(i).toString();
            onlysubject.add(imsi);
            subjectmenunumber++;
        }

        subjectmenu.setLayoutParams(params2);
        subjectmenu.setLayoutManager(new LinearLayoutManager(context));
        adapter2 = new onlysubject_r_c_adapter(onlysubject,true);
        adapter2.notifyDataSetChanged();
        subjectmenu.setAdapter(adapter2);

        constraintLayout.addView(subjectmenu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createnodivisionsubjectmenu(ArrayList subjectlist){
        //세부과목없는 전공과목나열
        onlysubject_r_c_adapter adapter  = null ;
        RecyclerView noDetailsubject= new RecyclerView(context);
        Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //파라미터 정의
        params.startToStart = containerid;
        params.topToBottom = progressbarid;
        params.setMarginStart((int)(dpToPx(16)));
        params.leftMargin = (int)(dpToPx(16));
        params.topMargin = (int)(dpToPx(10));
        prevviewid= View.generateViewId();
        noDetailsubject.setId(prevviewid);
        detailsubjectnumber++;
        noDetailsubject.setLayoutParams(params);

        for(int i=0; i<subjectlist.size();i++){
            subjectmenunumber++;
        }
        //
        noDetailsubject.setLayoutManager(new LinearLayoutManager(context));
        adapter=new onlysubject_r_c_adapter(subjectlist,true);
        adapter.notifyDataSetChanged();
        noDetailsubject.setAdapter(adapter);
        constraintLayout.addView(noDetailsubject);
    }


    public void calculateheigth(ArrayList arrayList){
        detailsubjectcounter++;
        for(int i=1; i<arrayList.size();i++){
            menucounter++;
        }
        if(arrayList.size()==1){
            menucounter++;
        }
        containerHeight=80+detailsubjectcounter*24+menucounter*18;
    }

    public void onlymajorcalculateheigth(ArrayList arrayList){
        detailsubjectcounter++;// 메뉴가아니라 세부로 사용
        for(int i=0; i<arrayList.size();i++){
            menucounter++;
        }

        containerHeight=80+detailsubjectcounter*26+menucounter*28;
    }
}
