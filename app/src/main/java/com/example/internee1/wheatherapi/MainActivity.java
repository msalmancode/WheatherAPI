package com.example.internee1.wheatherapi;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.DateFormat;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView selectCity, cityField, cityStatus, currentTemperatureField,weatherIcon;
    ImageView ivIconsStatus;

    int dataindex=0;

    String city = "karachi, PK";
    String OPEN_WEATHER_MAP_API = "65cd3a026b68dbd2c98c29aa747fa5ee";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectCity = (TextView) findViewById(R.id.selectCity);
        cityField = (TextView) findViewById(R.id.city_field);
        cityStatus = (TextView) findViewById(R.id.cityStatus);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);

        ivIconsStatus=(ImageView) findViewById(R.id.ivIconsStatus);

        taskLoadUp(city);


        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Change City");
                final EditText input = new EditText(MainActivity.this);
                input.setText(city);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Change",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                city = input.getText().toString();
                                taskLoadUp(city);
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void populateCardView(weather_model model_name, weather_model model) {

        final RecyclerView DataList = (RecyclerView) findViewById(R.id.Display);
        DataList.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<String> Model_Data = new ArrayList<>();
        Model_Data.add(model.getHumidity_field());
        Model_Data.add(model.getWindspeed_field());
        Model_Data.add(model.getMax_field());
        Model_Data.add(model.getMin_field());
        Model_Data.add(model.getPressure_field());

        final ArrayList<String> Model_Name = new ArrayList<>();
        Model_Name.add(model_name.getHumidity_field());
        Model_Name.add(model_name.getWindspeed_field());
        Model_Name.add(model_name.getMax_field());
        Model_Name.add(model_name.getMin_field());
        Model_Name.add(model_name.getPressure_field());

        final ArrayList<Integer> Data_Icons = new ArrayList<>();
        Data_Icons.add(R.drawable.humidity);
        Data_Icons.add(R.drawable.windspeed);
        Data_Icons.add(R.drawable.min_temp);
        Data_Icons.add(R.drawable.max_temp);
        Data_Icons.add(R.drawable.pressure);


        DataList.setAdapter(new DataAdapter(Model_Name, Model_Data,Data_Icons));



    }
    public void populateDaysCardView(String[] days_name, String[] day_temp,int[] weatherIcons,String[] day_humidity,String[] day_windspeed, String[] day_max_temp,
                                     String[] day_min_temp,String[] day_pressure, String[] city_name,String[] days_status) {



        final RecyclerView DataList = (RecyclerView) findViewById(R.id.days);
        DataList.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL,false));

        final ArrayList<Integer> array_Icons = new ArrayList<>();

        final ArrayList<String> Days_Data = new ArrayList<>();
        final ArrayList<String> Days_Name = new ArrayList<>();

        final ArrayList<String> City_Name = new ArrayList<>();
        final ArrayList<String> Days_Status = new ArrayList<>();
        final ArrayList<String> Days_Humidity = new ArrayList<>();
        final ArrayList<String> Days_WindSpeed = new ArrayList<>();
        final ArrayList<String> Days_Max_Temp = new ArrayList<>();
        final ArrayList<String> Days_Min_Temp= new ArrayList<>();
        final ArrayList<String> Days_Pressure = new ArrayList<>();


        for(int i = 0; i < dataindex; i++){

            array_Icons.add(weatherIcons[i]);

            Days_Data.add(day_temp[i]);
            Days_Name.add(returnDay(days_name[i]));

            City_Name.add((city_name[i]));
            Days_Status.add(days_status[i]);
            Days_Humidity.add((day_humidity[i]));
            Days_WindSpeed.add((day_windspeed[i]));
            Days_Max_Temp.add((day_max_temp[i]));
            Days_Min_Temp.add((day_min_temp[i]));
            Days_Pressure.add((day_pressure[i]));


        }


        DataList.setAdapter(new DaysAdapter(Days_Name,Days_Data,array_Icons, Days_Humidity,Days_WindSpeed,
                Days_Max_Temp,Days_Min_Temp,Days_Pressure,City_Name,Days_Status));


    }
    public String returnDay(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //dateInString = "2019-04-11";
        String currentDay = null;
        try {
            Calendar cal = Calendar.getInstance();
            Date date = formatter.parse(dateInString);
            currentDay = (String) DateFormat.format("EEEE", date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDay;
    }


    public void taskLoadUp(String query) {
        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();


        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    class DownloadWeather extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            String json = Function.excuteGet("https://api.openweathermap.org/data/2.5/forecast?q="
                    + args[0] + "&appid=" + OPEN_WEATHER_MAP_API);
            return json;

        }

        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {

                    JSONArray list = json.getJSONArray("list");
                    JSONObject day = list.getJSONObject(0);

                    JSONObject main = day.getJSONObject("main");
                    JSONObject wind = day.getJSONObject("wind");

                    JSONArray weather = day.getJSONArray("weather");
                    JSONObject Status = weather.getJSONObject(0);


                    ivIconsStatus.setImageResource(returnImageIcon(Status));



                    cityField.setText(json.getJSONObject("city").getString("name").toUpperCase(Locale.US)
                            + ", " + json.getJSONObject("city").getString("country"));

                    cityStatus.setText(Status.getString("main"));
//                    kelvin to celsius conversion value = 273.15;
//                    Double temp = (Double.parseDouble(main.getString("temp"))-273.15);

                    currentTemperatureField.setText(convertKelvinToCelsius(main.getString("temp")) + "°");

                    weather_model model_name = new weather_model();

                    model_name.setHumidity_field("Humidity");
                    model_name.setWindspeed_field("Wind Speed");
                    model_name.setMax_field("Max Temperature");
                    model_name.setMin_field("Min Temperature");
                    model_name.setPressure_field("Pressure");

                    weather_model model = new weather_model();

                    model.setHumidity_field(main.getString("humidity") + "%");
                    model.setWindspeed_field(wind.getString("speed"));
                    model.setMax_field(convertKelvinToCelsius(main.getString("temp_max")) + "°");
                    model.setMin_field(convertKelvinToCelsius(main.getString("temp_min")) + "°");
                    model.setPressure_field(main.getString("pressure"));

                    populateCardView(model_name, model);



                    String[] days_temp= new String[list.length()];
                    String[] days_name= new String[list.length()];

                    int[] weatherIcons= new int[list.length()];

                    String[] days_status= new String[list.length()];
                    String[] city_name= new String[list.length()];
                    String[] days_humidity= new String[list.length()];
                    String[] days_windpeed= new String[list.length()];
                    String[] days_max_temp= new String[list.length()];
                    String[] days_min_temp= new String[list.length()];
                    String[] days_pressure= new String[list.length()];


                    weather_model wm=new weather_model();


                    for(int i = 0; i < list.length(); i++){
                        JSONObject days = list.getJSONObject(i);
                        JSONObject mains = days.getJSONObject("main");

                        JSONObject winds = days.getJSONObject("wind");

                        JSONArray weathers = days.getJSONArray("weather");
                        JSONObject w_Status = weathers.getJSONObject(0);

                        String data[]=days.getString("dt_txt").split(" ");
                        if(days_name[0]==null)
                        {
                            /*********** For Weather icons show here ********/
                            weatherIcons[dataindex] = returnImageIcon(w_Status);

                            /*********** For Weather data show here ********/
                            days_name[dataindex]=data[0];
                            days_temp[dataindex]=convertKelvinToCelsius(mains.getString("temp")) + "°";

                            city_name[dataindex]=json.getJSONObject("city").getString("name").toUpperCase(Locale.US)
                                    + ", " + json.getJSONObject("city").getString("country");
                            days_status[dataindex]=w_Status.getString("main");
                            days_humidity[dataindex]= mains.getString("humidity") + "%";
                            days_windpeed[dataindex]= winds.getString("speed");
                            days_max_temp[dataindex]= convertKelvinToCelsius(mains.getString("temp_max")) + "°";
                            days_min_temp[dataindex]= convertKelvinToCelsius(mains.getString("temp_min")) + "°";
                            days_pressure[dataindex]= mains.getString("pressure");
                            dataindex++;
                        }
                        else if(!days_name[dataindex-1].equals(data[0]))
                        {
                            /*********** For Weather icons show here ********/
                            weatherIcons[dataindex] = returnImageIcon(w_Status);

                            /*********** For Weather data show here ********/
                            days_name[dataindex]=data[0];
                            days_temp[dataindex]=convertKelvinToCelsius(mains.getString("temp")) + "°";

                            city_name[dataindex]=json.getJSONObject("city").getString("name").toUpperCase(Locale.US)
                                    + ", " + json.getJSONObject("city").getString("country");
                            days_status[dataindex]=w_Status.getString("main");
                            days_humidity[dataindex]= mains.getString("humidity") + "%";
                            days_windpeed[dataindex]= winds.getString("speed");
                            days_max_temp[dataindex]= convertKelvinToCelsius(mains.getString("temp_max")) + "°";
                            days_min_temp[dataindex]= convertKelvinToCelsius(mains.getString("temp_min")) + "°";
                            days_pressure[dataindex]= mains.getString("pressure");

                            dataindex++;
                        }

                    }


                    populateDaysCardView(days_name, days_temp,
                            weatherIcons,days_humidity,days_windpeed,days_max_temp,days_min_temp,days_pressure,city_name,days_status);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
        public long convertKelvinToCelsius(String celsius) {
            Double temp = (Double.parseDouble(celsius)-273.15);
            return Math.round(temp);

        }
        public Integer returnImageIcon(JSONObject Status) {
            String weather_desc = null;
            String weather = null;
            int iconValue = 0;
            try {
                weather=Status.getString("main");
                weather_desc = Status.getString("description");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (weather) {
                case "Clouds":
                    if (weather_desc.equals("few clouds"))
                        iconValue = R.drawable.few_clouds;
                    else if (weather_desc.equals("scattered clouds"))
                        iconValue = R.drawable.scattered_clouds;
                    else if (weather_desc.equals("overcast clouds"))
                        iconValue = R.drawable.overcast_clouds;
                    else if (weather_desc.equals("broken clouds"))
                        iconValue = R.drawable.broken_clouds;
                    else
                        iconValue = R.drawable.cloud;
                    break;

                case "Rain":
                    if (weather_desc.equals("light rain"))
                        iconValue = R.drawable.light_rain;
                    else if (weather_desc.equals("moderate rain"))
                        iconValue = R.drawable.moderate_rain;
                    else if (weather_desc.equals("heavy intensity rain"))
                        iconValue = R.drawable.heavy_rain;
                    else
                        iconValue = R.drawable.light_rain;
                    break;

                case "Clear":
                    iconValue = R.drawable.clear_sky;
                    break;
            }
            return iconValue;
        }
    }
}
