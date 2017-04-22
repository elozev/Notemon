package com.notemon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.notemon.R;
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

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private List<TodoTask> tasks;
    private Context context;

    public TodoListAdapter(List<TodoTask> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoTask task = tasks.get(position);
        holder.text.setText(task.getContent());
//        holder.text.
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.todoSpinner)
        Spinner spinner;
        @BindView(R.id.itemTodoText)
        TextView text;
        @BindView(R.id.deleteTaskBtn)
        ImageButton deleteBtn;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.deleteTaskBtn)
        public void deleteClick() {
            Toast.makeText(context, "Deleting", Toast.LENGTH_SHORT).show();
            //TODO: Delete from the set
            notifyDataSetChanged();
        }
    }

    private ArrayAdapter<String> setUpSpinnerAdapter() {
        List<String> list = new ArrayList<String>();

        list.add(String.valueOf(Status.TODO));
        list.add(String.valueOf(Status.DOING));
        list.add(String.valueOf(Status.DONE));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }
}
