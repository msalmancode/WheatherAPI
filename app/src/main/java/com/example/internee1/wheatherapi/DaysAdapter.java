package com.example.internee1.wheatherapi;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Internee1 on 4/10/2019.
 */

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private ArrayList<Integer> days_icons;

    private ArrayList<String> data_name;
    private ArrayList<String> data;

    private ArrayList<String> days_cityname;
    private ArrayList<String> days_status;
    private ArrayList<String> days_humidity;
    private ArrayList<String> days_windpeed;
    private ArrayList<String> days_max_temp;
    private ArrayList<String> days_min_temp;
    private ArrayList<String> days_pressure;

    public DaysAdapter(ArrayList<String> data_name,ArrayList<String> data, ArrayList<Integer> weather_icon,ArrayList<String> humidity,
                       ArrayList<String> windspeed, ArrayList<String> max_temp,ArrayList<String> min_temp ,
                       ArrayList<String> pressure,ArrayList<String> cityname,ArrayList<String> daystatus) {


        this.days_icons = weather_icon;

        this.data_name = data_name;
        this.data = data;

        this.days_cityname=cityname;
        this.days_status=daystatus;
        this.days_humidity=humidity;
        this.days_windpeed=windspeed;
        this.days_max_temp=max_temp;
        this.days_min_temp=min_temp;
        this.days_pressure=pressure;
    }

    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.days_card_view_layout, parent,false);
        final DaysAdapter.ViewHolder viewHolders = new DaysAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolders.getAdapterPosition();

                Intent intend= new Intent(parent.getContext(),Display.class);

                intend.putExtra("weather_icon",days_icons.get(pos));

                intend.putExtra("current_temp",data.get(pos));
                intend.putExtra("day_name",data_name.get(pos));
                intend.putExtra("city_name",days_cityname.get(pos));
                intend.putExtra("days_status",days_status.get(pos));

                intend.putExtra("humidity", days_humidity.get(pos));
                intend.putExtra("windspeed",days_windpeed.get(pos));
                intend.putExtra("max_temp",days_max_temp.get(pos));
                intend.putExtra("min_temp",days_min_temp.get(pos));
                intend.putExtra("pressure",days_pressure.get(pos));


                parent.getContext().startActivity(intend);
            }
        });
        return viewHolders;



    }

    @Override
    public void onBindViewHolder(DaysAdapter.ViewHolder holder, int position) {
        String nametitle=data_name.get(position);
        holder.tvDislayDays.setText(nametitle);

        String datatitle=data.get(position);
        holder.tvDisplayTemp.setText(datatitle);

        int weatherIcon=days_icons.get(position);
        holder.ivIcons.setImageResource(weatherIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDislayDays,tvDisplayTemp;
        ImageView ivIcons;

        public ViewHolder(View itemView){
            super(itemView);
            tvDislayDays = (TextView) itemView.findViewById(R.id.tvDislaydays);
            tvDisplayTemp= (TextView) itemView.findViewById(R.id.tvDisplayTemp);
            ivIcons= (ImageView) itemView.findViewById(R.id.ivIcons);
        }
    }
}
