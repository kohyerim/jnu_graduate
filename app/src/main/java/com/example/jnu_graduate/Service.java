package com.example.jnu_graduate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Service extends AsyncTask<Object, Void, Boolean>{
    Dreamy dreamy;
    String cookie;
    JSONObject infoJson;
    JSONArray classJson = new JSONArray();
    Context ctx;
    Context MainActivity;
    Button loginBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Boolean doInBackground(Object... objects) {
        this.ctx = (Context) objects[0];
        this.MainActivity = (Context) objects[3];
        String id = objects[1].toString();
        String pw = objects[2].toString();
        this.loginBtn = (Button) objects[4];
        this.dreamy = new Dreamy(id, pw);
        try{
            // SSLHandShakeException Fixed
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            } };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // code start

            // 로그인 하고 Cookie, 학번, 전공 학기 정보 가져오기
            this.cookie = dreamy.login();
            this.infoJson = dreamy.getInfo(this.cookie);
            if(this.infoJson == null){
                return false;
            }
            JSONObject hakjuk = new JSONObject(this.infoJson.get("HJ_MST").toString());

            // 파일 저장하기 위한 JSONObject 생성
            JSONObject profileObj = new JSONObject();
            profileObj.put("student_num", hakjuk.get("student_no").toString()); // == strings[0]: 학번
            profileObj.put("student_name", hakjuk.get("nm").toString());
            if(hakjuk.get("maj_nm").toString().length() < 1){ // 전공 추가 (학부 x)
                profileObj.put("major", hakjuk.get("cls_nm").toString());
            }
            else{  // 전공 추가 (학부 - 학과)
                profileObj.put("major", hakjuk.get("maj_nm").toString());
            }
            profileObj.put("grade", hakjuk.get("grade_nm").toString()); // 학년
            profileObj.put("term_grade", hakjuk.get("term_grade").toString());  // 몇학기 인지 (ex. 7)
            if(hakjuk.get("dbl_dept_nm").toString().length() > 2){ // 복수전공 혹은 연계전공 있다면 추가
                profileObj.put("dbl_dept_nm", hakjuk.get("dbl_dept_nm"));
            }

            int s_num = Integer.parseInt(hakjuk.get("student_no").toString().substring(0,4));
            int curr_year = Calendar.getInstance().get(Calendar.YEAR);
            int[] term_list = new int[]{10, 11, 20, 21};

            // 수강한 수업 가져오기 : [cookie, 연도, 학기] param
            for (int i=s_num; i<=curr_year; i++){
                for(int j=0; j<term_list.length; j++){
                    JSONObject tmp = dreamy.getScore(this.cookie, i, term_list[j]);
                    if(tmp.toString().length() > 30){
                        classJson.put(tmp);
                    }
                }
            }

            try {
                // 파일 확인 : Shift 2번 -> Device File Explorer -> data/data/com.example.jnu_graduate/files

                //origin.json
                FileOutputStream originStream = ctx.openFileOutput("origin.json", Context.MODE_PRIVATE);
                originStream.write(hakjuk.toString().getBytes());
                originStream.close();

                // profile.json
                FileOutputStream profileStream = ctx.openFileOutput("profile.json", Context.MODE_PRIVATE);
                profileStream.write(profileObj.toString().getBytes());
                profileStream.close();

                // class.json
                FileOutputStream classStream = ctx.openFileOutput("class.json", Context.MODE_PRIVATE);
                classStream.write(classJson.toString().getBytes());
                classStream.close();
//
//                // score.json
//                FileOutputStream scoreStream = ctx.openFileOutput("score.json", Context.MODE_PRIVATE);
//                scoreStream.write(test.toString().getBytes());
//                scoreStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch (NoSuchAlgorithmException | IOException | KeyManagementException | JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean loginResult) {
        // login Success
        if(loginResult){
            ProgressDialog progressDialog = new ProgressDialog(this.MainActivity);
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("초기 설정 중");
            progressDialog.show();
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent lobbyintent=new Intent(ctx, lobby.class);
                    ctx.startActivity(lobbyintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }, 1000);

        }
        // login Fail
        else{
            loginBtn.setEnabled(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.MainActivity);
            builder.setMessage("Login Fail");
            builder.setPositiveButton("확인", null);
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
