package com.example.cinema;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton; // Đảm bảo đã import ImageButton
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RevenueDetailActivity extends AppCompatActivity {


    ImageButton btnBack;
    ListView lvRevenue;
    CinemaDB cinemaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_detail);


        btnBack = findViewById(R.id.btnBackAdmin);
        lvRevenue = findViewById(R.id.lvRevenue);

        cinemaDB = new CinemaDB(this);


        ArrayList<String> dataList = cinemaDB.getRevenueDetails();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvRevenue.setAdapter(adapter);


        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}