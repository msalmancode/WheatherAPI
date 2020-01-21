package com.example.internee1.wheatherapi;

/**
 * Created by Internee1 on 4/10/2019.
 */

public class weather_model  {

    String Humidity_field;
    String Windspeed_field;
    String Max_field;
    String Min_field;
    String Pressure_field;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay_temp() {
        return day_temp;
    }

    public void setDay_temp(String day_temp) {
        this.day_temp = day_temp;
    }

    String day;
    String day_temp;


    public String getMin_field() {
        return Min_field;
    }

    public void setMin_field(String min_field) {
        Min_field = min_field;
    }

    public String getHumidity_field() {
        return Humidity_field;
    }

    public void setHumidity_field(String humidity_field) {
        Humidity_field = humidity_field;
    }

    public String getWindspeed_field() {
        return Windspeed_field;
    }

    public void setWindspeed_field(String windspeed_field) {
        Windspeed_field = windspeed_field;
    }

    public String getMax_field() {
        return Max_field;
    }

    public void setMax_field(String max_field) {
        Max_field = max_field;
    }

    public String getPressure_field() {
        return Pressure_field;
    }

    public void setPressure_field(String pressure_field) {
        Pressure_field = pressure_field;
    }


}
