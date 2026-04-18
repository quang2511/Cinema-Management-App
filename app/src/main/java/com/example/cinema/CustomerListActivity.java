package com.example.cinema;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity {

    ImageButton btnBack;
    ListView lvCustomerList;
    CinemaDB cinemaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        btnBack = findViewById(R.id.btnBackCustomerList);
        lvCustomerList = findViewById(R.id.lvCustomerList);
        cinemaDB = new CinemaDB(this);


        ArrayList<String> dataList = cinemaDB.getDanhSachLuotKhach();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvCustomerList.setAdapter(adapter);


        btnBack.setOnClickListener(v -> finish());
    }
}