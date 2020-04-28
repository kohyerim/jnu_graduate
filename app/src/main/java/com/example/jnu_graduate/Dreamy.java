package com.example.jnu_graduate;

import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Base64;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Dreamy {
    private Base64.Encoder encoder = Base64.getEncoder();
    private String id = null;
    private String pw = null;
    private String studentNum = null;
    private String uri = "https://dreamy.jejunu.ac.kr/";
    private URL url = null;

    public Dreamy(String id, String pw){
        this.studentNum = id;
        this.id = this.encoder.encodeToString(id.getBytes());
        this.pw = this.encoder.encodeToString(pw.getBytes());
    }


    public String login() throws IOException {
        String body = "frame/sysUser.do?next=";
        String param = "&tmpu=" + this.id + "&tmpw=" + this.pw + "&mobile=y&app=null&z=Y&userid=&password=";
        url = new URL(uri + body + param);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        String cookie = conn.getHeaderField("Set-Cookie");
        System.out.println(conn.getURL());

        return cookie;
    }

    public void getInfo(String cookie) throws IOException {
        String body = "hjju/hj/sta_hj_1010q.jejunu";
        String param = "&mode=doValue&student_no=" + this.studentNum + "&_=";
        url = new URL(uri + body);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Cookie", cookie);

        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(param);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(responseCode);
        System.out.println(response.toString());

    }

    public void getSooup() throws IOException {
        String body = "susj/su/sta_su_8331r.jejunu" ;
        url = new URL(this.uri + body);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//        while((line = br.readLine()) != null ){
//            System.out.println(line);
//        }
    }
}