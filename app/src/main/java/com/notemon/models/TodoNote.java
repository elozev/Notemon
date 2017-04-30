package com.notemon.models;

import java.util.List;

/**
 * Created by emil on 4/26/17.
 */

public class TodoNote extends BaseNote {

    private List<TodoTask> tasks;

    public TodoNote(String title, int type, List<TodoTask> tasks, String content) {
        super(title, type, content);
        this.tasks = tasks;
    }

    public List<TodoTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<TodoTask> tasks) {
        this.tasks = tasks;
    }

//    @Override
//    public String toString() {
//        String res = "";
//        for(TodoTask task: )
//    }
}
