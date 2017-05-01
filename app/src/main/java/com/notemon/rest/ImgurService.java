package com.notemon.rest;

import com.notemon.models.ImageResponse;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by emil on 01.05.17.
 */
public interface ImgurService {
    String BASE_URL = "https://api.imgur.com";

    @Multipart
    @POST("/3/image")
    Call<ImageResponse> postImage(
            @Header("Authorization") String auth,
            @PartMap Map<String, RequestBody> image);
}