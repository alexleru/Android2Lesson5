package ru.alexey.weather.Data;

//Создал временно, типа поставшик данных
public class DataWeather {

    public static String[] getTemperature() {
        return new String[]{"30 °С", "11 °С", "19 °С"};
    }

    public static String[] getWind() {
        return new String[]{"3 м/с", "17 м/с", "2 м/с"};
    }

    public static String[] getWindDirection() {
        return new String[]{"Северо-западный", "Южный", "Восточный"};
    }

    public static String[] getHumidity() {
        return  new String[]{"51%", "70%", "61%"};
    }

    public static String[] getPressure() {
        return new String[]{"750 мм рт. ст.", "760 мм рт. ст.", "753 мм рт. ст."};
    }
}