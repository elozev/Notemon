package com.notemon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.notemon.R;
import com.notemon.adapters.TodoListAdapter;
import com.notemon.helpers.Constants;
import com.notemon.helpers.ContentSerializer;
import com.notemon.models.BaseNote;
import com.notemon.models.Status;
import com.notemon.models.TodoNote;
import com.notemon.models.TodoTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by emil on 23.04.17.
 */

public class TodoListNoteFragment extends Fragment {

    @BindView(R.id.todoRecyclerView)
    RecyclerView recyclerView;

    private String taskText = "";

    private TodoListAdapter adapter;

    private List<TodoTask> tasks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_todo, container, false);
        ButterKnife.bind(this, view);

        BaseNote note = (BaseNote) getArguments().getSerializable(Constants.NOTE_TODO);

        TodoNote todoNote = new TodoNote(note.getTitle(), Constants.NOTE_TYPE_TODO, ContentSerializer.deserializeTasks(note.getContent()), note.getContent());

        tasks = todoNote.getTasks();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new TodoListAdapter(tasks, getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String array = "";
        try {
            array = mapper.writeValueAsString(tasks);
            Log.d(TAG, "------------------------JSON: \n" + array);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TypeReference<List<TodoTask>> mapType = new TypeReference<List<TodoTask>>() {
        };
        List<TodoTask> jsonToPersonList= new ArrayList<>();
        try {
            jsonToPersonList = mapper.readValue(array, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"\n2. Convert JSON to List of person objects :");
        for(TodoTask task: jsonToPersonList){
            Log.d(TAG, "TASK" + task.getContent());
        }
        return view;
    }

    @OnClick(R.id.addTask)
    public void addTaskClick() {
        //TODO: add task to recycler by dialog
        new MaterialDialog.Builder(getActivity())
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        taskText = input.toString();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tasks.add(new TodoTask(taskText, 0, Status.TODO));
                        adapter.notifyItemInserted(0);
                        recyclerView.scrollToPosition(0);
                    }
                })
                .show();
    }
}
