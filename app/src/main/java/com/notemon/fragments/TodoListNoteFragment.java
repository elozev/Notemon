package com.notemon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_todo, container, false);
        ButterKnife.bind(this, view);

        List<TodoTask> tasks = new ArrayList<>();
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));
        tasks.add(new TodoTask("Banana nana nana", Status.DOING, 1));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        RecyclerView.Adapter adapter = new TodoListAdapter(tasks, getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.addTask)
    public void addTaskClick(){
        //TODO: add task to recycler by dialog
    }
}
