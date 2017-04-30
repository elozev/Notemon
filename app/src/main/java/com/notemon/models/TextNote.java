package com.notemon.models;

/**
 * Created by emil on 4/26/17.
 */

public class TextNote extends BaseNote {

    private String textNoteContent;

    public TextNote(String title, int type, String content) {
        super(title, type, content);
        this.textNoteContent = content;
    }


    public String getTextNoteContent() {
        return textNoteContent;
    }

    public void setTextNoteContent(String textNoteContent) {
        this.textNoteContent = textNoteContent;
    }

    @Override
    public String toString() {
        return textNoteContent;
    }
}
