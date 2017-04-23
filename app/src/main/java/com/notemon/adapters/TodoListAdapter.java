package com.notemon.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.notemon.R;
import com.notemon.models.Status;
import com.notemon.models.TodoTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by emil on 23.04.17.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private boolean onBind;

    private List<TodoTask> tasks;
    private Context context;
    private boolean isSetUp = true;
    private LinkedList<String> spinnerList;
    private TodoTask task;

    private int bindCount = 0;

    public TodoListAdapter(List<TodoTask> tasks, Context context) {
        this.tasks = sortByStatus(tasks);
        this.context = context;

        spinnerList = new LinkedList<>();
        spinnerList.add(String.valueOf(Status.TODO));
//        spinnerList.add(String.valueOf(Status.DOING));
        spinnerList.add(String.valueOf(Status.DONE));
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    TodoViewHolder holder2;
    int itemPosition;

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, final int position) {
        Log.d(TAG, bindCount++ + ";" + "itemCount: " + getItemCount());

        holder2 = holder;

        itemPosition = position;
        task = tasks.get(position);
        holder.text.setText(task.getContent());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.remove(position);
                tasks = new LinkedList<>(sortByStatus(tasks));
                notifyItemRemoved(position);
                notifyDataSetChanged();
//                notify
            }
        });

        switch (task.getStatus()) {
            case TODO:
                holder.box.setChecked(false);
                break;
            case DONE:
                holder.box.setChecked(true);
                break;
        }

        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.box.setChecked(isChecked);
                Status s = isChecked? Status.DONE : Status.TODO;
                tasks.get(position).setStatus(s);
//                tasks = new ArrayList<>(sortByStatus(tasks));
//                notifyItemChanged(position);
//                notifyDataSetChanged();

            }
        });
    }

    public synchronized void swapData(List<TodoTask> newTasks){
        tasks.removeAll(tasks);
        tasks = new ArrayList<>();
        tasks.addAll(sortByStatus(newTasks));
        notifyAll();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkedBtn)
        CheckBox box;

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

//        @OnItemSelected
    }

    private ArrayAdapter<String> setUpSpinnerAdapter() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, spinnerList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }

    private List<TodoTask> sortByStatus(List<TodoTask> tasks) {
        List<TodoTask> result = new ArrayList<>();

//        for (TodoTask task : tasks) {
//            if (task.getStatus().equals(Status.DOING)) {
//                result.add(task);
//            }
//        }

//        int lastDoingIndex = result.size();
        for (TodoTask task : tasks) {
            if (task.getStatus().equals(Status.TODO)) {
                result.add(task);
            }
        }

        for (TodoTask task : tasks) {
            if (task.getStatus().equals(Status.DONE)) {
                result.add(task);
            }
        }

        return result;
    }
}
