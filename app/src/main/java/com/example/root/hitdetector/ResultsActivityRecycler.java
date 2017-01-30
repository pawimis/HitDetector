package com.example.root.hitdetector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 29.01.17.
 */

public class ResultsActivityRecycler extends RecyclerView.Adapter<ResultsActivityRecycler.ListViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<String> datas ;
    DBHelper dbHelper;

    public ResultsActivityRecycler(Context context,ArrayList<String> datas) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.datas = datas;
        dbHelper = new DBHelper(context);


    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.adapter_results,parent,false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String data = datas.get(position);
        for(String temp : datas){
            Log.i("results",temp);
        }
        String []dataTable = data.split(",:");
        Log.i("score",dataTable[0]);
        Log.i("date",dataTable[1]);
        holder.score.setText(dataTable[0] + " ms");
        holder.date.setText(dataTable[1]);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView score;
        TextView date;
        public ListViewHolder(View itemView) {
            super(itemView);

            score = (TextView) itemView.findViewById(R.id.adapterResults_Text_Score);
            date = (TextView) itemView.findViewById(R.id.adapterResults_Text_Date);
        }
    }


}
