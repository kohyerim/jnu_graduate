package com.example.jnu_graduate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GradeParser {
    public void getMajor() throws IOException {
        try {
            InputStream is = new URL("https://raw.githubusercontent.com/incuriositas/capstoneDatabase/master/major/test.json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();
            while((str = rd.readLine()) != null){
                buffer.append(str);
            }
            String receiveMsg = buffer.toString();
            System.out.println(receiveMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getCulture(int i) throws IOException {
        try {
            InputStream is = new URL("https://raw.githubusercontent.com/incuriositas/capstoneDatabase/master/culture/"+i+".json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();
            while((str = rd.readLine()) != null){
                buffer.append(str);
            }
            String receiveMsg = buffer.toString();
            System.out.println(receiveMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
