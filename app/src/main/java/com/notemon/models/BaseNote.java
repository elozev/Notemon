package com.notemon.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by emil on 4/26/17.
 */

public class BaseNote implements Serializable{

    private Long id;
    private String title;
    private Date reminder;
    private Date createdAt;
    private String content;
    private int type;

    public BaseNote(String title, int type, String content) {
        this.title = title;
        this.type = type;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
