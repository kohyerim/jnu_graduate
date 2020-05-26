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
        // 1-1학기만 테스트
        JSONObject tmpJson = (JSONObject) classjson.get(0);
        JSONArray tmp = (JSONArray) tmpJson.get("DTL_LIST");
        JSONObject subject = (JSONObject) tmp.get(0); // 1학년 1학기 1번째 과목인 기초공학설계에 접근
        subject.get("subject_nm"); // 기공설 과목명에 접근
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("subject_nm", subject.get("subject_nm"));
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JSONObject finalJson = new JSONObject();
        finalJson.put(String.valueOf(0), jsonArray);

        FileOutputStream parsedClassFile = this.ctx.openFileOutput("parsedClass.json", Context.MODE_PRIVATE);
        parsedClassFile.write(finalJson.toString().getBytes());
        parsedClassFile.close();


    }
}