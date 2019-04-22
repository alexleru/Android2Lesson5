package ru.alexey.weather.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;
import ru.alexey.weather.ActivityAboutWeather;
import ru.alexey.weather.Data.WeatherDataParsingSingleton;
import ru.alexey.weather.R;

public class FragmentSearch extends Fragment{
    public static final String ABOUT_WEATHER = "ABOUT_WEATHER";
    public static final String ABOUT_APP = "ABOUT_APP";
    public static final String FEEDBACK = "FEEDBACK";
    private View view;
    private String cityName;
    public final static String CITY = "CITY";
    public final static String ABOUT = "ABOUT";
    private boolean isExitFragmentAboutWeather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExitFragmentAboutWeather =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private Intent getIntentAboutWeather() {
        Intent intent = new Intent(getActivity(), ActivityAboutWeather.class);
        intent.putExtra(CITY, cityName);
        intent.putExtra(ABOUT, ABOUT_WEATHER);
        return intent;
    }

    private Intent getIntentAboutApp() {
        Intent intent = new Intent(getActivity(), ActivityAboutWeather.class);
        intent.putExtra(ABOUT, ABOUT_APP);
        return intent;
    }

    private Intent getIntentFeedBack() {
        Intent intent = new Intent(getActivity(), ActivityAboutWeather.class);
        intent.putExtra(ABOUT, FEEDBACK);
        return intent;
    }

    private Bundle getBundleAboutWeather() {
        Bundle bundle = new Bundle();
        bundle.putString(CITY, cityName);
        return bundle;
    }

    private void initView(){
        CardView cardViewMoscow = view.findViewById(R.id.cardViewMoscow);
        cardViewMoscow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCellSearch(Objects.requireNonNull(getResources().getString(R.string.moscow)));
            }
        });
        CardView cardViewPeterburg = view.findViewById(R.id.cardViewSaintPeterburg);
        cardViewPeterburg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCellSearch(Objects.requireNonNull(getResources().getString(R.string.saint_petersburg)));
            }
        });
    }

    private void onClickCellSearch(String cityNameOfCard) {
        cityName = cityNameOfCard;
        AsyncGetData asyncGetData = new AsyncGetData();
        asyncGetData.execute(cityName);
    }

    class AsyncGetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WeatherDataParsingSingleton.getInstance().startConnection(strings[0], getResources());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isExitFragmentAboutWeather){
                showFragmentAboutWeather();
            }
            else {
                startActivity(getIntentAboutWeather());
            }
        }
    }

    public void onClickMenuAboutApp() {
        if(isExitFragmentAboutWeather){
            showFragmentAboutApp();
        }
        else{
            startActivity(getIntentAboutApp());
        }
    }

    public void onClickMenuFeedback() {
        if(isExitFragmentAboutWeather){
            showFragmentFeedback();
        }
        else{
            startActivity(getIntentFeedBack());
        }
    }

    private void showFragmentAboutWeather(){
        FragmentAboutWeather detail = FragmentAboutWeather.create(getBundleAboutWeather());
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_about_weather, detail);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
    }

    private void showFragmentAboutApp() {
        FragmentAboutApp detail = new FragmentAboutApp();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_about_weather, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }

    private void showFragmentFeedback() {
        FragmentFeedback detail = new FragmentFeedback();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_about_weather, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }
}
