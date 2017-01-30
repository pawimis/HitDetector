package com.example.root.hitdetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


public class ResultsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
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
        button = (Button) findViewById(R.id.resultsActivity_button_clear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                dbHelper.deleteAll();
                recyclerView.invalidate();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
