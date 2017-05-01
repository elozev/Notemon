package com.notemon.models;

import java.io.Serializable;

/**
 * Created by emil on 23.04.17.
 */

public class TodoTask implements Serializable{


    private String content;
    private Status status;
    private int order;

    public TodoTask(String content, int order, Status status) {
        this.content = content;
        this.status = status;
        this.order = order;
    }
    public TodoTask(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
