package com.notemon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.notemon.Constants;
import com.notemon.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emil on 22.04.17.
 */

public class MediaNoteFragment extends Fragment {

    @BindView(R.id.mediaNoteTextView)
    TextView textView;

    @BindView(R.id.mediaNoteImageView)
    ImageView imageView;

    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_media, container, false);
        ButterKnife.bind(this, view);

        url = getArguments().getString(Constants.NOTE_MEDIA_URL);
        Picasso.with(getActivity()).load(url).into(imageView);

        textView.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.mediaNoteImageView)
    public void imageClick(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
