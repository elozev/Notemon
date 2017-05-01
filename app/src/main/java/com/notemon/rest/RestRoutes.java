package com.notemon.rest;

import com.notemon.models.BaseNote;
import com.notemon.models.Project;
import com.notemon.models.Token;
import com.notemon.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by emil on 30.04.17.
 */

public interface RestRoutes {

    String users = "users/";
    String project = "projects/";
    //URLS FOR USERS

    @POST("register")
    Call<String> createUser(@Body User user);


    @POST("login")
    Call<Token> loginUser(@Body User user);

    // URLS FOR PROJECTS
    @POST("projects/create")
    Call<String> createProject(@Body Project project,  @Header("Authorization") String token);

    @POST("projects/{id}/note")
    Call<String> createNoteToProject(@Body BaseNote note, @Header("Authorization") String token, @Path("id") Long projectId);

    @GET("projects/")
    Call<List<Project>> getProjects(@Header("Authorization") String token);

    @GET("projects/{id}/notes")
    Call<List<BaseNote>> getNotesFromProject(@Path("id") Long projectId);
    //URLS FOR NOTES
}
