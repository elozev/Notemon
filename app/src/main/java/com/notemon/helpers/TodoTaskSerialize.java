package com.notemon.helpers;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.notemon.models.TodoTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by emil on 01.05.17.
 */

public class TodoTaskSerialize {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String serializeTasks(List<TodoTask> taskList) {
        String array = "";
        try {
            array = mapper.writeValueAsString(taskList);
            Log.d(TAG, "------------------------JSON: \n" + array);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static List<TodoTask> deserializeTasks(String array) {
        TypeReference<List<TodoTask>> mapType = new TypeReference<List<TodoTask>>() {
        };
        List<TodoTask> jsonToPersonList = new ArrayList<>();
        try {
            jsonToPersonList = mapper.readValue(array, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonToPersonList;
    }
}
