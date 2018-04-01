package com.example.android.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.weather.model.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInfo extends AsyncTask<String, Void, Boolean>{
    private WeakReference<MainActivity> mainActivityReference;
    private OpenWeatherMap openWeatherMap;
    private final String URL = "http://api.openweathermap.org";
    private City city = new City();
    private int imageRes;
    private String windDirection;
    private ProgressBar progressBar;

    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();
    private Weather service = retrofit.create(Weather.class);

    public WeatherInfo(MainActivity context) {
        mainActivityReference = new WeakReference<MainActivity>(context);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        MainActivity activity = mainActivityReference.get();
        ConnectivityManager cm = (ConnectivityManager)activity.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            publishProgress();
            Call<JsonElement> call = service.getWeather(strings[0], strings[1]);
            try {
                Response<JsonElement> response = call.execute();
                openWeatherMap = gson.fromJson(response.body().toString(),
                        OpenWeatherMap.class);
                imageRes = activity.getResources().getIdentifier("w" +
                                openWeatherMap.getWeather().get(0).getIcon(), "drawable",
                        activity.getPackageName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        MainActivity activity = mainActivityReference.get();
        progressBar = (ProgressBar)activity.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Boolean succes) {
        super.onPostExecute(succes);
        progressBar.setVisibility(View.GONE);
        MainActivity activity = mainActivityReference.get();
        if (succes) {
            ImageView weatherIcon = (ImageView) activity.findViewById(R.id.weather_icon);
            weatherIcon.setImageResource(imageRes);
            double wind = openWeatherMap.getWind().getDeg();
            if (wind >= 337.5 && wind <= 360 || wind < 22.5) {
                windDirection = "северный";
            }
            if (wind >= 22.5 && wind < 67.5) {
                windDirection = "северо-восточный";
            }
            if (wind >= 67.5 && wind < 112.5) {
                windDirection = "восточный";
            }
            if (wind >= 112.5 && wind < 157.5) {
                windDirection = "юго-восточный";
            }
            if (wind >= 157.5 && wind < 202.5) {
                windDirection = "южный";
            }
            if (wind >= 202.5 && wind < 247.5) {
                windDirection = "юго-западный";
            }
            if (wind >= 247.5 && wind < 292.5) {
                windDirection = "западный";
            }
            if (wind >= 292.5 && wind < 337.5) {
                windDirection = "северо-западный";
            }
            String message = openWeatherMap.getMain().getTemp() - 273 + "°C " +
                    city.conditions.get(openWeatherMap.getWeather().get(0)
                            .getMain()) + "\nДавление: " +
                    String.format("%.2f", openWeatherMap.getMain().getPressure() / 1013)
                    + "атм.\nВлажность: " +
                    openWeatherMap.getMain().getHumidity() + "%\nВетер: " +
                    windDirection + " " + openWeatherMap.getWind().getSpeed() + "м/с\n";
            TextView textMessage = (TextView) activity.findViewById(R.id.temperature);
            textMessage.setText(message);
        }else {
            Toast.makeText(activity, "Ошибка интернет соединения", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
