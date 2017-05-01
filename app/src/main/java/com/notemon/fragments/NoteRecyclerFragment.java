package com.notemon.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.notemon.R;
import com.notemon.activities.BasicNote;
import com.notemon.adapters.NotesRecyclerAdapter;
import com.notemon.helpers.Constants;
import com.notemon.helpers.DialogBuilder;
import com.notemon.helpers.DocumentHelper;
import com.notemon.models.BaseNote;
import com.notemon.models.TextNote;
import com.notemon.models.UploadImage;
import com.notemon.rest.RestMethods;
import com.notemon.rest.UploadService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by emil on 22.04.17.
 */

public class NoteRecyclerFragment extends Fragment {

    private static final int REQUEST_SPEECH_INPUT = 451;
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 412;
    @BindView(R.id.noteRecycler)
    RecyclerView noteRecycler;

    @BindView(R.id.fabRecycler)
    FloatingActionsMenu fabMenu;
    private Long projectId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_recycler, container, false);

        ButterKnife.bind(this, view);

        projectId = getArguments().getLong(Constants.PROJECT_ID);
        getNotesForProject();
//        createTextNote("Title", "Content");
        return view;
    }

    private void getNotesForProject() {
        Call<List<BaseNote>> call = RestMethods.getNotesFromProject(projectId);
        call.enqueue(new Callback<List<BaseNote>>() {
            @Override
            public void onResponse(Call<List<BaseNote>> call, Response<List<BaseNote>> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(getActivity(), "Success in getting notes from project", Toast.LENGTH_SHORT).show();
                        setUpRecycler(response.body());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<BaseNote>> call, Throwable t) {

            }
        });
    }

    private void setUpRecycler(List<BaseNote> notes) {
        NotesRecyclerAdapter adapter = new NotesRecyclerAdapter(notes, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        noteRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        noteRecycler.setAdapter(adapter);
    }

    @OnClick(R.id.addMediaNote)
    public void mediaNoteClick() {
//        Intent intent = new Intent(getActivity(), BasicNote.class);
//        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TEXT);
//        startActivity(intent);
        dialogTitleNote(Constants.NOTE_TYPE_MEDIA);
    }

    @OnClick(R.id.addTextNote)
    public void textNoteClick() {
        dialogTitleNote(Constants.NOTE_TYPE_TEXT);
    }

    @OnClick(R.id.addVoiceNote)
    public void voiceNoteClick() {
        dialogTitleNote(Constants.NOTE_TYPE_VOICE);
    }

    @OnClick(R.id.addTodoList)
    public void todoListClick() {
        Toast.makeText(getActivity(), "TodoList Note", Toast.LENGTH_SHORT).show();
        //TODO implement
        DialogBuilder.preTodo(getActivity(), projectId);
    }

    private void createVoiceNote() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQUEST_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Toast.makeText(getActivity(), result.get(0), Toast.LENGTH_LONG).show();
                    createTextNote(noteTitle, result.get(0));
                }
            }
            break;

            case Constants.FILE_PICK: {
                Uri returnUri;
                if (resultCode != RESULT_OK)
                    return;

                returnUri = data.getData();
                String filePath = DocumentHelper.getPath(getActivity(), returnUri);
                if (filePath == null || filePath.isEmpty()) return;
                File chosenFile = new File(filePath);

                UploadImage upload = new UploadImage();
                upload.image = chosenFile;
                upload.title = "djakuzi";
                upload.uri = returnUri;
//                Picasso.with(getActivity()).load(returnUri).into(profilePhoto);
                UploadService service = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    service = new UploadService(getContext(), getActivity());
                }
                service.buildRestAdapter(upload);
            }
            break;
        }
    }

    String noteTitle;
    String noteContent;

    private void dialogTitleNote(final int type) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.enter_title)
                .inputRangeRes(1, 200, R.color.project_red)
                .input(getString(R.string.title), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        noteTitle = input.toString();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                switch (type) {
                    case Constants.NOTE_TYPE_VOICE:
                        createVoiceNote();
                        break;
                    case Constants.NOTE_TYPE_TEXT:
                        dialog.dismiss();
                        dialogNoteContent();
                        break;
                    case Constants.NOTE_TYPE_MEDIA:
                        dialogPickFile();
                }
            }
        })
                .show();
    }

    private void dialogPickFile() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            filePicker();
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new MaterialDialog.Builder(getActivity())
                        .title("Permissions needed!")
                        .content("App needs permissions to access storage")
                        .positiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                requestPermission();
                            }
                        })
                        .show();
            } else {
                requestPermission();
            }

        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_STORAGE);
    }

    private void filePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, Constants.FILE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    filePicker();
                } else {
                    new MaterialDialog.Builder(getActivity())
                            .title("Permissions needed!")
                            .content("App needs permissions to access storage")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    requestPermission();
                                }
                            })
                            .show();
                }
        }
    }

    private void dialogNoteContent() {
        new MaterialDialog.Builder(getActivity())
                .title("Enter content:")
                .inputRangeRes(1, 200, R.color.project_red)
                .alwaysCallInputCallback()
                .input("Content", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog3, @NonNull CharSequence input3) {
                        noteContent = input3.toString();
                        Log.d(TAG, "Values are here: " + input3.toString());

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                Toast.makeText(getActivity(), noteContent, Toast.LENGTH_SHORT).show();
                createTextNote(noteTitle, noteContent);
            }
        })
                .show();
    }

    private void createTextNote(String noteTitle, String content) {
        Toast.makeText(getActivity(), "NoteTitle: " + noteTitle + "; Content: " + content, Toast.LENGTH_SHORT).show();
        TextNote textNote = new TextNote(noteTitle, Constants.NOTE_TYPE_TEXT, content);
        BaseNote baseNote = new BaseNote(textNote.getTitle(), Constants.NOTE_TYPE_TEXT, content);
        baseNote.setType(Constants.NOTE_TYPE_TEXT);

        Call<String> call = RestMethods.createNoteToProject(getActivity(), projectId, baseNote);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(GifHeaderParser.TAG, response.raw().toString());
                switch (response.code()) {
                    case 200:
                        Toast.makeText(getActivity(), "Success in creating note into project", Toast.LENGTH_SHORT).show();
                        getNotesForProject();
                        break;

                    case 401:
                        Toast.makeText(getActivity(), "401 in creating note into project", Toast.LENGTH_SHORT).show();
                        Log.d(GifHeaderParser.TAG, "401: " + response.message() + "\n" + response.body() + "\n" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(GifHeaderParser.TAG, t.toString());
                Toast.makeText(getActivity(), "Failure with adding note to project!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(getActivity(), BasicNote.class);
        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TEXT);
        intent.putExtra(Constants.NOTE_TITLE, noteTitle);
        intent.putExtra(Constants.NOTE_TEXT_CONTENT, content);
        startActivity(intent);
    }

}
