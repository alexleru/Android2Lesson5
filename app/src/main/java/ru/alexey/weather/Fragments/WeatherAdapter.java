package ru.alexey.weather.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import ru.alexey.weather.Data.DataWeather;
import ru.alexey.weather.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private Context context;
    private String cityName;
    private boolean [] addData;

    public WeatherAdapter(String cityName, boolean[] addData) {
        this.cityName = cityName;
        this.addData = addData;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
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
        return DataWeather.getTemperature().length;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd");
        calendar.add(Calendar.DATE, index);
        String addInfo = dateFormat.format(calendar.getTime());
        addInfo = addInfo.concat(delimiter + DataWeather.getTemperature()[index]);
        for (int i = 0 ; i < addData.length; i++){
            if (addData[i] && i == 0) {
                addInfo = addInfo.concat(delimiter
                        + DataWeather.getWind()[index]
                        + ",  "
                        + DataWeather.getWindDirection()[index]);
            }
            if (addData[i] && i == 1) {
                addInfo = addInfo.concat(delimiter + DataWeather.getHumidity()[index]);
            }
            if (addData[i] && i == 2) {
                addInfo = addInfo.concat(delimiter + DataWeather.getPressure()[index]);
                }
        }
        return  addInfo;
    }
}
