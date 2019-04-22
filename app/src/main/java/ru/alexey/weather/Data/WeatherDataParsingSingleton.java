package ru.alexey.weather.Data;

import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.alexey.weather.R;


public class WeatherDataParsingSingleton {
    private static WeatherDataParsingSingleton instance;
    public ArrayList<Double> temp;
    public ArrayList<Double> wind;
    public ArrayList<String> windDirectionString;
    public ArrayList<Integer> hum;
    public ArrayList<Double> pres;
    public ArrayList<String> dataTime;
    public Resources resources;

    private WeatherDataParsingSingleton(){}

    public static WeatherDataParsingSingleton getInstance(){
        if(instance == null){
            instance = new WeatherDataParsingSingleton();
        }
        return instance;
    }

    public void startConnection(final String city, Resources resources){
        this.resources = resources;
        temp = new ArrayList<>();
        wind = new ArrayList<>();
        windDirectionString = new ArrayList<>();
        hum = new ArrayList<>();
        pres = new ArrayList<>();
        dataTime = new ArrayList<>();



        final JSONObject jsonObject = WeatherDataLoader.getJSONData(city);
        parsingJson(jsonObject);
    }

    private void parsingJson(JSONObject jsonObject){
        try {
            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
            for (int i=0; i < jsonArrayList.length(); i++){
                temp.add(jsonArrayList.getJSONObject(i).getJSONObject("main").getDouble("temp"));
                hum.add(jsonArrayList.getJSONObject(i).getJSONObject("main").getInt("humidity"));
                wind.add(jsonArrayList.getJSONObject(i).getJSONObject("wind").getDouble("speed"));
                Double windDirectionDouble = (jsonArrayList.getJSONObject(i).getJSONObject("wind").getDouble("deg"));
                windDirectionString.add(windDirectionToString(windDirectionDouble));
                pres.add(jsonArrayList.getJSONObject(i).getJSONObject("main").getDouble("pressure"));
                dataTime.add(substringLess2(jsonArrayList.getJSONObject(i).getString("dt_txt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Убираем последние два символа у строки
    private String substringLess2(String str){
        return str.substring(0, str.length()-3);
    }

    //получаем направление ветра в градусах и возращаем в описательном виде
    // (90 - это Восточный, 210 Юго-Западный)
    private String windDirectionToString(Double windDirectionDouble){

        if (windDirectionDouble >= 0 && windDirectionDouble < 22.5
        && windDirectionDouble >= 337.5 && windDirectionDouble <= 360){
            return resources.getString(R.string.north);
        }else if (windDirectionDouble >= 22.5 && windDirectionDouble < 67.5){
            return resources.getString(R.string.northeast);
        }else if (windDirectionDouble >= 67.5 && windDirectionDouble < 112.5){
            return resources.getString(R.string.east);
        }else if (windDirectionDouble >= 112.5 && windDirectionDouble < 157.5){
            return resources.getString(R.string.southeast);
        }else if (windDirectionDouble >= 157.5 && windDirectionDouble < 202.5){
            return resources.getString(R.string.south);
        }else if (windDirectionDouble >= 202.5 && windDirectionDouble < 247.5){
            return resources.getString(R.string.southwest);
        }else if (windDirectionDouble >= 247.5 && windDirectionDouble < 292.5){
            return resources.getString(R.string.west);
        }else if (windDirectionDouble >= 292.5 && windDirectionDouble < 337.5){
            return resources.getString(R.string.northwest);
        }
        return resources.getString(R.string.none_data);
    }

}
