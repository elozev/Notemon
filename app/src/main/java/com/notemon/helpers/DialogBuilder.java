package com.notemon.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.notemon.R;

import java.util.Calendar;

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

        switch (type){
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

    public static void dissmissProgressDialog() {
        dialog.dismiss();
    }
}
