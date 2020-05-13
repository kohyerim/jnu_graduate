package com.example.jnu_graduate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GradeParser {
    public JSONObject getMajor() throws IOException, JSONException {
        try {
            InputStream is = new URL("https://raw.githubusercontent.com/incuriositas/capstoneDatabase/master/gradePoint/requirements.json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();
            while((str = rd.readLine()) != null){
                buffer.append(str);
            }
            String receiveMsg = buffer.toString();
            JSONObject major =  new JSONObject(receiveMsg);
            return major;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getCulture(int i) throws IOException, JSONException {
        try {
            InputStream is = new URL("https://raw.githubusercontent.com/incuriositas/capstoneDatabase/master/culture/"+i+".json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();
            while((str = rd.readLine()) != null){
                buffer.append(str);
            }
            String receiveMsg = buffer.toString();
            JSONObject culture =  new JSONObject(receiveMsg);
            return culture;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
