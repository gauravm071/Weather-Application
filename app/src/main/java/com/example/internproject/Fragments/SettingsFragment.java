package com.example.internproject.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.internproject.sfName;
import com.example.internproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    CheckBox fahr, celcius;
    TextView username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String celcius_state,myusername,unit,fahrenite_state;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fahr = view.findViewById(R.id.fahr);
        username = view.findViewById(R.id.setting_username);
        celcius = view.findViewById(R.id.celcius);
        getSharedPrefernceData();

        username.setText(myusername);
        if (unit.equals("metric")) {
            celcius.setChecked(true);
            fahr.setChecked(false);
        } else {
            fahr.setChecked(true);
            celcius.setChecked(false);
        }
        celcius_state = sharedPreferences.getString("celcius_state", null);
        fahrenite_state = sharedPreferences.getString("fahrenite_state", null);
        celcius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataIncelcius();
            }

        });
        fahr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataInFahrenite();
            }
        });

    }

    private void setDataInFahrenite() {
        if (fahrenite_state.equals("checked")) {
            fahr.setChecked(false);
            celcius.setChecked(true);
            celcius_state = "checked";
            fahrenite_state = "unchecked";
            editor.putString("unit", "metric");
            editor.putString("fahrenite_state", "unchecked");
            editor.putString("celcius_state", "checked").apply();
        } else {
            fahr.setChecked(true);
            celcius.setChecked(false);
            fahrenite_state = "checked";
            celcius_state = "unchecked";
            editor.putString("unit", "imperial");
            editor.putString("fahrenite_state", "checked");
            editor.putString("celcius_state", "unchecked").apply();
        }
        editor.commit();
    }

    private void setDataIncelcius() {
        if (celcius_state.equals("checked")) {
            celcius.setChecked(false);
            fahr.setChecked(true);
            celcius_state = "unchecked";
            fahrenite_state = "checked";
            editor.putString("unit", "imperial");
            editor.putString("celcius_state", "unchecked");
            editor.putString("fahrenite_state", "checked").apply();
        } else {
            celcius.setChecked(true);
            fahr.setChecked(false);
            celcius_state = "checked";
            fahrenite_state = "unchecked";
            editor.putString("unit", "metric");
            editor.putString("celcius_state", "checked");
            editor.putString("fahrenite_state", "unchecked");
        }
        editor.commit();
    }

    private void getSharedPrefernceData() {
        sharedPreferences = this.getActivity().getSharedPreferences(sfName.sharedPreferencename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        unit = sharedPreferences.getString("unit", null);
        myusername = sharedPreferences.getString("username", "");
    }


}