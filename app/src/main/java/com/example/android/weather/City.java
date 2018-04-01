package com.example.android.weather;

import java.util.HashMap;
import java.util.Map;

public class City {
    public String[] cities = {"Vinnytsya", "Dnipro", "Donetsk", "Zhytomyr", "Zaporizhzhya",
    "Ivano-Frankivsk", "Kiev", "Kirovohrad", "Luhansk", "Lutsk", "Lviv", "Mykolaiv", "Odesa",
    "Poltava", "Rivne", "Sumy", "Ternopil", "Uzhhorod", "Kharkiv", "Kherson", "Khmelnytskyi",
    "Cherkasy", "Chernihiv", "Chernivtsi"};

    public Map<String, String> conditions = new HashMap<>();

    private void fillConditions(){
        conditions.put("Thunderstorm", "Ураган");
        conditions.put("Drizzle", "Мелкий дождь");
        conditions.put("Rain", "Дождь");
        conditions.put("Snow", "Снег");
        conditions.put("Clear", "Ясно");
        conditions.put("Clouds", "Облачно");
        conditions.put("Fog", "Туман");
        conditions.put("Mist", "Легкий туман");
    }

    public City() {
        fillConditions();
    }
}