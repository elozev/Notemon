package com.notemon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.notemon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emil on 22.04.17.
 */

public class TextNoteFragment extends Fragment {

    @BindView(R.id.textNoteText)
    TextView textView;

    private String editedText = "";
    private String content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_text, container, false);
        ButterKnife.bind(this, view);

        content = getArguments().getString("note_content");
        textView.setText(content);

        return view;
    }


    @OnClick(R.id.textNoteText)
    public void textClick(){
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
    }


    //TODO
    private void sendUpdateToAPI() {
    }

}
