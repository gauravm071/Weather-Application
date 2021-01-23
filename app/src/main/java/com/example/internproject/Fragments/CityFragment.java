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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.internproject.ApiRepository;
import com.example.internproject.sfName;
import com.example.internproject.R;
import com.example.internproject.WeatherInfoWithCurrentLocation.WeatherData;
import com.example.internproject.Interface.WeatherInterface;
import com.example.internproject.WeatherInfoWithCity.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends Fragment {
    Spinner spinner;
    String selectedCity;
    LottieAnimationView animation;
    String lat, lon, unit;
    int dateIndex;
    CalendarView calendarView;
    TextView temperature, windSpeed, description, humidity, pressure, visibility, username;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFragment newInstance(String param1, String param2) {
        CityFragment fragment = new CityFragment();
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
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSharedPrefernceData();

        calendarView = (CalendarView) view.findViewById(R.id.calender);

        setCalenderForWeek();

        int currentDate= getCurrentDate();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateIndex = dayOfMonth - currentDate;
//                Toast.makeText(getContext(),String.valueOf(dateIndex) , Toast.LENGTH_SHORT).show();
                getDataOfDate();
            }
        });

        InitializeViews(view);

        spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = (String) parent.getItemAtPosition(position);
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.city_name, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void getSharedPrefernceData() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(sfName.sharedPreferencename, Context.MODE_PRIVATE);
        unit = sharedPreferences.getString("unit", "metric");
//        if (unit.equals("celcius")) unit = "metric";
//        else unit = "imperial";
    }

    private void setCalenderForWeek() {
        long currentTime = System.currentTimeMillis();
        long maxTime = currentTime + 1000 * 60 * 60 * 24 * 7;
        calendarView.setMinDate(currentTime);
        calendarView.setMaxDate(maxTime);
    }

    private void getDataOfDate() {
        ApiRepository.getCurrentLocationWeatherData(lat, lon, unit, new WeatherInterface() {
            @Override
            public void currentLocationWeatherInfo(Response<WeatherData> response) {
                if (response.code() == 200) {
                    WeatherData weatherData = response.body();
                    assert weatherData != null;
                    setCurrentDateData(weatherData);
                }
            }

            @Override
            public void currentCityWeatherInfo(Response<WeatherInfo> response) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentDateData(WeatherData weatherData) {
        if (unit.equals("metric")) {
            temperature.setText(weatherData.getDaily().get(dateIndex).getTemp().getDay() + " \u2103");
            windSpeed.setText(weatherData.getDaily().get(dateIndex).getWindSpeed() + " m/s");
        } else {
            temperature.setText(weatherData.getDaily().get(dateIndex).getTemp().getDay() + " \u2109");
            windSpeed.setText(weatherData.getDaily().get(dateIndex).getWindSpeed() + " m/h");
        }

        description.setText("----------");
        humidity.setText(weatherData.getDaily().get(dateIndex).getHumidity() + " %");
        pressure.setText(weatherData.getDaily().get(dateIndex).getPressure() + " hPa");
        visibility.setText("--------");
    }

    private void InitializeViews(View view) {
        temperature = view.findViewById(R.id.tvsetTemp);
        windSpeed = view.findViewById(R.id.tvset_wind_speed);
        description = view.findViewById(R.id.tvsetdesc);
        humidity = view.findViewById(R.id.tvsetHumidity);
        pressure = view.findViewById(R.id.tvsetPressure);
        visibility = view.findViewById(R.id.tvsetvisibility);
        animation = view.findViewById(R.id.cityweatherlottie);
        username = view.findViewById(R.id.cityUsername);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(sfName.sharedPreferencename, Context.MODE_PRIVATE);
        String myusername = preferences.getString("username", "NO USERNAME");
        username.setText(myusername);
    }


    private void getData() {
        ApiRepository.getWeatherWithCity(selectedCity, unit, new WeatherInterface() {
            @Override
            public void currentLocationWeatherInfo(Response<WeatherData> response) {

            }

            @Override
            public void currentCityWeatherInfo(Response<WeatherInfo> response) {
                if (response.code() == 200) {
                    animation.cancelAnimation();
                    animation.setVisibility(View.GONE);
                    WeatherInfo weatherInfo = response.body();
                    lat = String.valueOf(weatherInfo.getCoord().getLat());
                    lon = String.valueOf(weatherInfo.getCoord().getLon());
                    setWeatherInfo(weatherInfo);
                }
            }

        });

    }

    @SuppressLint("SetTextI18n")
    private void setWeatherInfo(WeatherInfo weatherInfo) {
        String temp = String.valueOf(weatherInfo.getMain().getTemp());
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(sfName.sharedPreferencename, Context.MODE_PRIVATE);
        String unit = sharedPreferences.getString("unit", "metric");
        if (unit.equals("metric")) {
            temperature.setText(temp + " \u2103");
            windSpeed.setText(weatherInfo.getWind().getSpeed() + " m/s");
        } else {
            temperature.setText(temp + " \u2109");
            windSpeed.setText(weatherInfo.getWind().getSpeed() + " m/h");
        }
        description.setText(String.valueOf(weatherInfo.getWeather().get(0).getDescription()));
        humidity.setText(weatherInfo.getMain().getHumidity() + " %");
        pressure.setText(weatherInfo.getMain().getPressure() + " hPa");
        visibility.setText(weatherInfo.getVisibility() + " m");
    }

    public int getCurrentDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        int currentDate = Integer.parseInt(currentDateandTime.substring(0, 2));
        return currentDate;
    }


}