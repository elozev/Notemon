package com.notemon.models;

import java.io.Serializable;

/**
 * Created by emil on 4/28/17.
 */

public class Project implements Serializable {
    private Long id;
    private String name;
    private int color;
    private int colorDark;

    public Project(int color, int colorDark, String name) {
        this.color = color;
        this.colorDark = colorDark;
        this.name = name;
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorDark() {
        return colorDark;
    }

    public void setColorDark(int colorDark) {
        this.colorDark = colorDark;
    }
}
