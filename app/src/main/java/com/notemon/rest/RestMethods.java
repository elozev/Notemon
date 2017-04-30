package com.notemon.rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.notemon.MainActivity;
import com.notemon.helpers.Constants;
import com.notemon.helpers.DialogBuilder;
import com.notemon.models.Token;
import com.notemon.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by emil on 30.04.17.
 */

public class RestMethods {

    public static void createUser(User user, final Context context) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        Call<String> userCall = routes.createUser(user);
        DialogBuilder.startProgressDialog(context, Constants.REGISTRATION_PROGRESS);

        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Response " + response.raw());
                Toast.makeText(context.getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                DialogBuilder.dissmissProgressDialog();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(context.getApplicationContext(), "Failure with creating user!", Toast.LENGTH_SHORT).show();
                DialogBuilder.dissmissProgressDialog();
            }
        });
    }

    public static void loginUser(User user, final Context context) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        Call<Token> loginCall = routes.loginUser(user);
        DialogBuilder.startProgressDialog(context, Constants.LOGIN_PROGRESS);

        loginCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d(TAG, call.request().toString());
                Log.d(TAG, response.raw().toString());
                switch (response.code()) {
                    case 200:
                        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(Constants.TOKEN, response.body().getToken());
                        editor.apply();
                        editor.commit();
                        Toast.makeText(context, "Token:" + response.body().getToken(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Token: " + response.body().getToken());

                        startMainActivity(context);
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                }
                DialogBuilder.dissmissProgressDialog();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(TAG, t.toString());
                DialogBuilder.dissmissProgressDialog();
                Toast.makeText(context, "Failure with creating user!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
