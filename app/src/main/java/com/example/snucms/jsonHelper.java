package com.example.snucms;

import android.content.Context;

import com.example.snucms.timetable.CalendarUtils;
import com.example.snucms.timetable.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class jsonHelper {

    private static File jsonFile;

    public jsonHelper(Context context) {
        jsonFile = new File(context.getFilesDir(), "timetable.json");
    }

    public void readJson() throws IOException, JSONException {
        FileReader fileReader = new FileReader(jsonFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();

        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        CalendarUtils.eventsList.clear();
        //System.out.println(jsonArray.toString());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObj = (jsonArray.get(i).getClass() == JSONObject.class)?
                    ((JSONObject) jsonArray.get(i))
                    :new JSONObject();
            Event temp = new Event(
                    jsonObj.getLong("id"),
                    jsonObj.getString("name"),
                    LocalDate.parse(jsonObj.getString("date")),
                    LocalTime.parse(jsonObj.getString("time")),
                    Boolean.parseBoolean(jsonObj.getString("repeat"))
            );
            CalendarUtils.eventsList.add(temp);
        }
    }

    public void writeJson() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<CalendarUtils.eventsList.size();i++) {
            JSONObject temp = new JSONObject();
            temp.put("id", CalendarUtils.eventsList.get(i).getId());
            temp.put("name", CalendarUtils.eventsList.get(i).getName());
            temp.put("date", CalendarUtils.eventsList.get(i).getDate());
            temp.put("time", CalendarUtils.eventsList.get(i).getTime());
            temp.put("repeat", CalendarUtils.eventsList.get(i).isRepeat());
            jsonArray.put(temp);
        }
        jsonObject.put("events", jsonArray);
        String userString = jsonObject.toString();
        //System.out.println(jsonObject.toString());

        FileWriter fileWriter = new FileWriter(jsonFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.flush();
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }

    public void clearJson() {
        jsonFile.delete();
    }

}