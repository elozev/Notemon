package com.notemon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.notemon.R;
import com.notemon.models.BaseNote;
import com.notemon.models.TodoNote;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by emil on 4/26/17.
 */

public class NotesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseNote> notes;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class TextNoteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textTitleRecycler)
        TextView title;

        @BindView(R.id.textContentRecycler)
        TextView content;

        TextNoteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class MediaNoteHolder extends RecyclerView.ViewHolder {

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

    private class TodoNoteHolder extends RecyclerView.ViewHolder {
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
