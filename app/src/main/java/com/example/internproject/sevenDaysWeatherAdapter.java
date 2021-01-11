package com.example.internproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internproject.WeatherInfoWithCurrentLocation.Daily;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sevenDaysWeatherAdapter extends RecyclerView.Adapter<sevenDaysWeatherAdapter.ViewHolder> {
    List<Daily> sevenDaysWeatherlist = new ArrayList<>();
    Context context;

    public sevenDaysWeatherAdapter(List<Daily> sevenDaysWeatherlist, Context context) {
        this.sevenDaysWeatherlist = sevenDaysWeatherlist;
        this.context = context;
    }

    @NonNull
    @Override
    public sevenDaysWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.weather_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull sevenDaysWeatherAdapter.ViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        String temp = String.valueOf(sevenDaysWeatherlist.get(position).getTemp().getDay());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sevenDaysWeatherlist.get(position).getDt();
        holder.date.setText(dateString);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Names.sharedPreferencename, Context.MODE_PRIVATE);
        String unit = sharedPreferences.getString("unit", "metric");
        if (unit.equals("metric")) {
            holder.temperature.setText(String.valueOf(sevenDaysWeatherlist.get(position).getTemp().getDay()) + " \u2103");
            holder.wind_speed.setText(String.valueOf(sevenDaysWeatherlist.get(position).getWindSpeed()) + " m/s");
        } else {
            holder.temperature.setText(String.valueOf(sevenDaysWeatherlist.get(position).getTemp().getDay()) + " \u2109");
            holder.wind_speed.setText(String.valueOf(sevenDaysWeatherlist.get(position).getWindSpeed()) + " m/h");
        }

        holder.humidity.setText(String.valueOf(sevenDaysWeatherlist.get(position).getHumidity())+" %");
    }


    @Override
    public int getItemCount() {
        return sevenDaysWeatherlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView temperature, humidity, date, wind_speed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.card_temp);
            humidity = itemView.findViewById(R.id.card_humidity);
            date = itemView.findViewById(R.id.card_date);
            wind_speed = itemView.findViewById(R.id.card_windspeed);
        }
    }
}
