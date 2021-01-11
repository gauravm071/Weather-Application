package com.example.internproject.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.internproject.ApiRepository;
import com.example.internproject.Names;
import com.example.internproject.R;
import com.example.internproject.WeatherInfoWithCurrentLocation.WeatherData;
import com.example.internproject.Interface.WeatherInterface;
import com.example.internproject.WeatherInfoWithCity.WeatherInfo;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    TextView temperature, windSpeed, description, humidity, pressure, visibility, username;
    String lat, lon;
    LottieAnimationView animation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
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
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initialization(view);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(Names.sharedPreferencename, Context.MODE_PRIVATE);
        String myusername = preferences.getString("username", "NO USERNAME");
        username.setText(myusername);
        getWeatherData();

    }

    private void Initialization(View view) {
        temperature = view.findViewById(R.id.temprature);
        windSpeed = view.findViewById(R.id.wind_speed);
        description = view.findViewById(R.id.desc);
        humidity = view.findViewById(R.id.humidity);
        pressure = view.findViewById(R.id.pressure);
        visibility = view.findViewById(R.id.visibility);
        username = view.findViewById(R.id.tvusername);
        animation = view.findViewById(R.id.lottie);
    }

    private void getWeatherData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Names.sharedPreferencename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String unit = sharedPreferences.getString("unit", "metric");
        String lat = sharedPreferences.getString("lat", "0.0");
        String lon = sharedPreferences.getString("lon", "0.0");
//        if (unit.equals("celcius")) {
//            unit = "metric";
//        } else unit = "imperial";
        ApiRepository.getCurrentLocationWeatherData(lat, lon, unit, new WeatherInterface() {
            @Override
            public void currentLocationWeatherInfo(Response<WeatherData> response) {

                if (response.code() == 200) {
                    animation.cancelAnimation();
                    animation.setVisibility(View.GONE);
                    WeatherData weatherData = response.body();
                    Log.v("TEMP", String.valueOf(weatherData.getCurrent().getTemp()));
                    setData(weatherData);
                }
            }

            @Override
            public void currentCityWeatherInfo(Response<WeatherInfo> response) {
            }

        });


    }

    @SuppressLint("SetTextI18n")
    private void setData(WeatherData weatherData) {
        double temp = weatherData.getCurrent().getTemp();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Names.sharedPreferencename, Context.MODE_PRIVATE);
        String unit = sharedPreferences.getString("unit", "celcius");

        if (unit.equals("metric")) {
            temperature.setText(String.valueOf(weatherData.getCurrent().getTemp()) + " \u2103");
            windSpeed.setText((weatherData.getCurrent().getWindSpeed()) + " m/s");
        } else {

            temperature.setText(String.valueOf(weatherData.getCurrent().getTemp()) + " \u2109");
            windSpeed.setText((weatherData.getCurrent().getWindSpeed()) + " m/h");
        }

        description.setText(String.valueOf(weatherData.getCurrent().getWeather().get(0).getDescription()));
        humidity.setText(String.valueOf(weatherData.getCurrent().getHumidity()) + "%");
        pressure.setText(String.valueOf(weatherData.getCurrent().getPressure()) + " hPa");
        visibility.setText(String.valueOf(weatherData.getCurrent().getVisibility()) + "m");
    }
}