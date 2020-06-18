package com.example.jnu_graduate;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClassParser {
    JSONArray classjson;
    String jsonString;
    Context ctx;

    // 생성할 때 class.json파일 넣어줄 것
    // InputStream inputStream = openFileInput("class.json");
    public ClassParser(InputStream inputStream, Context ctx) throws JSONException {
        this.ctx = ctx;
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

        classjson = new JSONArray(jsonString);
    }

    public void createParsedClass() throws JSONException, IOException {
        JSONObject finalJson = new JSONObject();
        for(int i=0; i<classjson.length(); i++){
            JSONObject tmpJson = (JSONObject) classjson.get(i);
            JSONArray tmp = (JSONArray) tmpJson.get("GRID_DATA");
            JSONArray jsonArray = new JSONArray();
            for(int j=0; j<tmp.length(); j++){
                JSONObject subject = (JSONObject) tmp.get(j);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("credit", subject.get("credit"));
                jsonObject.put("curri_year", subject.get("year"));
                if(subject.get("term_gb").toString().equals("1학기")){
                    jsonObject.put("term_gb", "10");
                }
                else if(subject.get("term_gb").toString().equals("2학기")){
                    jsonObject.put("term_gb", "20");
                }
                else if(subject.get("term_gb").toString().equals("하기계절")){
                    jsonObject.put("term_gb", "11");
                }
                else if(subject.get("term_gb").toString().equals("동기계절")){
                    jsonObject.put("term_gb", "21");
                }
                //jsonObject.put("term_gb", subject.get("term_gb"));
                jsonObject.put("isu_nm", subject.get("isu_nm"));
                jsonObject.put("subject_nm", subject.get("subject_nm"));
                /*
                  "credit": "2",
                  "curri_year": "2017",
                  "term_gb": "10",
                  "isu_nm": "전인교양",
                  "subject_nm": "생활속프로그래밍",
                */
                jsonArray.put(jsonObject);
                finalJson.put(String.valueOf(i), jsonArray);
            }

        }

        FileOutputStream parsedClassFile = this.ctx.openFileOutput("parsedClass.json", Context.MODE_PRIVATE);
        parsedClassFile.write(finalJson.toString().getBytes());
        parsedClassFile.close();
    }
}