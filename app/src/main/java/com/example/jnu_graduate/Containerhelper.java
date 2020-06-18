package com.example.jnu_graduate;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Containerhelper {
    GradeParser gradeParser;

    //받아와야할거
    private addcontainer container;
    private String title;
    private JSONObject majorInfo;
    private JSONObject gradeInfo;
    //--divsion subject를 받아오자마자 저장안하고 그냥 바로 가공시작
    private String hakbeon; //10자리인지 4자리인지 확인해야함
    private JSONObject subMaxCredit;
    private JSONObject db;
    private ArrayList<String> etcsubject;
    //안에서 생성될거
    private ArrayList<JSONObject> divisionSubject;
    private int hereCredit=0;
    private String maxCredit;
    private ArrayList<ArrayList<String>> grouparr;
    private boolean needdivision =false;
    //필수 과목
    private ArrayList<ArrayList<String>> libartsgrouparr=new ArrayList<>();
    private ArrayList<ArrayList<String>> majorgrouparr=new ArrayList<>();
    //
    private int pilsumaxcount =0;
    private int pilsuherecount =0;
    //졸업자격
    private ArrayList<ArrayList<String>> graduategrouparr;
    private int injungcredits=9;
    private boolean isJNU=false;
    //다시 리뉴얼된 교양
    ArrayList<ArrayList<ArrayList<String>>> libartsArraylist;
    ArrayList<ArrayList<String>> libartsArraylistMenu;


    public int get_herecredit(){
        return hereCredit;
    }
    public String get_maxcredit(){
        return maxCredit;
    }

    public ArrayList<ArrayList<String>> getGrouparr(){
        return grouparr;
    }
    public void setStartSetting(String title, String hakbeon, JSONObject mysubject){
        this.title = title;
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        try {
            this.majorInfo=gradeParser.eachMajor();
            this.gradeInfo=gradeParser.getMajor();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(title.equals("학문기초")||title.equals("기초교양")){
            needdivision=true;
        }

        ArrayList<String> keyArr = new ArrayList<>();
        divisionSubject=new ArrayList<JSONObject>();
        final String finalTitle;
        if (title.equals("전공탐색교양")){
            finalTitle = "전공탐색";
        }
        else {
            finalTitle = title;
        }

            try {
                Iterator iterator = mysubject.keys();
                while (iterator.hasNext()) {
                    keyArr.add(iterator.next().toString());
                }
                for (int num = 0; num < keyArr.size(); num++) {
                    JSONArray tmp = (JSONArray) mysubject.get(keyArr.get(num));
                    for (int num2 = 0; num2 < tmp.length(); num2++) {
                        JSONObject tmpobj = (JSONObject) tmp.get(num2);
                        if (tmpobj.get("isu_nm").equals(finalTitle)) {
                            divisionSubject.add(tmpobj);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public void setnewStartSetting(String hakbeon, JSONObject mysubject){
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        try {
            this.majorInfo=gradeParser.eachMajor();
            this.gradeInfo=gradeParser.getMajor();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> keyArr = new ArrayList<>();
        divisionSubject=new ArrayList<JSONObject>();

        try {
            Iterator iterator = mysubject.keys();
            while (iterator.hasNext()) {
                keyArr.add(iterator.next().toString());
            }
            for (int num = 0; num < keyArr.size(); num++) {
                JSONArray tmp = (JSONArray) mysubject.get(keyArr.get(num));
                for (int num2 = 0; num2 < tmp.length(); num2++) {
                    JSONObject tmpobj = (JSONObject) tmp.get(num2);
                    if (!tmpobj.get("isu_nm").equals("전공")&&!tmpobj.get("isu_nm").equals("전공필수")&&!tmpobj.get("isu_nm").equals("복수전공(전공)")&&!tmpobj.get("isu_nm").equals("일반선택")) {
                        divisionSubject.add(tmpobj);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setMajorStartSetting(String title, String hakbeon, JSONObject mysubject){
        this.title = title;
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        try {
            this.majorInfo=gradeParser.eachMajor();
            this.gradeInfo=gradeParser.getMajor();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        System.out.println("학번은 어케생겻니"+hakbeon);
        ArrayList<String> keyArr = new ArrayList<>();
        divisionSubject=new ArrayList<JSONObject>();


        try {
            Iterator iterator = mysubject.keys();
            while (iterator.hasNext()) {
                keyArr.add(iterator.next().toString());
            }
            for (int num = 0; num < keyArr.size(); num++) {
                JSONArray majortmp = (JSONArray) mysubject.get(keyArr.get(num));
                for (int num2 = 0; num2 < majortmp.length(); num2++) {
                    JSONObject tmpobj = (JSONObject) majortmp.get(num2);
                    if (tmpobj.get("isu_nm").equals(title)||tmpobj.get("isu_nm").equals("전공필수")||tmpobj.get("isu_nm").equals("복수전공(전공)")) {
                        if(!tmpobj.get("credit").equals("0")){
                            divisionSubject.add(tmpobj);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setlinkedmajorStartSetting(String title, String hakbeon, JSONObject mysubject){
        this.title = title;
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        try {
            this.majorInfo=gradeParser.eachMajor();
            this.gradeInfo=gradeParser.getMajor();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String finaltitle;
        if(title.equals("연계전공")){
            finaltitle="복수전공(전공)";
        }
        else{
            finaltitle=title;
        }
//        System.out.println("학번은 어케생겻니"+hakbeon);
        ArrayList<String> keyArr = new ArrayList<>();
        divisionSubject=new ArrayList<JSONObject>();
        try {
            Iterator iterator = mysubject.keys();
            while (iterator.hasNext()) {
                keyArr.add(iterator.next().toString());
            }
            for (int num = 0; num < keyArr.size(); num++) {
                JSONArray majortmp = (JSONArray) mysubject.get(keyArr.get(num));
                for (int num2 = 0; num2 < majortmp.length(); num2++) {
                    JSONObject tmpobj = (JSONObject) majortmp.get(num2);
                    if (tmpobj.get("isu_nm").equals(finaltitle)||tmpobj.get("isu_nm").equals("전공필수")||tmpobj.get("isu_nm").equals("전공")) {
                        if(!tmpobj.get("credit").equals("0")){
                            divisionSubject.add(tmpobj);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    public void makePilsuStartSetting(String hakbeon,JSONObject mysubject){
        gradeParser=new GradeParser();
        try {
            this.majorInfo=gradeParser.eachMajor();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONObject hakbeonInfo = (JSONObject) majorInfo.get(hakbeon);
            JSONObject pilsu_cultureInfo = (JSONObject) hakbeonInfo.get("교양필수");
            JSONObject pilsu_majorInfo = (JSONObject) hakbeonInfo.get("전공필수");

            libartsgrouparr=new ArrayList<>();
            majorgrouparr=new ArrayList<>();
            Iterator culturelist=pilsu_cultureInfo.keys();

            while(culturelist.hasNext()){
                String subject_1=culturelist.next().toString();
                JSONArray subject_2=(JSONArray)pilsu_cultureInfo.get(subject_1);

                for(int i=0; i<subject_2.length();i++){
                    String subject=subject_2.get(i).toString();
                    ArrayList<String> childarr=new ArrayList<>();
                    childarr.add(subject);

                    //
                    ArrayList<String> keyArr = new ArrayList<>();
                    Iterator iterator = mysubject.keys();

                    while (iterator.hasNext()) {
                        keyArr.add(iterator.next().toString());
                    }
                    loop:
                    for (int num = 0; num < keyArr.size(); num++) {
                        JSONArray majortmp = (JSONArray) mysubject.get(keyArr.get(num));
                        for (int num2 = 0; num2 < majortmp.length(); num2++) {
                            JSONObject tmpobj = (JSONObject) majortmp.get(num2);
                            if (tmpobj.get("subject_nm").toString().equals(subject)) {
                                childarr.add("");
                                break loop;
                            }
                        }
                    }
                    //
                    libartsgrouparr.add(childarr);
                }
            }
            Iterator majorlist_1=pilsu_majorInfo.keys();
            while(majorlist_1.hasNext()){
                String subject_1=majorlist_1.next().toString();
                JSONObject pilsu_majorInfo2=(JSONObject) pilsu_majorInfo.get(subject_1);
                Iterator majorlist_2=pilsu_majorInfo2.keys();
                while(majorlist_2.hasNext()){
                    String subject_2=majorlist_2.next().toString();
                    JSONArray subject_3=(JSONArray)pilsu_majorInfo2.get(subject_2);
                    for(int i=0; i<subject_3.length();i++){
                        String subject=subject_3.get(i).toString();

                        ArrayList<String> childarr=new ArrayList<>();
                        childarr.add(subject);
                        //
                        ArrayList<String> keyArr = new ArrayList<>();
                        Iterator iterator = mysubject.keys();
                        while (iterator.hasNext()) {
                            keyArr.add(iterator.next().toString());
                        }
                        loop:
                        for (int num = 0; num < keyArr.size(); num++) {
                            JSONArray majortmp = (JSONArray) mysubject.get(keyArr.get(num));
                            for (int num2 = 0; num2 < majortmp.length(); num2++) {
                                JSONObject tmpobj = (JSONObject) majortmp.get(num2);
                                if (tmpobj.get("subject_nm").equals(subject)) {
                                    childarr.add("");
                                    break loop;
                                }
                            }
                        }
                        //
                        majorgrouparr.add(childarr);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<ArrayList<String>> get_majorgrouparr(){
        return majorgrouparr;
    }

    public ArrayList<ArrayList<String>> get_libartsgrouparr(){
        return libartsgrouparr;
    }


    public void makegrauate_major(JSONObject majorinfo){
        this.majorInfo=majorinfo;
        JSONObject list= null;
        try {
            list = (JSONObject)majorinfo.get("전공영역");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        make_resource(list);

    }


    public void makelicense(JSONObject majorinfo){
        this.majorInfo=majorinfo;
        JSONObject list= null;
        try {
            list = (JSONObject)majorinfo.get("인정자격증");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        make_resource(list);
    }

    public void makeforeign(JSONObject majorinfo){
        this.majorInfo=majorinfo;
        JSONObject list= null;
        try {
            list = (JSONObject)majorinfo.get("외국어영역");
            Iterator listI = list.keys();
            ArrayList<ArrayList<String>> grouparr = new ArrayList<>();
            while (listI.hasNext()) {
                ArrayList<String> childarr = new ArrayList<>();
                String list2title = listI.next().toString();
                JSONObject list2 = (JSONObject) list.get(list2title);
                childarr.add(list2title);
                Iterator list2I=list2.keys();
                while(list2I.hasNext()){
                    String imsi=list2I.next().toString();
                    String imsi2=list2.get(imsi).toString();
                    childarr.add(imsi+":"+imsi2);
                }
                grouparr.add(childarr);
            }
            graduategrouparr = new ArrayList<>();
            graduategrouparr = grouparr;
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    public ArrayList<ArrayList<String>> get_graduate_grouparr(){
        return graduategrouparr;
    }

    private void make_resource(JSONObject list) {
        try {
            Iterator listI = list.keys();
            ArrayList<ArrayList<String>> grouparr = new ArrayList<>();
            while (listI.hasNext()) {
                ArrayList<String> childarr = new ArrayList<>();
                String list2title = listI.next().toString();
                JSONArray list2 = (JSONArray) list.get(list2title);
                childarr.add(list2title);
                for (int i = 0; i < list2.length(); i++) {
                    String imsi = list2.get(i).toString();
                    childarr.add(imsi);
                }
                grouparr.add(childarr);
            }
            graduategrouparr = new ArrayList<>();
            graduategrouparr = grouparr;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int get_pilsumaxcount(){
        for(int i=0; i<majorgrouparr.size(); i++){
            pilsumaxcount++;
        }
        for(int i=0; i<libartsgrouparr.size(); i++){
            pilsumaxcount++;
        }
        return pilsumaxcount;
    }

    public int get_pilsuherecount(){
        for(int i=0; i<majorgrouparr.size(); i++){
            ArrayList<String> childarr=majorgrouparr.get(i);
                if(childarr.size()>1){

                    pilsuherecount++;

            }
        }
        for(int i=0; i<libartsgrouparr.size(); i++){
            ArrayList<String> childarr=libartsgrouparr.get(i);
                if(childarr.size()>1){

                    pilsuherecount++;
            }
        }
        return pilsuherecount;
    }

    public void libartsContainercreate(){
        try {
            //제목,현재학점,목표학점이 들어갈 배열 +최종 arr작업
            ArrayList<ArrayList<String>> components=new ArrayList<>();
            ArrayList<ArrayList<ArrayList<String>>> libartsarraylist=new ArrayList<>();
            JSONObject gradeInfo1=(JSONObject)gradeInfo.get("교양학점");
            JSONObject gradeInfo2=(JSONObject)gradeInfo1.get(hakbeon);
            JSONObject gradeInfo3=(JSONObject)gradeInfo2.get("구분");
            Iterator gradeInfo3I=gradeInfo3.keys();
            while(gradeInfo3I.hasNext()){
                String imsi=gradeInfo3I.next().toString();
                ArrayList<String> childarr=new ArrayList<>();
                childarr.add(imsi);//타이틀

                if(imsi.equals("JNU특성화교양")) { //JNU특성화 교양이 분류에있니? 이걸로 나중에 JNU를 일반교양으로 바꿀지 말지 판단할것임
                    isJNU = true;
                }
                childarr.add("0");//현재학점
                childarr.add(gradeInfo3.get(imsi).toString());//max학점
                components.add(childarr);
                //여기까지
                //여기는 최종arr 작업
                JSONArray imsi2=(JSONArray) gradeInfo2.get(imsi); //
                ArrayList<ArrayList<String>>grouparr=new ArrayList<>(); //언어와문학,역사와철학 이런게담길거임
                for(int z=0; z<imsi2.length(); z++){
                    String imsi3=imsi2.get(z).toString();
                    ArrayList<String> childarr2=new ArrayList<>();
                    childarr2.add(imsi3);//맨첫번째 칸에 언어와 문학 이렇게 하나가 들어갈거임
                    grouparr.add(childarr2);
                }
                libartsarraylist.add(grouparr);
            }
            ArrayList<String> imsichildarr1=new ArrayList<>();
            imsichildarr1.add("일반교양");//타이틀
            imsichildarr1.add("0");//현재학점
            imsichildarr1.add("0");//max학점
            components.add(imsichildarr1);
            ArrayList<String> imsichildarr2=new ArrayList<>();
            imsichildarr2.add("일반교양");
            ArrayList<ArrayList<String>> imsigrouparr=new ArrayList<>();
            imsigrouparr.add(imsichildarr2);
            libartsarraylist.add(imsigrouparr);

            //기초교양, 학문기초용 배열
            ArrayList<String> submaxcreditarray=new ArrayList();
            ArrayList<String> submaxtitlecreditarray=new ArrayList();
            JSONObject submaxcredit=(JSONObject)gradeInfo2.get("세부학점");
            Iterator submaxcreditI=submaxcredit.keys();
            while(submaxcreditI.hasNext()){
                String imsi=submaxcreditI.next().toString();
                submaxtitlecreditarray.add(imsi);
                submaxcreditarray.add(submaxcredit.get(imsi).toString());
                submaxcreditarray.add("0");
            }

            // 기초작업끝
            //db 미리가져오기
            JSONObject culturedb = getotherCultureDB(hakbeon);

            //
            //이제 과목분리 할차례

            for(int i=0; i<divisionSubject.size(); i++){
                JSONObject subjectobject=divisionSubject.get(i);

                String curri_year=subjectobject.get("curri_year").toString();
                String isu_nm=subjectobject.get("isu_nm").toString();
                String subject_nm=subjectobject.get("subject_nm").toString();
                if(isu_nm.equals("전공탐색")){
                    isu_nm="전공탐색교양";
                }
                else{
                }
                String credit=subjectobject.get("credit").toString();
                String detail=null;

                //detail구하기(세부분류)
                if(isu_nm.equals("JNU특성화교양")&&!isJNU){//JNU특성화 교양일때 내 구분에 JNU가 없다?
                    isu_nm="일반교양";
                    detail="일반교양";
                }
                else{ //둘중에 하나가 아니다?
                    JSONObject myculturedb=(JSONObject) culturedb.get(isu_nm);
                    Iterator myculturedbI=myculturedb.keys();
                    while(myculturedbI.hasNext()){
                        String imsi1= myculturedbI.next().toString();
                        if(subject_nm.equals(imsi1)){ //만약 서브젝트네임과 db의 서브젝트 네임이 같다?
                            detail=myculturedb.get(imsi1).toString();

                            break;
                        }
                    }
                    //검색햇는데 아직도 detail null이다?
                    if(detail==null) {
                        if (isu_nm.equals("JNU특성화교양")) { //그럼 JNU특성화교양도 교양이아니네? 돌아가
                            isu_nm = "일반교양";
                            detail = "일반교양";
                        } else {
                            JSONObject imsiotherculturedb = getotherCultureDB(curri_year);
                            JSONObject otherculturedb = (JSONObject) imsiotherculturedb.get(isu_nm);
                            Iterator otherculturedbI = otherculturedb.keys();
                            while (otherculturedbI.hasNext()) {
                                String imsi1 = otherculturedbI.next().toString();
                                detail = otherculturedb.get(imsi1).toString();

                                break;
                            }
                        }
                    }
                }
                //여기까지 detail 구하기엿습니다
                //이제 과목분류할 차례
                if(isu_nm.equals("기초교양")||isu_nm.equals("학문기초")) {
                    loop:
                    for (int k = 0; k < components.size(); k++) {
                        ArrayList<String> imsi1 = components.get(k); //타이틀, 현재학점, 목표학점
                        if (imsi1.get(0).equals(isu_nm)) {// 만약 타이틀과 isu_nm이 같다면
                            ArrayList<ArrayList<String>> imsi2 = libartsarraylist.get(k); //libartslsit에서 꺼내라
                            for(int l=0; l<imsi2.size(); l++){
                                ArrayList<String> imsi3= imsi2.get(l);
                                String imsi4= imsi3.get(0);
                                if(imsi4.equals(detail)){ // 만약 첫번째꺼 꺼냇는데 세부항목이랑 같다면?
                                    imsi3.add(subject_nm); //항목을 넣고 학점계산을 실시한다
                                    ///////////학점계산실시
                                    for(int z=0; z<submaxcreditarray.size()/2; z++){
                                        String innerdetail=submaxtitlecreditarray.get(z).toString();
                                        if(innerdetail.equals(detail)){
                                            int herecredit=Integer.parseInt(imsi1.get(1));
                                            int uppercredit=Integer.parseInt(credit);
                                            int limitcredit=Integer.parseInt(submaxcreditarray.get(z*2).toString());
                                            int herelimitcredit=Integer.parseInt(submaxcreditarray.get(z*2+1).toString());
                                            if((herelimitcredit+uppercredit)<=limitcredit){
                                                herelimitcredit+=uppercredit;
                                                herecredit+=uppercredit;
                                                submaxcreditarray.set(z*2+1,String.valueOf(herelimitcredit));
                                                imsi1.set(1,String.valueOf(herecredit));
                                                components.set(k,imsi1);
                                                System.out.println("조건1");
                                                System.out.println("현재과목"+subject_nm);
                                                System.out.println(herelimitcredit);
                                                System.out.println(herecredit);
                                                System.out.println(submaxcreditarray);
                                                break loop;
                                            }
                                            else{
                                                int differ=limitcredit-herelimitcredit;
                                                herelimitcredit+=differ;
                                                herecredit+=differ;
                                                submaxcreditarray.set(z*2+1,String.valueOf(herelimitcredit));
                                                imsi1.set(1,String.valueOf(herecredit));
                                                components.set(k,imsi1);
                                                System.out.println("조건2");
                                                System.out.println("현재과목"+subject_nm);
                                                System.out.println(herelimitcredit);
                                                System.out.println(herecredit);
                                                System.out.println(submaxcreditarray);
                                                break loop;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                else{
                    loop:
                    for (int k = 0; k < components.size(); k++) {
                        ArrayList<String> imsi1 = components.get(k); //타이틀, 현재학점, 목표학점
                        if (imsi1.get(0).equals(isu_nm)) {// 만약 타이틀과 isu_nm이 같다면
                            ArrayList<ArrayList<String>> imsi2 = libartsarraylist.get(k); //libartslsit에서 꺼내라
                            for(int l=0; l<imsi2.size(); l++){
                                ArrayList<String> imsi3= imsi2.get(l);
                                String imsi4= imsi3.get(0);
                                if(imsi4.equals(detail)){ // 만약 첫번째꺼 꺼냇는데 세부항목이랑 같다면?
                                    imsi3.add(subject_nm); //항목을 넣고 학점계산을 실시한다
                                    String imsi5=imsi1.get(1).toString();
                                    int imsi6= Integer.parseInt(imsi5);
                                    int uppercredit=Integer.parseInt(credit);
                                    imsi6+=uppercredit;
                                    imsi1.set(1,String.valueOf(imsi6));
                                    components.set(k,imsi1);
                                    break loop;
                                }
                            }
                        }
                    }

                }
            }
            ///마지막 일반교양이 0이면 일반교양을 삭제
            loop:
            for(int i=0 ; i<components.size();i++){
                ArrayList<String> imsi1=components.get(i);
                if(imsi1.get(0).equals("일반교양")){
                    ArrayList<ArrayList<String>> imsi2=libartsarraylist.get(i);
                    for(int k=0; k<imsi2.size(); k++){
                        ArrayList<String> imsi3= imsi2.get(k);
                        if(imsi3.get(0).equals("일반교양")&&imsi3.size()<2){
                            libartsarraylist.remove(i);
                            components.remove(i);
                        }
                    }
                }

            }


            this.libartsArraylist=libartsarraylist;
            this.libartsArraylistMenu=components;



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ArrayList<ArrayList<String>>> getlibartsArraylist(){
        return libartsArraylist;
    }
    public ArrayList<ArrayList<String>> getlibartsArraylistmenu(){
        return libartsArraylistMenu;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void cultureContainerCreate(){
        try {
            // 전공필수 과목 참고코드
            // 해당 키값에 맞는 곳에 배열에 들어있는 과목 출력해주시면 됩니다!
            JSONObject hakbeonInfo = (JSONObject) majorInfo.get(hakbeon);

            // 교양세부과목
            JSONObject gradePoint = (JSONObject) gradeInfo.get("교양학점");
            JSONObject cultureGradePoint = (JSONObject) gradePoint.get(hakbeon);
            JSONObject division = (JSONObject) cultureGradePoint.get("구분");
            maxCredit=division.get(title).toString();//타이틀을 기반으로 이컨테이너의 최고학점을 구하기

            // progress bar하실때 세부학점 받아오는 부분 참고 코드입니다.
            subMaxCredit = (JSONObject) cultureGradePoint.get("세부학점");


            //여기부터는 첫번째 컨테이너만 적용되는 리소스를 만들차례.
            ArrayList<String> needTitleArr=new ArrayList<String>();//글쓰기,외국어,논리적사고력
            ArrayList<String> needCreditSubjectArr=new ArrayList<String>();//2,0,4,0,2,0
            Iterator crediti=subMaxCredit.keys();
            try {
                while(crediti.hasNext()) {
                    String imsi = crediti.next().toString();
                    needTitleArr.add(imsi);
                    needCreditSubjectArr.add(subMaxCredit.get(imsi).toString());
                    needCreditSubjectArr.add("0");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ////////////////////////////////////////////////////////////////////
            JSONArray _title = (JSONArray)cultureGradePoint.get(title);//_title은 그 타이틀이 가지고있어야하는 요소를 가진 jsonobject

            ArrayList<ArrayList<String>> grouparr=new ArrayList<ArrayList<String>>();

            //제목만 넣기
            for(int m=0; m<_title.length(); m++){
                String first=_title.get(m).toString();
                ArrayList<String> childarr=new ArrayList<String>();
                childarr.add(first);
                grouparr.add(childarr);
            }

            //이제 검색
            for(int i=0; i<divisionSubject.size();i++){
                String intoarr=null; //들어갈 과목
                String intoarr2=null; //들어갈 과목의 세분류
                JSONObject imsi=divisionSubject.get(i);

                String curri_year=imsi.get("curri_year").toString();
                String subject_nm=imsi.get("subject_nm").toString();

                JSONObject db= null;
                try {
                    db = getCultureDB(title,hakbeon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator dbI=db.keys();
                //이 과목의 세부분류를 알아낼 차례
                while(dbI.hasNext()){
                    String imsi2=dbI.next().toString(); //imsi2는 db내의 일본어1:외국어 이렇게 쭉쭉 내려가는걸 저장하게 된다
                    if(subject_nm.equals(imsi2)){
                        intoarr=subject_nm;
                        intoarr2=db.get(subject_nm).toString();
                        break;
                    }
                }
                //만일 db에 없는 것일경우(년도가 달라서 분류가 달라졋으면 수강년도기준으로 다시찾기)
                if(intoarr==null){
                    JSONObject otherdb= null;
                    try {
                        otherdb = getotherCultureDB(curri_year);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Iterator otherdbI=otherdb.keys();
                    loop:
                    while(otherdbI.hasNext()){
                        String inotherdbkey=otherdbI.next().toString();
                        JSONObject inOtherdb=(JSONObject) otherdb.get(inotherdbkey);
                        Iterator inOtherdbI=inOtherdb.keys();
                        while(inOtherdbI.hasNext()){
                            String imsi2=inOtherdbI.next().toString(); //imsi2는 db내의 일본어1:외국어 이렇게 쭉쭉 내려가는걸 저장하게 된다
                            if(subject_nm.equals(imsi2)) {
                                ArrayList<String> childarr = new ArrayList<>();
                                childarr.add(inotherdbkey);
                                grouparr.add(childarr);
                                intoarr = subject_nm;
                                intoarr2 = inOtherdb.get(subject_nm).toString();
                                break loop;
                            }
                        }
                    }
                }
                for(int x=0; x<grouparr.size();x++){
                    ArrayList<String> childarr=grouparr.get(x);
                    if(childarr.get(0).equals(intoarr2)){
                        childarr.add(intoarr);
                        grouparr.set(x,childarr);
                        break;
                    }
                }
            }
            //학점계산-1 첫번째 플래그 필요한 교양학점
            this.grouparr=grouparr;
            if(needdivision) {
                for (int x = 0; x < grouparr.size(); x++) {
                    ArrayList<String> childarr = grouparr.get(x);//childarr설정
                    for (int k = 0; k < needTitleArr.size(); k++) { // 안의 갯수만큼 반복
                        if (childarr.get(0).equals(needTitleArr.get(k))) { //만약 childarr의 제목이 needtitlearr의 내용물과 같다면
                            for (int u = 1; u < childarr.size(); u++) {          //childarr의 내용물을 전부꺼내서 학점계산/한계치계산
                                for (int i = 0; i < divisionSubject.size(); i++) { //내가 수강한 서브젝트들을 모두 꺼내서 childarr안에잇는 서브젝트랑비교
                                    JSONObject subject = divisionSubject.get(i);
                                    if (subject.get("subject_nm").equals(childarr.get(u))) {

                                        String calcredit = subject.get("credit").toString();
                                        String calmaxcredit = needCreditSubjectArr.get(2 * k);
                                        int _calcredit = Integer.parseInt(calcredit);
                                        int _needcredit = Integer.parseInt(needCreditSubjectArr.get(2 * k + 1));
                                        int _calmaxcredit = Integer.parseInt(calmaxcredit);
                                        if ((_calmaxcredit - _needcredit) >= _calcredit) {
                                            _needcredit += _calcredit;
                                            needCreditSubjectArr.set(2 * k + 1, String.valueOf(_needcredit));
                                            hereCredit += _calcredit;

                                            break;
                                        }
                                        if ((_calmaxcredit - _needcredit) < _calcredit) {
                                            int differ = _calmaxcredit - _needcredit;
                                            _needcredit += differ;
                                            needCreditSubjectArr.set(2 * k + 1, String.valueOf(_needcredit));
                                            hereCredit += differ;

                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //학점계산-2 나머지
            if(!needdivision) {
                for (int x = 0; x < grouparr.size(); x++) {
                    ArrayList<String> childarr = grouparr.get(x);//childarr설정
                    for(int i=1; i<childarr.size(); i++){
                        for(int k=0; k<divisionSubject.size();k++){
                            JSONObject imsi=divisionSubject.get(k);
                            if(imsi.get("subject_nm").equals(childarr.get(i))){
                                int addcredt=Integer.parseInt(imsi.get("credit").toString());
                                hereCredit+=addcredt;
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void majorContainerCreate(){
        try {
            // 전공필수 과목 참고코드
            JSONObject majorgradeInfo = gradeParser.eachMajor();
            JSONObject majorGradePoint = (JSONObject) majorgradeInfo.get(hakbeon);
            maxCredit=majorGradePoint.get("심화전공").toString();
            //제목넣기
            ArrayList<ArrayList<String>> grouparr=new ArrayList<ArrayList<String>>();
            ArrayList<String> childarr1=new ArrayList<String>();
            childarr1.add("전공필수");
            ArrayList<String> childarr2=new ArrayList<String>();
            childarr2.add("전공");
            //검색
            for(int i=0; i<divisionSubject.size();i++) {
                JSONObject imsi = divisionSubject.get(i);
                String curri_year = imsi.get("curri_year").toString();
                String subject_nm = imsi.get("subject_nm").toString();
                String isu_nm=imsi.get("isu_nm").toString();
                String credit=imsi.get("credit").toString();
                String term_gb=imsi.get("term_gb").toString();
                String _term_gb=null;
                switch (term_gb){
                    case "10":
                        _term_gb="1";
                        break;
                    case "20":
                        _term_gb="2";
                        break;
                    case "11":
                    case "21":
                        _term_gb="계절학기";
                        break;

                }

                if(isu_nm.equals(childarr1.get(0).toString())){
                    childarr1.add(curri_year+"-"+_term_gb+":"+subject_nm);
                    hereCredit+=Integer.parseInt(credit);
                }
                else if (isu_nm.equals(childarr2.get(0).toString())) {
                    childarr2.add(curri_year+"-"+_term_gb+":"+subject_nm);
                    hereCredit+=Integer.parseInt(credit);
                }
                else{
                    int imsicredit=Integer.parseInt(credit);
                    if ((injungcredits -imsicredit ) >=0) {
                        injungcredits -= imsicredit;
                        hereCredit+=imsicredit;
                    }
                    if ((injungcredits -imsicredit ) <0) {
                        int differ = imsicredit-injungcredits;
                        injungcredits=0;
                        imsicredit-=differ;
                        hereCredit+=imsicredit;
                    }
                }

            }
            childarr2.set(0,"전공필수");
            childarr2.set(0,"전공        ");
            grouparr.add(childarr1);
            grouparr.add(childarr2);

                //학점계산.
            this.grouparr=grouparr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void LinkedmajorContainerCreate(String linkedmajor){
        try {
            // 전공필수 과목 참고코드
            JSONObject majorgradeInfo = gradeParser.eachMajor();
            JSONObject majorGradePoint = (JSONObject) majorgradeInfo.get(hakbeon);
            JSONObject linkedInfo = gradeParser.getlinkedMajor();
            maxCredit=majorGradePoint.get("연계전공").toString();
            //제목넣기
            ArrayList<ArrayList<String>> grouparr=new ArrayList<ArrayList<String>>();
            ArrayList<String> childarr1=new ArrayList<String>();
            childarr1.add("복수전공(전공)");
            ArrayList<String> childarr2=new ArrayList<String>();
            childarr2.add("복수개설전공");
            ArrayList<String> childarr3=new ArrayList<String>();
            childarr3.add("복수개설전공인정학점");
            childarr3.add("최대인정학점:"+injungcredits);

            JSONObject linkedmajor1=(JSONObject)linkedInfo.get(linkedmajor);
            JSONObject linkedmajor2=(JSONObject)linkedmajor1.get("중복전공");

            //검색

            for(int i=0; i<divisionSubject.size();i++) {

                JSONObject imsi = divisionSubject.get(i);
                String curri_year = imsi.get("curri_year").toString();
                String subject_nm = imsi.get("subject_nm").toString();
                String isu_nm=imsi.get("isu_nm").toString();
                String credit=imsi.get("credit").toString();
                String term_gb=imsi.get("term_gb").toString();
                String _term_gb=null;
                switch (term_gb){
                    case "10":
                        _term_gb="1";
                        break;
                    case "20":
                        _term_gb="2";
                        break;
                    case "11":
                    case "21":
                        _term_gb="계절학기";
                        break;
                }

                if(isu_nm.equals(childarr1.get(0).toString())){
                    childarr1.add(curri_year+"-"+_term_gb+":"+subject_nm);
                    hereCredit+=Integer.parseInt(credit);
                }
                else{
                    Iterator linkedmajor2I=linkedmajor2.keys();
                    while(linkedmajor2I.hasNext()){
                        String imsi1=linkedmajor2I.next().toString();

                        if(imsi1.equals(subject_nm)){

                            childarr2.add(curri_year+"-"+_term_gb+":"+subject_nm);
                            int imsicredit=Integer.parseInt(credit);
                            if ((injungcredits -imsicredit ) >=0) {
                                injungcredits -= imsicredit;
                                hereCredit+=imsicredit;
                            }
                            if ((injungcredits -imsicredit ) <0) {
                                int differ = imsicredit-injungcredits;
                                injungcredits=0;
                                imsicredit-=differ;
                                hereCredit+=imsicredit;
                            }
                        }
                    }
                }
            }
            childarr3.add("남은인정학점:"+injungcredits);
            childarr1.set(0,"연계전공        ");
            childarr2.set(0,"복수개설전공");
            grouparr.add(childarr1);
            grouparr.add(childarr2);
            grouparr.add(childarr3);
            //학점계산.
            this.grouparr=grouparr;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void wholeContainerCreate(){
        try {
            // 전공필수 과목 참고코드
            JSONObject majorgradeInfo = gradeParser.eachMajor();
            JSONObject majorGradePoint = (JSONObject) majorgradeInfo.get(hakbeon);
            maxCredit=majorGradePoint.get("심화전공").toString();
            //제목넣기
            ArrayList<ArrayList<String>> grouparr=new ArrayList<ArrayList<String>>();
            ArrayList<String> childarr1=new ArrayList<String>();
            childarr1.add(title);
            //검색
            for(int i=0; i<divisionSubject.size();i++) {
                JSONObject imsi = divisionSubject.get(i);
                String curri_year = imsi.get("curri_year").toString();
                String subject_nm = imsi.get("subject_nm").toString();
                String isu_nm=imsi.get("isu_nm").toString();
                String credit=imsi.get("credit").toString();
                String term_gb=imsi.get("term_gb").toString();
                String _term_gb=null;
                switch (term_gb){
                    case "10":
                        _term_gb="1";
                        break;
                    case "20":
                        _term_gb="2";
                        break;
                    case "11":
                    case "21":
                        _term_gb="계절학기";
                        break;

                }
                if(isu_nm.equals(childarr1.get(0).toString())){
                    childarr1.add(curri_year+"-"+_term_gb+":"+subject_nm);
                }
                hereCredit+=Integer.parseInt(credit);
            }
            grouparr.add(childarr1);


            //학점계산.
            this.grouparr=grouparr;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject getCultureDB(String classification, String _hakbeon) throws JSONException, IOException {
        JSONObject subjectInfo = gradeParser.getCulture(Integer.parseInt(_hakbeon));
        JSONObject cultureSub = (JSONObject) subjectInfo.get("교양");
        JSONObject mainDivision = (JSONObject) cultureSub.get(classification);
        return mainDivision;
    }

    public JSONObject getotherCultureDB(String _hakbeon) throws JSONException, IOException {
        JSONObject subjectInfo = gradeParser.getCulture(Integer.parseInt(_hakbeon));
        JSONObject cultureSub = (JSONObject) subjectInfo.get("교양");
        return cultureSub;
    }
}
