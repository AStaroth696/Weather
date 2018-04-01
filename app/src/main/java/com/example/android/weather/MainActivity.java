package com.example.android.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


public class MainActivity extends Activity {
    private City city;
    private final String KEY = "0b3f3d5f3dfeab5a27f560d3c822cdfc";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner cities = (Spinner) findViewById(R.id.cities);
        city = new City();
        getWeather(cities.getSelectedItemPosition(), KEY);

        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                getWeather(i, KEY);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getWeather(int position, String key){
        WeatherInfo weatherInfo = new WeatherInfo(MainActivity.this);
        weatherInfo.execute(city.cities[position], key);
    }
}
