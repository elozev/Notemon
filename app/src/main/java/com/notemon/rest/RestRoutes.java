package com.notemon.rest;

import com.notemon.models.Token;
import com.notemon.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by emil on 30.04.17.
 */

public interface RestRoutes {

    String users = "users/";
    //URL FOR USERS

    @POST(users + "create")
    Call<String> createUser(@Body User user);


    @POST(users + "login")
    Call<Token> loginUser(@Body User user);
}
