package com.notemon.helpers;

/**
 * Created by emil on 22.04.17.
 */

public interface Constants {
    String NOTE_TYPE = "note_type";
    String NOTE_TODO = "note_todo";
    String NOTE_VOICE = "note_voice";
    String NOTE_TEXT = "note_text";
    String NOTE_MEDIA = "note_media";
    String NOTE_MEDIA_URL = "note_url";
    String HOME_FRAGMENT = "home_fragment";
    String PROJECT_ID = "project_id";
    String PROJECT_ALL = "project_all";

    int NOTE_TYPE_TEXT = 0;
    int NOTE_TYPE_MEDIA= 1;
    int NOTE_TYPE_TODO = 2;

    String USER_DETAILS = "user_details_shared_prefs";
    String TOKEN = "user_token";
    String LOGIN_PROGRESS = "login_progress";
    String REGISTRATION_PROGRESS = "registration_progress";
    String TOKEN_PREFIX = "JWT ";
}
