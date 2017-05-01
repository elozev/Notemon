package com.notemon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.freesoulapps.preview.android.Preview;
import com.notemon.R;
import com.notemon.helpers.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by emil on 22.04.17.
 */

public class TextNoteFragment extends Fragment implements Preview.PreviewListener {

    @BindView(R.id.textNoteText)
    TextView textView;

    @BindView(R.id.preview)
    Preview preview;

    private String editedText = "";
    private String content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_text, container, false);
        ButterKnife.bind(this, view);

//        preview = (Preview) view.findViewById(R.id.preview);
        preview.setListener(this);
        preview.setVisibility(View.INVISIBLE);

        content = getArguments().getString(Constants.NOTE_TEXT_CONTENT);
        if (content != null) {
            textView.setText(content);
            checkHasItLink(content);
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    private void checkHasItLink(String content) {
        if (content.contains("http") || content.contains("www")) {
            String[] splitContent = content.split(" ");
            List<String> links = new ArrayList<>();

            for (String str : splitContent) {
                if (str.contains("http") || str.contains("www")) {
                    links.add(str);
                }
            }
            preview.setVisibility(View.VISIBLE);
            preview.setData(links.get(0));
        }
    }

    @OnLongClick(R.id.textNoteText)
    public boolean textClick() {
        Toast.makeText(getActivity(), "Edit textView", Toast.LENGTH_SHORT).show();

        new MaterialDialog.Builder(getActivity())
                .title(R.string.edit)
                .input(null, textView.getText().toString(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        editedText = input.toString();
                        Toast.makeText(getActivity(), "editedText: " + editedText, Toast.LENGTH_SHORT).show();
                    }
                })
                .alwaysCallInputCallback()
                .positiveText("Done")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Toast.makeText(getActivity(), "textView: " + editedText, Toast.LENGTH_SHORT).show();
                        textView.setText(editedText);
                        sendUpdateToAPI();
                    }
                })
                .negativeText("Cancel")
                .show();

        return false;
    }

    //TODO
    private void sendUpdateToAPI() {
    }

    @Override
    public void onDataReady(Preview preview) {
        this.preview.setMessage(preview.getLink());
    }
}
