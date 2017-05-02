package com.notemon.rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.notemon.MainActivity;
import com.notemon.R;
import com.notemon.helpers.Constants;
import com.notemon.helpers.DialogBuilder;
import com.notemon.models.BaseNote;
import com.notemon.models.FirebaseToken;
import com.notemon.models.Project;
import com.notemon.models.Reminder;
import com.notemon.models.Token;
import com.notemon.models.User;
import com.notemon.models.Username;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by emil on 30.04.17.
 */

public class RestMethods {

    public static void createUser(final User user, final Context context) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        Call<String> userCall = routes.createUser(user);
        DialogBuilder.startProgressDialog(context, Constants.REGISTRATION_PROGRESS);

        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Response " + response.raw());
                Toast.makeText(context.getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                DialogBuilder.dismissProgressDialog();
                loginUser(user, context, true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.d(TAG, "401: " + t.getMessage() + "\n");

                Toast.makeText(context.getApplicationContext(), "Failure with creating user!", Toast.LENGTH_SHORT).show();
                DialogBuilder.dismissProgressDialog();
            }
        });
    }

    private static Project createBaseProject(Context context) {
        Project project = new Project();
        project.setName("Base Project");
        project.setColor(context.getResources().getColor(R.color.white));
        project.setColorDark(context.getResources().getColor(R.color.white_pressed));
        return project;
    }

    public static void loginUser(User user, final Context context, final boolean isAfterReg) {
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

                        if (isAfterReg) {
                            Log.d(TAG, isAfterReg + "");
                            createProject(createBaseProject(context), context, true);
                        } else {
                            Log.d(TAG, isAfterReg + "");
                            startMainActivity(context);
                        }
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                }
                DialogBuilder.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(TAG, t.toString());
                DialogBuilder.dismissProgressDialog();
                Toast.makeText(context, "Failure with logging user!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void createProject(Project project, final Context context, final boolean isBaseProject) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = prefs.getString(Constants.TOKEN, "");

        Call<String> createProjectCall = routes.createProject(project, Constants.TOKEN_PREFIX + token);

        createProjectCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, call.request().toString());
                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Success in creating project", Toast.LENGTH_SHORT).show();
                        if (isBaseProject) {
                            startMainActivity(context);
                        }
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                        break;
                    case 500:
                        Toast.makeText(context, "Internal server error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "500: " + response.message() + "\n" + response.body() + "\n" + response.errorBody() + "\n" + response.raw() + "\n" + response.toString());

                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(context, "Failure with creating project!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Call<List<Project>> getProjects(final Context context) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        return routes.getProjects(token);
    }

    public static Call<String> createNoteToProject(final Context context, Long projectId, BaseNote note) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        Call<String> call = routes.createNoteToProject(note, token, projectId);

        return call;
    }

    public static Call<List<BaseNote>> getNotesFromProject(Long id) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        return routes.getNotesFromProject(id);
    }

    public static Call<String> deleteNoteWithId(Long id, Context context) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        Call<String> call = routes.deleteNoteWithId(id, token);
        return call;
    }

    public static void addNoteToProject(final Context context, Long projectId, Long tokenId) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        Call<String> call = routes.addNoteToProject(projectId, tokenId, token);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Success in adding to project", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                        break;
                    case 500:
                        Toast.makeText(context, "Internal server error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "500: " + response.message() + "\n" + response.body() + "\n" + response.errorBody() + "\n" + response.raw() + "\n" + response.toString());
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(context, "Failure with creating project!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addTokenToUser(final Context context, FirebaseToken deviceToken) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        Call<String> call = routes.addTokenToUser(deviceToken, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Success in adding token", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                        break;
                    case 500:
                        Toast.makeText(context, "Internal server error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "500: " + response.message() + "\n" + response.body() + "\n" + response.errorBody() + "\n" + response.raw() + "\n" + response.toString());
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(context, "Failure with creating project!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addReminderToNote(final Context context, Long noteId, Reminder reminder) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        Call<String> call = routes.addReminderToNote(noteId, token, reminder);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(context, response.code() + "", Toast.LENGTH_SHORT).show();

                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Success in adding token", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                        break;
                    case 500:
                        Toast.makeText(context, "Internal server error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "500: " + response.message() + "\n" + response.body() + "\n" + response.errorBody() + "\n" + response.raw() + "\n" + response.toString());
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(context, "Failure with creating project!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addUserToNote(final Context context, Username username, Long noteId) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        Call<String> call = routes.addUserToNote(noteId, token, username);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Success in adding token", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Log.d(TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                        break;
                    case 500:
                        Toast.makeText(context, "Internal server error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "500: " + response.message() + "\n" + response.body() + "\n" + response.errorBody() + "\n" + response.raw() + "\n" + response.toString());
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(context, "Failure with creating project!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Call<User> getCurrentUser(Context context) {
        RestRoutes routes = RestRetriever.getClient().create(RestRoutes.class);
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + prefs.getString(Constants.TOKEN, "");

        return routes.getCurrentUser(token);
    }
}
