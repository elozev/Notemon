package com.notemon.fragments;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.notemon.BasicNote;
import com.notemon.Constants;
import com.notemon.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by emil on 22.04.17.
 */

public class NoteRecyclerFragment extends Fragment {

    private static final int REQUEST_SPEECH_INPUT = 451;
    @BindView(R.id.noteRecycler)
    RecyclerView noteRecycler;

    @BindView(R.id.fabRecycler)
    FloatingActionsMenu fabMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_recycler, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.addMediaNote)
    public void mediaNoteClick() {
        Toast.makeText(getActivity(), "Media Note", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), BasicNote.class);
        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TEXT);
        startActivity(intent);

    }

    @OnClick(R.id.addTextNote)
    public void textNoteClick() {
        Toast.makeText(getActivity(), "Text Note", Toast.LENGTH_SHORT).show();
        dialogTitleNote(false);
    }

    @OnClick(R.id.addVoiceNote)
    public void voiceNoteClick() {
        Toast.makeText(getActivity(), "Voice Note", Toast.LENGTH_SHORT).show();
        dialogTitleNote(true);
    }

    @OnClick(R.id.addTodoList)
    public void todoListClick() {
        Toast.makeText(getActivity(), "TodoList Note", Toast.LENGTH_SHORT).show();
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
        }
    }

    String noteTitle;
    String noteContent;

    private void dialogTitleNote(final boolean isVoiceNote) {
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
                        if (isVoiceNote)
                            createVoiceNote();
                        else {
                            dialog.dismiss();
                            dialogNoteContent();
                        }
                    }
                })
                .show();
    }

    private void dialogNoteContent() {
        new MaterialDialog.Builder(getActivity())
                .title("Enter content:")
                .inputRangeRes(1, 200, R.color.project_red)
                .alwaysCallInputCallback()
                .input(getString(R.string.content), null, new MaterialDialog.InputCallback() {
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
        //TODO: call the api post

        Toast.makeText(getActivity(), "Media Note", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), BasicNote.class);
        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TEXT);
        intent.putExtra("note_title", noteTitle);
        intent.putExtra("note_content", content);
        startActivity(intent);
    }
}
