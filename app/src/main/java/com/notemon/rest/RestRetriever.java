package com.notemon.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emil on 30.04.17.
 */

public class RestRetriever {

    private static final String BASE_URL = "https://morning-retreat-85964.herokuapp.com/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getImageAdapter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ImgurService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
