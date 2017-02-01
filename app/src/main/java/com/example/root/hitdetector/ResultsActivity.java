package com.example.root.hitdetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ResultsActivityRecycler adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        adapter = new ResultsActivityRecycler(this,dbHelper.getAllContacts());
        recyclerView = (RecyclerView) findViewById(R.id.resultsActivity_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
