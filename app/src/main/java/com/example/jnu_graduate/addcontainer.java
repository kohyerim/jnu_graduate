package com.example.jnu_graduate;


import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
    private boolean chainred=true;
    private String here;
    private String max;
    private int bigmenucounter=0;
    private int menucounter=0;
    private int detailsubjectcounter=0;
    private int detailsubjectnumber=0;
    private int subjectmenunumber=0;
    private int bigsubjectmenunumber=0;
    private float containerHeight=80;
    private int yaubun=0;
    private boolean ismax=false;

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
        prevviewid=containerid;
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
        Constraints.LayoutParams params = new Constraints.LayoutParams((int) dpToPx(365), (int) dpToPx(62));
        params.startToStart = containerid;
        params.topToTop = containerid;
        params.setMarginStart((int)(dpToPx(12)));
        params.leftMargin = (int)(dpToPx(12));
        params.topMargin = (int)(dpToPx(22));
        p_Adapter=new progressbar_r_c_adapter(progressbarlist,here,max);
        progressbar_r_c.setAdapter(p_Adapter);
        progressbar_r_c.setLayoutParams(params);
        progressbar_r_c.setLayoutManager(new LinearLayoutManager(context)) ;
        constraintLayout.addView(progressbar_r_c);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setprogressbar (String here, String max) {
        progressbarid=View.generateViewId();
        RecyclerView imsicontainer=new RecyclerView(context);
        imsicontainer.setId(progressbarid);
        progressbar_r_c=imsicontainer;
        progressbar_item item = new progressbar_item();
        progressbarlist.add(item);
        prevviewid=progressbarid;
        int _here=Integer.parseInt(here);
        int _max=Integer.parseInt(max);
        if(_here>=_max){
            ismax=true;
        }

        this.here=here;
        this.max=max;
    }

    //세부과목 만들기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu(ArrayList subjectlist){
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        yaubun=0;
        int detailmarginstart= (int)(dpToPx(16));
        int detailleftmargin= (int)(dpToPx(16));
        int detailtopmargin= (int)(dpToPx(24*detailsubjectnumber+18*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        subjectmenuresource(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu2(ArrayList subjectlist){
        yaubun=0;
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        int detailmarginstart= (int)(dpToPx(40));
        int detailleftmargin= (int)(dpToPx(40));
        int detailtopmargin= (int)(dpToPx(24*detailsubjectnumber+18*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        subjectmenuresource(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void subjectmenuresource(ArrayList subjectlist, int detailmarginstart, int detailleftmargin, int detailtopmargin) {
        subject_r_c_adapter adapter1  = null ;
        subject_r_c_adapter adapter2  = null ;
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
            adapter1 = new subject_r_c_adapter(onlydetail,1);
        }
        if(subjectlist.size()<2){
            if(ismax){
                adapter1 = new subject_r_c_adapter(onlydetail,2);
            }
            else{
                adapter1 = new subject_r_c_adapter(onlydetail,0);
            }
        }


        if(subjectlist.get(0).equals("일반선택")){
            adapter1 = new subject_r_c_adapter(onlydetail,1);
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
        params2.topMargin = (int)(dpToPx(24*(detailsubjectnumber-1)+18*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
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
        yaubun=(subjectmenunumber/8)*30;
        if(subjectlist.size()>35){
            subjectmenunumber=0;
            bigsubjectmenunumber++;
            yaubun=135;
        }

        subjectmenu.setLayoutParams(params2);
        subjectmenu.setLayoutManager(new LinearLayoutManager(context));
        adapter2 = new subject_r_c_adapter(onlysubject,1);
        adapter2.notifyDataSetChanged();
        subjectmenu.setAdapter(adapter2);

        constraintLayout.addView(subjectmenu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu3(ArrayList subjectlist){
        yaubun=0;
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        int detailmarginstart= (int)(dpToPx(16));
        int detailleftmargin= (int)(dpToPx(16));
        int detailtopmargin= (int)(dpToPx(60+24*detailsubjectnumber+18*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        subjectmenuresource2(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu4(ArrayList subjectlist){
        yaubun=0;
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        int detailmarginstart= (int)(dpToPx(40));
        int detailleftmargin= (int)(dpToPx(40));
        int detailtopmargin= (int)(dpToPx(60+24*detailsubjectnumber+18*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        subjectmenuresource2(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void subjectmenuresource2(ArrayList subjectlist, int detailmarginstart, int detailleftmargin, int detailtopmargin) {
        subject_r_c_adapter adapter1  = null ;
        subject_r_c_adapter adapter2  = null ;
        RecyclerView detailsubject= new RecyclerView(context);
        RecyclerView subjectmenu= new RecyclerView(context);
        Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Constraints.LayoutParams params2 = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //세부과목부터
        params.startToStart = containerid;
        params.topToTop = containerid;
        prevviewid= View.generateViewId();
        detailsubject.setId(prevviewid);
        detailsubjectnumber++;

        ArrayList<String> onlydetail=new ArrayList<String>();
        String text1=subjectlist.get(0).toString();
        onlydetail.add(text1);
        //
        detailsubject.setLayoutManager(new LinearLayoutManager(context));

        if(subjectlist.size()>=2){
            adapter1 = new subject_r_c_adapter(onlydetail,1);
        }
        if(subjectlist.size()<2){
            if(ismax){
                adapter1 = new subject_r_c_adapter(onlydetail,2);
            }
            else{
                adapter1 = new subject_r_c_adapter(onlydetail,0);
            }
        }

        adapter1.notifyDataSetChanged();
        detailsubject.setAdapter(adapter1);

        //
        params.setMarginStart(detailmarginstart);
        params.leftMargin = detailleftmargin;
        params.topMargin = detailtopmargin;
        detailsubject.setLayoutParams(params);
        constraintLayout.addView(detailsubject);

        //교과목

        params2.setMarginStart((int)(dpToPx(8)));
        params2.leftMargin = (int)(dpToPx(8));
        params2.topMargin = (int)(dpToPx(24*(detailsubjectnumber-1)+18*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        params2.startToStart = prevviewid;
        params2.topToTop = containerid;

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
        yaubun=(subjectmenunumber/8)*30;
        if(subjectlist.size()>35){
            subjectmenunumber=0;
            bigsubjectmenunumber++;
            yaubun=135;
        }

        subjectmenu.setLayoutParams(params2);
        subjectmenu.setLayoutManager(new LinearLayoutManager(context));
        adapter2 = new subject_r_c_adapter(onlysubject, 1);
        adapter2.notifyDataSetChanged();
        subjectmenu.setAdapter(adapter2);
        constraintLayout.addView(subjectmenu);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createsubjectmenu5(ArrayList subjectlist){
        //세부과목과 교과목이 들어갈 텍스트뷰와 그것을 구성하는 옵션들이 들어갈것 정의
        yaubun=0;
        int detailmarginstart= (int)(dpToPx(16));
        int detailleftmargin= (int)(dpToPx(16));
        int detailtopmargin= (int)(dpToPx(40+50*detailsubjectnumber+20*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        subjectmenuresource3(subjectlist, detailmarginstart, detailleftmargin, detailtopmargin);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void subjectmenuresource3(ArrayList subjectlist, int detailmarginstart, int detailleftmargin, int detailtopmargin) {
        subject_r_c_adapter adapter1  = null ;
        subject_r_c_adapter adapter2  = null ;
        RecyclerView detailsubject= new RecyclerView(context);
        RecyclerView subjectmenu= new RecyclerView(context);
        Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Constraints.LayoutParams params2 = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //세부과목부터
        params.startToStart = containerid;
        params.topToTop = containerid;
        prevviewid= View.generateViewId();
        detailsubject.setId(prevviewid);
        detailsubjectnumber++;
        ArrayList<String> onlydetail=new ArrayList<String>();
        String text1=subjectlist.get(0).toString()+":";
        onlydetail.add(text1);
        detailsubject.setLayoutManager(new LinearLayoutManager(context));
        adapter1 = new subject_r_c_adapter(onlydetail,1);
        adapter1.notifyDataSetChanged();
        detailsubject.setAdapter(adapter1);
        params.setMarginStart(detailmarginstart);
        params.leftMargin = detailleftmargin;
        params.topMargin = (detailtopmargin);
        detailsubject.setLayoutParams(params);
        constraintLayout.addView(detailsubject);

        //교과목

        params2.setMarginStart((int)(dpToPx(8)));
        params2.leftMargin = (int)(dpToPx(13));
        params2.topMargin = (int)(dpToPx(64+50*(detailsubjectnumber-1)+20*subjectmenunumber+yaubun+bigsubjectmenunumber*36*18));
        params2.startToStart = prevviewid;
        params2.topToTop = containerid;

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
        yaubun=(subjectmenunumber/8)*30;
        if(subjectlist.size()>35){
            subjectmenunumber=0;
            bigsubjectmenunumber++;
            yaubun=135;
        }

        subjectmenu.setLayoutParams(params2);
        subjectmenu.setLayoutManager(new LinearLayoutManager(context));
        adapter2 = new subject_r_c_adapter(onlysubject,1);
        adapter2.notifyDataSetChanged();
        subjectmenu.setAdapter(adapter2);

        constraintLayout.addView(subjectmenu);
    }


    public void calculateheigth(ArrayList arrayList){
        detailsubjectcounter++;
        for(int i=1; i<arrayList.size();i++){
            menucounter++;
        }
        if(arrayList.size()==1){
            menucounter++;
        }
        yaubun=(menucounter/8)*30;
        if(menucounter>35){
            bigmenucounter++;
            menucounter=0;
            yaubun=135;
        }



        containerHeight=yaubun+118+detailsubjectcounter*24+menucounter*18+bigmenucounter*18*36;
    }
    public void calculateheigth2(ArrayList arrayList){
        detailsubjectcounter++;
        for(int i=1; i<arrayList.size();i++){
            menucounter++;
        }
        if(arrayList.size()==1){
            menucounter++;
        }
        yaubun=(menucounter/8)*30;
        if(menucounter>35){
            bigmenucounter++;
            menucounter=0;
            yaubun=135;
        }
        containerHeight=yaubun+100+detailsubjectcounter*50+menucounter*18+bigmenucounter*18*36;
    }

}
