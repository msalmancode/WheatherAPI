package com.example.internee1.wheatherapi;

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

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private ArrayList<String> data_name;
    private ArrayList<String> data;
    private ArrayList<Integer> icons;


    public DataAdapter(ArrayList<String> data_name, ArrayList<String> data, ArrayList<Integer> ICONS) {

        this.data_name = data_name;
        this.data = data;
        this.icons = ICONS;

    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.card_view_layout, parent,false);
        final ViewHolder viewHolders = new ViewHolder(view);
        return viewHolders;

    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        String nametitle=data_name.get(position);
        holder.tvDislayLabel.setText(nametitle);

        String datatitle=data.get(position);
        holder.tvDisplayData.setText(datatitle);

        int dataicon=icons.get(position);
        holder.ivIcons.setImageResource(dataicon);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDislayLabel,tvDisplayData;
        ImageView ivIcons;

        public ViewHolder(View itemView){
            super(itemView);
            tvDislayLabel = (TextView) itemView.findViewById(R.id.tvDislayLabel);
            tvDisplayData = (TextView) itemView.findViewById(R.id.tvDislayData);
            ivIcons =(ImageView) itemView.findViewById(R.id.ivIcons);

        }
    }
}
