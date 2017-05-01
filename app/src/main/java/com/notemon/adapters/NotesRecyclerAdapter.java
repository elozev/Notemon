package com.notemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.notemon.R;
import com.notemon.activities.BasicNote;
import com.notemon.helpers.Constants;
import com.notemon.helpers.TodoTaskSerialize;
import com.notemon.models.BaseNote;
import com.notemon.models.MediaNote;
import com.notemon.models.TextNote;
import com.notemon.models.TodoNote;
import com.notemon.models.TodoTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by emil on 4/26/17.
 */

public class NotesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseNote> notes;
    private Context context;

    public NotesRecyclerAdapter(List<BaseNote> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View viewText = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_text_recycler, parent, false);
                return new TextNoteHolder(viewText);
            case 1:
                View viewMedia = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_media_recycler, parent, false);
                return new MediaNoteHolder(viewMedia);
            case 2:
                View viewTodo = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_todo_recycler, parent, false);
                return new TodoNoteHolder(viewTodo);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                BaseNote baseNote = notes.get(position);
                TextNoteHolder textNoteHolder = (TextNoteHolder) holder;

                TextNote textNote = new TextNote(baseNote.getTitle(), baseNote.getType(), baseNote.getContent());

                textNoteHolder.title.setText(textNote.getTitle());
                textNoteHolder.content.setText(textNote.getContent());
                textNoteHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Click: " + notes.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, BasicNote.class);
                        intent.putExtra(Constants.NOTE_TYPE, Constants.NOTE_TEXT);
                        intent.putExtra(Constants.NOTE_TITLE, notes.get(position).getTitle());
                        intent.putExtra(Constants.NOTE_TEXT_CONTENT, notes.get(position).getContent());
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                MediaNoteHolder mediaNoteHolder = (MediaNoteHolder) holder;
                MediaNote mediaNote = (MediaNote) notes.get(position);

                mediaNoteHolder.title.setText(mediaNote.getTitle());
                mediaNoteHolder.content.setText(mediaNote.getContent());
                Picasso.with(context).load(mediaNote.getMediaUrl()).into(mediaNoteHolder.imageView);

                break;
            case 2:
                BaseNote baseNote2 = notes.get(position);
                TodoNoteHolder todoNoteHolder = (TodoNoteHolder) holder;

                TodoNote todoNote = new TodoNote(baseNote2.getTitle(), Constants.NOTE_TYPE_TODO, TodoTaskSerialize.deserializeTasks(baseNote2.getContent()), baseNote2.getContent());

                todoNoteHolder.title.setText(todoNote.getTitle());

                List<String> tasks = new ArrayList<>(todoNote.getTasks().size());
                for (TodoTask task : todoNote.getTasks()) {
                    tasks.add(task.getContent());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, tasks);
                todoNoteHolder.listView.setAdapter(adapter);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return notes.get(position).getType();
    }

    class TextNoteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.noteTextRecyclerLinearLayout)
        LinearLayout layout;

        @BindView(R.id.textTitleRecycler)
        TextView title;

        @BindView(R.id.textContentRecycler)
        TextView content;

        TextNoteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MediaNoteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mediaTitleRecycler)
        TextView title;

        @BindView(R.id.mediaContentRecycler)
        TextView content;

        @BindView(R.id.mediaImageRecycler)
        ImageView imageView;

        MediaNoteHolder(View viewMedia) {
            super(viewMedia);
            ButterKnife.bind(this, viewMedia);
        }
    }

    class TodoNoteHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.todoTitleRecycler)
        TextView title;

        @BindView(R.id.todoListView)
        ListView listView;

        TodoNoteHolder(View viewTodo) {
            super(viewTodo);
            ButterKnife.bind(this, viewTodo);
        }
    }
}
