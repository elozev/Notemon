package com.notemon.models;

/**
 * Created by emil on 23.04.17.
 */

public class TodoTask {
    private String content;
    private Status status;
    private int order;

    public TodoTask(String content, Status status, int order) {
        this.content = content;
        this.status = status;
        this.order = order;
    }

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
