package com.notemon.models;

/**
 * Created by emil on 4/26/17.
 */

public class MediaNote extends TextNote {

    private String mediaUrl;

    public MediaNote(String title, int type, String content, String mediaUrl) {
        super(title, type, content);
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
