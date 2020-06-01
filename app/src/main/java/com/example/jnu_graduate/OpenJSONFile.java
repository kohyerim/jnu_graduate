package com.example.jnu_graduate;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class OpenJSONFile {
    Context context;
    JSONObject classjson;

    public  OpenJSONFile(InputStream inputStream, Context context) throws JSONException {
        this.context = context;
        String jsonString = null;
        try {
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                jsonString = stringBuilder.toString();

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        classjson = new JSONObject(jsonString);

    }

    public JSONObject getJSONObject(){
        return classjson;
    }

    public int getCultureGP() throws JSONException {
        int GP = 0;
        ArrayList<String> keyArr = new ArrayList<>();
        Iterator iterator = classjson.keys();
        while (iterator.hasNext()){
            keyArr.add(iterator.next().toString());
        }
        for(int i=0; i<keyArr.size(); i++){
            JSONArray tmp = (JSONArray) classjson.get(keyArr.get(i));
            for(int j=0; j<tmp.length(); j++){
                JSONObject tmpobj = (JSONObject) tmp.get(j);
                if(!tmpobj.get("isu_nm").equals("전공") && !tmpobj.get("isu_nm").equals("전공필수") && !tmpobj.get("isu_nm").equals("일반선택")){
                    GP += Integer.parseInt((String) tmpobj.get("credit"));
                }
            }
        }

        return GP;
    }
    public int getMajorGP() throws JSONException {
        int GP = 0;
        ArrayList<String> keyArr = new ArrayList<>();
        Iterator iterator = classjson.keys();
        while (iterator.hasNext()){
            keyArr.add(iterator.next().toString());
        }
        for(int i=0; i<keyArr.size(); i++){
            JSONArray tmp = (JSONArray) classjson.get(keyArr.get(i));
            for(int j=0; j<tmp.length(); j++){
                JSONObject tmpobj = (JSONObject) tmp.get(j);
                if(tmpobj.get("isu_nm").equals("전공") || tmpobj.get("isu_nm").equals("전공필수")){
                    GP += Integer.parseInt((String) tmpobj.get("credit"));
                }
            }
        }

        return GP;
    }
    public int getTotalGP() throws JSONException {
        int GP = 0;
        ArrayList<String> keyArr = new ArrayList<>();
        Iterator iterator = classjson.keys();
        while (iterator.hasNext()){
            keyArr.add(iterator.next().toString());
        }
        for(int i=0; i<keyArr.size(); i++){
            JSONArray tmp = (JSONArray) classjson.get(keyArr.get(i));
            for(int j=0; j<tmp.length(); j++){
                JSONObject tmpobj = (JSONObject) tmp.get(j);
                GP += Integer.parseInt((String) tmpobj.get("credit"));
            }
        }

        return GP;
    }
}
