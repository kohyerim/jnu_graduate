package com.example.jnu_graduate;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Service extends AsyncTask<String, Void, Void>{
    Dreamy dreamy;
    String cookie;
    JSONObject infoJson;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(String... strings) { // 로그인했을 때 가져올 정보들 get
        this.dreamy = new Dreamy(strings[0], strings[1]);
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
            this.cookie = dreamy.login();
            this.infoJson = dreamy.getInfo(this.cookie);
            JSONObject hakjuk = new JSONObject(this.infoJson.get("HJ_MST").toString());

            hakjuk.get("student_no"); // == strings[0]: 학번
            hakjuk.get("dept_full_nm").toString(); // 전공
            hakjuk.get("term_grade").toString(); // 몇학기 인지 (ex. 7)
            // json value 출력
//                Iterator keyList = jsonObject.keys();
//                while(keyList.hasNext()){
//                    String tmp = keyList.next().toString();
//                    System.out.println(tmp);
//                }

            // 수강한 수업 가져오기 : [cookie, 연도, 학기] param
            JSONObject classJson =  dreamy.getClass(this.cookie, 2020, 10);
            System.out.println(classJson);
        }
        catch (NoSuchAlgorithmException | IOException | KeyManagementException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
