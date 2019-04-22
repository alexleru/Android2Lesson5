package ru.alexey.weather.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.alexey.weather.Data.WeatherDataParsingSingleton;
import ru.alexey.weather.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private String cityName;
    private boolean [] addData;
    private Resources resources;

    public WeatherAdapter(String cityName, boolean[] addData, Resources resources) {
        this.cityName = cityName;
        this.addData = addData;
        this.resources = resources;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        weatherViewHolder.textCityName.setText(cityName);
        String showAddData = showAddData(i);
        weatherViewHolder.textCell.setText(showAddData);
    }

    @Override
    public int getItemCount() {
        int i  = WeatherDataParsingSingleton.getInstance().temp.size();
        return i;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textCityName;
        TextView textCell;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            textCityName = view.findViewById(R.id.text_view_city_name);
            textCell = view.findViewById(R.id.linear_layout_cell);
        }
    }

    private String showAddData(int index){
        String delimiter = "\n";
        String addInfo = WeatherDataParsingSingleton.getInstance().dataTime.get(index);
        addInfo = addInfo.concat(delimiter
                + round(WeatherDataParsingSingleton.getInstance().temp.get(index), 1)
                + " ℃");
        for (int i = 0 ; i < addData.length; i++){
            if (addData[i] && i == 0) {
                addInfo = addInfo.concat(delimiter
                        + round(WeatherDataParsingSingleton.getInstance().wind.get(index), 0)
                        + " " + resources.getString(R.string.metr_per_sec)
                        + ",  "
                        + WeatherDataParsingSingleton.getInstance().windDirectionString.get(index));
            }
            if (addData[i] && i == 1) {
                addInfo = addInfo.concat(delimiter + WeatherDataParsingSingleton.getInstance().hum.get(index) + " %");
            }
            if (addData[i] && i == 2) {
                //Поделил на 1,33322, что бы получить значения из кПа в мм.рт.ст
                addInfo = addInfo.concat(delimiter
                        + round(WeatherDataParsingSingleton.getInstance().pres.get(index)/1.33322, 1)
                        + " " + resources.getString(R.string.mmhg));
                }
        }
        return addInfo;
    }

    //метод, который округляет на заданное кол-во знаков
    private double round(double value, int number){
        return Math.round(value * Math.pow(10, number))/Math.pow(10, number);
    }
}
