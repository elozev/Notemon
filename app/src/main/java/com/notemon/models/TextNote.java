package com.notemon.models;

/**
 * Created by emil on 4/26/17.
 */

public class TextNote extends BaseNote {

    private String content;

    public TextNote(String title, int type, String content) {
        super(title, type, content);
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
