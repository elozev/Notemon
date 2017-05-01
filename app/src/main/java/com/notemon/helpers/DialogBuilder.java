package com.notemon.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.notemon.R;
import com.notemon.activities.BasicNote;
import com.notemon.models.BaseNote;
import com.notemon.models.Status;
import com.notemon.models.TodoNote;
import com.notemon.models.TodoTask;
import com.notemon.rest.RestMethods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by emil on 30.04.17.
 */

public class DialogBuilder {

    private static Context activityContext;

    private static String collaboratorUsername;

    public static void enterUsername(final Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.add_collaborator)
                .alwaysCallInputCallback()
                .input("Username", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        collaboratorUsername = input.toString();
                    }
                })
                .positiveText(R.string.add)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO: add api call here
                        Toast.makeText(context, collaboratorUsername + " to api", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public static void promptForDelete(final Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.delete_note)
                .content(R.string.prompt_for_delete)
                .positiveText(R.string.yes)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO: call the api
                        Toast.makeText(context, "Deleting this note", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private static Calendar calendar = Calendar.getInstance();
    private static DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Toast.makeText(activityContext, calendar.getTime().getTime() + "", Toast.LENGTH_SHORT).show();
            //TODO: call the api for update
        }
    };

    public static void addReminder(Context context) {
        activityContext = context;
        new DatePickerDialog(context, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void addToProject(Context context) {

        //TODO: getProjects() then call the dialog

//        new MaterialDialog.Builder(context)
//                .title(R.string.add_to_project)
//                .items()

    }

    static MaterialDialog dialog;

    public static void startProgressDialog(Context context, String type) {
        String title = "";
        String content = "";

        switch (type) {
            case Constants.LOGIN_PROGRESS:
                title = context.getString(R.string.title_login);
                content = context.getString(R.string.content_login);
                break;
            case Constants.REGISTRATION_PROGRESS:
                title = context.getString(R.string.title_reg);
                content = context.getString(R.string.content_reg);
                break;
        }

        dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .show();

    }

    public static void dismissProgressDialog() {
        dialog.dismiss();
    }

    private static List<TodoTask> todoTasks;
    private static String taskName = "";
    private static String todoTitle = "";

    public static void preTodo(Context c, final Long projectId) {
        todoTasks = new ArrayList<>();
        todoTitle = "";
        taskName = "";
        todoTitle(c, projectId);
    }

    private static void todoTitle(final Context context, final Long projectId) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("Todo Task")
                .input("Todo List Title", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        todoTitle = input.toString();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        todoTaskAdd(context, projectId);
                    }
                }).show();
    }

    private static void todoTaskAdd(final Context context, final Long projectId) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.add_task)
                .alwaysCallInputCallback()
                .input(context.getString(R.string.task_name), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        taskName = input.toString();
                    }
                }).positiveText(R.string.add_next_task)
                .negativeText(R.string.finish)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        TodoTask todoTask = new TodoTask(taskName, 0, Status.TODO);
                        todoTasks.add(todoTask);
                        dialog.dismiss();
                        todoTaskAdd(context, projectId);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        todoTasks.add(new TodoTask(taskName, 0, Status.TODO));
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        TodoNote note = new TodoNote(todoTitle, Constants.NOTE_TYPE_TODO, todoTasks, "");

                        startTodoNote(context, note, projectId);
                    }
                })
                .show();
    }

    private static void startTodoNote(final Context context, TodoNote note, Long projectId) {
        BaseNote baseNote = new BaseNote(note.getTitle(), Constants.NOTE_TYPE_TODO, TodoTaskSerialize.serializeTasks(note.getTasks()));


        Call<String> call = RestMethods.createNoteToProject(context, projectId, baseNote);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Successfully created note to project!", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(context, "Unauthorized!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(context, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(context, BasicNote.class);
        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TODO);
        intent.putExtra(Constants.NOTE_TODO, note);
        context.startActivity(intent);
    }
}
