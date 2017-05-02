package com.notemon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notemon.R;
import com.notemon.models.User;
import com.notemon.rest.RestMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emil on 02.05.17.
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.profileUsername)
    TextView username;

    @BindView(R.id.profileEmail)
    TextView email;

    @BindView(R.id.profileFirstName)
    TextView firstName;

    @BindView(R.id.profileLastName)
    TextView lastName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        ButterKnife.bind(this, view);

        getCurrentUser();
        return view;
    }

    private void getCurrentUser() {
        Call<User> call = RestMethods.getCurrentUser(getActivity());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()) {
                    case 200:
                        if (response.body() != null)
                            setUpViews(response.body());
                        break;

                    case 400:
                        break;

                    case 500:
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void setUpViews(User user) {
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
    }

}
