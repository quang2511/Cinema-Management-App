package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class HomeActivity extends AppCompatActivity {

    TextView tvRevenue, tvCustomerCount;
    Button btnLogoutAdmin;
    CinemaDB cinemaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvRevenue = findViewById(R.id.tvRevenue);
        tvCustomerCount = findViewById(R.id.tvCustomerCount);
        btnLogoutAdmin = findViewById(R.id.btnLogoutAdmin);


        View cardLuotKhach = findViewById(R.id.cardLuotKhach);

        // Khởi tạo DB
        cinemaDB = new CinemaDB(this);

        loadDashboardData();

        btnLogoutAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


        cardLuotKhach.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CustomerListActivity.class);
            startActivity(intent);
        });
        Button btnMoTrangThemPhim = findViewById(R.id.btnMoTrangThemPhim);
        btnMoTrangThemPhim.setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, AddMovieActivity.class));
        });
        // Ánh xạ
        Button btnMoTrangQuanLyPhim = findViewById(R.id.btnMoTrangQuanLyPhim);

        // Bắt sự kiện chuyển trang
        btnMoTrangQuanLyPhim.setOnClickListener(v -> {
            startActivity(new android.content.Intent(HomeActivity.this, ManageMoviesActivity.class));
        });
    }

    private void loadDashboardData() {
        double realRevenue = cinemaDB.getTotalRevenue();
        int realCustomers = cinemaDB.getCustomerCount();

        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedRevenue = formatter.format(realRevenue) + " VNĐ";

        tvRevenue.setText(formattedRevenue);
        tvCustomerCount.setText(String.valueOf(realCustomers));
    }

    public void xemChiTiet(View view) {
        startActivity(new Intent(HomeActivity.this, RevenueDetailActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDashboardData();
    }
}