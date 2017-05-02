package com.notemon.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.notemon.R;
import com.notemon.helpers.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by emil on 02.05.17.
 */

public class SettingsFragment extends Fragment {


    @BindView(R.id.notificationSwitch)
    Switch notSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);

        boolean switchState = prefs.getBoolean(Constants.NOTIFICATIONS, true);
        notSwitch.setChecked(switchState);

        return view;
    }

    @OnCheckedChanged(R.id.notificationSwitch)
    public void checkedChanged(boolean isChecked){
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Constants.NOTIFICATIONS, isChecked);
        editor.apply();
        editor.commit();
    }
}
