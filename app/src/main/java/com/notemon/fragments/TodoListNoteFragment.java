package com.notemon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.notemon.R;
import com.notemon.adapters.TodoListAdapter;
import com.notemon.models.Status;
import com.notemon.models.TodoTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        tasks = new ArrayList<>();
        tasks.add(new TodoTask("1", Status.DONE, 1));
        tasks.add(new TodoTask("2", Status.DONE, 1));
        tasks.add(new TodoTask("3", Status.TODO, 1));
        tasks.add(new TodoTask("4", Status.DONE, 1));
        tasks.add(new TodoTask("5", Status.TODO, 1));
        tasks.add(new TodoTask("7", Status.TODO, 1));
        tasks.add(new TodoTask("8", Status.TODO, 1));
        tasks.add(new TodoTask("9", Status.DONE, 1));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new TodoListAdapter(tasks, getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

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
                        tasks.add(new TodoTask(taskText, Status.TODO, 1));
                        adapter.notifyItemInserted(0);
                        recyclerView.scrollToPosition(0);
                    }
                })
                .show();
    }
}
