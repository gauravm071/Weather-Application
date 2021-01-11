package com.example.internproject.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.internproject.ApiRepository;
import com.example.internproject.Interface.WeatherInterface;
import com.example.internproject.Names;
import com.example.internproject.R;
import com.example.internproject.WeatherInfoWithCity.WeatherInfo;
import com.example.internproject.WeatherInfoWithCurrentLocation.Daily;
import com.example.internproject.WeatherInfoWithCurrentLocation.WeatherData;
import com.example.internproject.sevenDaysWeatherAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SevenDaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SevenDaysFragment extends Fragment {
    TextView username;
    String lat, lon;
    LottieAnimationView lottieAnimationView;
    List<Daily> dailyWeatherList = new ArrayList<>();
    RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SevenDaysFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SevenDaysFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SevenDaysFragment newInstance(String param1, String param2) {
        SevenDaysFragment fragment = new SevenDaysFragment();
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
        return inflater.inflate(R.layout.fragment_seven_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Names.sharedPreferencename, Context.MODE_PRIVATE);
        lat = sharedPreferences.getString("lat", "0.0");
        lon = sharedPreferences.getString("lon", "0.0");
        String unit = sharedPreferences.getString("unit", "metric");
        String myusername = sharedPreferences.getString("username", "");
//        if (unit.equals("celcius")) unit = "metric";
//        else unit = "imperial";
        Initialization(view);
        username.setText(myusername);
        getData(lat, lon, unit);

    }

    private void getData(String lat, String lon, String unit) {
        ApiRepository.getCurrentLocationWeatherData(lat, lon, unit, new WeatherInterface() {
            @Override
            public void currentLocationWeatherInfo(Response<WeatherData> response) {
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);
                WeatherData weatherData = response.body();
                setData(weatherData);
            }

            @Override
            public void currentCityWeatherInfo(Response<WeatherInfo> response) {

            }

        });

    }

    private void setData(WeatherData weatherData) {
        dailyWeatherList = weatherData.getDaily();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new sevenDaysWeatherAdapter(dailyWeatherList, getContext()));


    }

    private void Initialization(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        username = view.findViewById(R.id.sevendays_username);
        lottieAnimationView = view.findViewById(R.id.weeklyreportlottie);

    }
}