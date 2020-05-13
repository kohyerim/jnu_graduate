package com.example.jnu_graduate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Service extends AsyncTask<Object, Void, Void>{
    Dreamy dreamy;
    String cookie;
    JSONObject infoJson;
    JSONObject classJson;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Object... objects) {
        Context ctx = (Context) objects[0];
        String id = objects[1].toString();
        String pw = objects[2].toString();
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
            JSONObject hakjuk = new JSONObject(this.infoJson.get("HJ_MST").toString());
            System.out.println(hakjuk.toString());

            // 파일 저장하기 위한 JSONObject 생성
            JSONObject profileObj = new JSONObject();
            profileObj.put("student_num", hakjuk.get("student_no").toString()); // == strings[0]: 학번
            profileObj.put("student_name", hakjuk.get("nm").toString());
            profileObj.put("major", hakjuk.get("maj_nm").toString());  // 전공
            profileObj.put("grade", hakjuk.get("grade_nm").toString()); // 학년
            profileObj.put("term_grade", hakjuk.get("term_grade").toString());  // 몇학기 인지 (ex. 7)

            // 수강한 수업 가져오기 : [cookie, 연도, 학기] param
            classJson = dreamy.getClass(this.cookie, 2020, 10);
            try {
                // 파일 확인 : Shift 2번 -> Device File Explorer -> data/data/com.example.jnu_graduate/files

                // profile.json
                FileOutputStream profileStream = ctx.openFileOutput("profile.json", Context.MODE_PRIVATE);
                profileStream.write(profileObj.toString().getBytes());
                profileStream.close();

                // class.json
                FileOutputStream classStream = ctx.openFileOutput("class.json", Context.MODE_PRIVATE);
                classStream.write(classJson.toString().getBytes());
                classStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch (NoSuchAlgorithmException | IOException | KeyManagementException | JSONException e) {
            e.printStackTrace();
        }

        return null;
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
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
    }
}
