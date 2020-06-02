package com.example.jnu_graduate;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Containerhelper {
    GradeParser gradeParser;
    //기본
    private ConstraintLayout constraintLayout;
    private Context context;
    private int prevcontainerid;
    //받아와야할거
    private addcontainer container;
    private String title;
    private JSONObject majorInfo;
    private JSONObject gradeInfo;
    //--divsion subject를 받아오자마자 저장안하고 그냥 바로 가공시작
    private String hakbeon; //10자리인지 4자리인지 확인해야함
    private JSONObject subMaxCredit;
    private JSONObject db;
    //안에서 생성될거
    private ArrayList<JSONObject> divisionSubject;
    private int hereCredit=0;
    private String maxCredit;
    private ArrayList<ArrayList<String>> grouparr;
    private boolean needdivision =false;
    public void setBasicSetting(ConstraintLayout constraintLayout, Context context, int prevcontainerid){
        this.constraintLayout=constraintLayout;
        this.context=context;
        this.prevcontainerid=prevcontainerid;
    }
    public int get_herecredit(){
        return hereCredit;
    }
    public String get_maxcredit(){
        return maxCredit;
    }

    public ArrayList<ArrayList<String>> getGrouparr(){
        return grouparr;
    }
    public void setStartSetting(String title, String hakbeon, JSONObject mysubject, JSONObject majorinfo, JSONObject gradeinfo, addcontainer container){
        this.title = title;
        this.hakbeon=hakbeon;
        this.container=container;
        gradeParser=new GradeParser();
        this.majorInfo=majorinfo;
        this.gradeInfo=gradeinfo;

        if(title.equals("학문기초")||title.equals("기초교양")){
            needdivision=true;
        }

//        System.out.println("학번은 어케생겻니"+hakbeon);
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

    public void setMajorStartSetting(String title, String hakbeon, JSONObject mysubject, JSONObject majorinfo, JSONObject gradeinfo, addcontainer container){
        this.title = title;
        this.hakbeon=hakbeon;
        this.container=container;
        gradeParser=new GradeParser();
        this.majorInfo=majorinfo;
        this.gradeInfo=gradeinfo;

        if(title.equals("학문기초")||title.equals("기초교양")){
            needdivision=true;
        }

//        System.out.println("학번은 어케생겻니"+hakbeon);
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
                JSONArray majortmp = (JSONArray) mysubject.get(keyArr.get(num));
                for (int num2 = 0; num2 < majortmp.length(); num2++) {
                    JSONObject tmpobj = (JSONObject) majortmp.get(num2);
                    if (tmpobj.get("isu_nm").equals(finalTitle)||tmpobj.get("isu_nm").equals("전공필수")) {
                        divisionSubject.add(tmpobj);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void test(){
        for(int i=0; i<divisionSubject.size(); i++){
            JSONObject imsi=divisionSubject.get(i);
//            System.out.println(imsi+"캇트");
        }
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
//                    System.out.println("지금 intoarr가 null이니?");
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
                        _term_gb="계절학기";
                        break;
                    case "21":
                        _term_gb="계절학기";
                        break;
                }
                System.out.println(isu_nm+"이건isn_nm");
                if(isu_nm.equals(childarr1.get(0).toString())){
                    childarr1.add(curri_year+"-"+_term_gb+":"+subject_nm);
                    System.out.println(childarr1.get(0).toString()+"이건childarr1");
                }
                else{
                    childarr2.add(curri_year+"-"+_term_gb+":"+subject_nm);
                    System.out.println(childarr2.get(0).toString()+"이건childarr2");
                }
                hereCredit+=Integer.parseInt(credit);
            }
            childarr2.set(0,"전공        ");
            grouparr.add(childarr1);
            grouparr.add(childarr2);

                //학점계산.
            this.grouparr=grouparr;
//            for (int x = 0; x < grouparr.size(); x++) {
//                ArrayList<String> childarr = grouparr.get(x);//childarr설정
//                for(int i=1; i<childarr.size(); i++){
//                    for(int k=0; k<divisionSubject.size();k++){
//                        JSONObject imsi=divisionSubject.get(k);
//                        if(imsi.get("subject_nm").equals(childarr.get(i))){
//                            int addcredt=Integer.parseInt(imsi.get("credit").toString());
//                            hereCredit+=addcredt;
//                        }
//                    }
//                }
//            }
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
