package com.example.jnu_graduate;

import android.os.Build;

import androidx.annotation.RequiresApi;

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


    public int get_herecredit(){
        return hereCredit;
    }
    public String get_maxcredit(){
        return maxCredit;
    }

    public ArrayList<ArrayList<String>> getGrouparr(){
        return grouparr;
    }
    public void setStartSetting(String title, String hakbeon, JSONObject mysubject, JSONObject majorinfo, JSONObject gradeinfo){
        this.title = title;
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        this.majorInfo=majorinfo;
        this.gradeInfo=gradeinfo;

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

    public void setMajorStartSetting(String title, String hakbeon, JSONObject mysubject, JSONObject majorinfo, JSONObject gradeinfo){
        this.title = title;
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        this.majorInfo=majorinfo;
        this.gradeInfo=gradeinfo;

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

    public void setlinkedmajorStartSetting(String title, String hakbeon, JSONObject mysubject, JSONObject majorinfo, JSONObject gradeinfo){
        this.title = title;
        this.hakbeon=hakbeon;
        gradeParser=new GradeParser();
        this.majorInfo=majorinfo;
        this.gradeInfo=gradeinfo;
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
                            System.out.println("이 연계전공이 잘들어가니"+tmpobj);
                            divisionSubject.add(tmpobj);
                        }
                    }
                }
            }
            System.out.println("완성된배열"+divisionSubject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    public void makePilsuStartSetting(String hakbeon,JSONObject mysubject, JSONObject majorinfo){
        this.majorInfo=majorinfo;

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

                }
                else{
                    childarr2.add(curri_year+"-"+_term_gb+":"+subject_nm);
                }
                hereCredit+=Integer.parseInt(credit);

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
            System.out.println("이 학과에는 무슨과목이있을까요?"+linkedmajor2);
            //검색
            System.out.println(divisionSubject+"잘받아왓니?");
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
                    System.out.println("연계전공 지금학점:"+hereCredit);
                    System.out.println("나는야 연계전공 핫둘핫둘"+curri_year+"-"+_term_gb+":"+subject_nm);
                }
                else{
                    Iterator linkedmajor2I=linkedmajor2.keys();
                    while(linkedmajor2I.hasNext()){
                        String imsi1=linkedmajor2I.next().toString();

                        if(imsi1.equals(subject_nm)){
                            System.out.println("맞았어!");
                            childarr2.add(curri_year+"-"+_term_gb+":"+subject_nm);
                            int imsicredit=Integer.parseInt(credit);
                            if ((injungcredits -imsicredit ) >=0) {
                                System.out.println("현재인정학점"+injungcredits);
                                System.out.println("과목학점:"+imsicredit);
                                System.out.println("첫번째조건 결과"+(injungcredits-imsicredit));

                                injungcredits -= imsicredit;
                                hereCredit+=imsicredit;
                                System.out.println("첫번째 조건 인정학점:"+injungcredits);
                                System.out.println("첫번째 조건 지금학점:"+hereCredit);
                            }
                            if ((injungcredits -imsicredit ) <0) {
                                System.out.println("현재인정학점"+injungcredits);
                                System.out.println("과목학점:"+imsicredit);
                                System.out.println("두번째조건 결과"+(injungcredits-imsicredit));
                                int differ = imsicredit-injungcredits;
                                injungcredits=0;
                                imsicredit-=differ;
                                hereCredit+=imsicredit;
                                System.out.println("두번째 조건 인정학점:"+injungcredits);
                                System.out.println("두번째 조건 지금학점:"+hereCredit);
                            }
                        }
                    }
                }
            }
            childarr3.add("남은인정학점:"+injungcredits);
            childarr1.set(0,"연계전공        ");
            childarr2.set(0,"복수개설전공");
            System.out.println(childarr1);
            System.out.println(childarr2);
            grouparr.add(childarr1);
            grouparr.add(childarr2);
            grouparr.add(childarr3);
            //학점계산.
            this.grouparr=grouparr;
            System.out.println(grouparr+"완성됫니?");

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
