package com.notemon;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.notemon.fragments.MediaNoteFragment;
import com.notemon.fragments.TextNoteFragment;
import com.notemon.fragments.TodoListNoteFragment;
import com.notemon.models.TodoTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BasicNote extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String title;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_note);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        String noteType = getIntent().getStringExtra(Constants.NOTE_TYPE);

        title = getIntent().getStringExtra("note_title");
        content = getIntent().getStringExtra("note_content");

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        replaceFragment(noteType);
    }


    //TODO: replace with the title of the note and send the needed objects
    private void replaceFragment(String noteType) {
        FragmentManager manager = getFragmentManager();


        switch (noteType){
            case Constants.NOTE_TEXT:
                toolbar.setTitle(title);

                TextNoteFragment textNoteFragment = new TextNoteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("note_content", content);
                textNoteFragment.setArguments(bundle);

                manager.beginTransaction()
                        .replace(R.id.basicNoteFrameLayout, textNoteFragment)
                        .commit();
                break;
            case Constants.NOTE_MEDIA:
                toolbar.setTitle("Media Notes");

                MediaNoteFragment mediaNoteFragment = new MediaNoteFragment();
                Bundle args = new Bundle();
                args.putString(Constants.NOTE_MEDIA_URL, "http://kingofwallpapers.com/picture/picture-008.jpg");
                mediaNoteFragment.setArguments(args);

                manager.beginTransaction()
                        .replace(R.id.basicNoteFrameLayout, mediaNoteFragment)
                        .commit();
                break;
            case Constants.NOTE_TODO:

                TodoListNoteFragment todoFragment = new TodoListNoteFragment();

                manager.beginTransaction()
                        .replace(R.id.basicNoteFrameLayout, todoFragment)
                        .commit();

                toolbar.setTitle("Todo Note");
                break;
            case Constants.NOTE_VOICE:
                toolbar.setTitle("Voice Note");
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return true;
    }
}
