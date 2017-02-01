package com.example.root.hitdetector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class ResultsActivityRecycler extends RecyclerView.Adapter<ResultsActivityRecycler.ListViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> data;

    ResultsActivityRecycler(Context context, ArrayList<String> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.adapter_results,parent,false);
        return new ListViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String dataString = data.get(position);
        String[] dataTable = dataString.split(",:");
        Log.i("score",dataTable[0]);
        Log.i("date",dataTable[1]);
        holder.score.setText(dataTable[0] + " ms");
        holder.date.setText(dataTable[1]);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView score;
        TextView date;

        ListViewHolder(View itemView) {
            super(itemView);
            score = (TextView) itemView.findViewById(R.id.adapterResults_Text_Score);
            date = (TextView) itemView.findViewById(R.id.adapterResults_Text_Date);
        }
    }


}
