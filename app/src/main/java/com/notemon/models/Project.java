package com.notemon.models;

import java.io.Serializable;

/**
 * Created by emil on 4/28/17.
 */

public class Project implements Serializable {
    private Long id;
    private int color;
    private int colorDark;
    private String colorName;
    private String name;
    private String description;

    public Project(String colorName, int color, int colorDark, String name, String description) {
        this.colorName = colorName;
        this.color = color;
        this.colorDark = colorDark;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColorDark() {
        return colorDark;
    }

    public void setColorDark(int colorDark) {
        this.colorDark = colorDark;
    }
}
