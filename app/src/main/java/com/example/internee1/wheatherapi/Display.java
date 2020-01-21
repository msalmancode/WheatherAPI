package com.example.internee1.wheatherapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Display extends AppCompatActivity {


    TextView current_temp,day_name,days_status,city_name;
    TextView tv_humidity,tv_windspeed,tv_maxtemp,tv_mintemp,tv_pressure;

    ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        iv_icon = (ImageView) findViewById(R.id.ivIcons) ;

        tv_humidity = (TextView) findViewById(R.id.tvHumidityData);
        tv_windspeed = (TextView) findViewById(R.id.tvWindSpeedData);
        tv_maxtemp = (TextView) findViewById(R.id.tvMaxtempData);
        tv_mintemp = (TextView) findViewById(R.id.tvMintempData);
        tv_pressure = (TextView) findViewById(R.id.tvPressureData);
        current_temp = (TextView) findViewById(R.id.current_temperature_field);
        day_name = (TextView) findViewById(R.id.day_name);
        days_status= (TextView) findViewById(R.id.cityStatus);
        city_name = (TextView) findViewById(R.id.city_field);




        Bundle ex = getIntent().getExtras();

        if(ex!=null){

            iv_icon.setImageResource(ex.getInt("weather_icon"));

            current_temp.setText(ex.getString("current_temp"));
            day_name.setText(ex.getString("day_name"));

            tv_humidity.setText(ex.getString("humidity"));
            tv_windspeed.setText(ex.getString("windspeed"));
            tv_maxtemp.setText(ex.getString("max_temp"));
            tv_mintemp.setText(ex.getString("min_temp"));
            tv_pressure.setText(ex.getString("pressure"));
            days_status.setText(ex.getString("days_status"));
            city_name.setText(ex.getString("city_name"));

        }

    }
}
