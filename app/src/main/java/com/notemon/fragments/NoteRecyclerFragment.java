package com.notemon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.notemon.BasicNote;
import com.notemon.Constants;
import com.notemon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emil on 22.04.17.
 */

public class NoteRecyclerFragment extends Fragment {


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
    public void mediaNoteClick(){
        Toast.makeText(getActivity(), "Media Note", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), BasicNote.class);
        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TEXT);
        startActivity(intent);

    }

    @OnClick(R.id.addTextNote)
    public void textNoteClick(){
        Toast.makeText(getActivity(), "Text Note", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.addVoiceNote)
    public void voiceNoteClick(){
        Toast.makeText(getActivity(), "Voice Note", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.addTodoList)
    public void todoListClick(){
        Toast.makeText(getActivity(), "TodoList Note", Toast.LENGTH_SHORT).show();
    }
}
