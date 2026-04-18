package com.example.cinema;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddMovieActivity extends AppCompatActivity {

    EditText edtTenPhim, edtGiaVe, edtThoiLuong, edtNgonNgu, edtMoTa;
    Button btnLuuPhim, btnHuyThemPhim;
    CinemaDB cinemaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);


        edtTenPhim = findViewById(R.id.edtTenPhim);
        edtGiaVe = findViewById(R.id.edtGiaVe);
        edtThoiLuong = findViewById(R.id.edtThoiLuong);
        edtNgonNgu = findViewById(R.id.edtNgonNgu);
        edtMoTa = findViewById(R.id.edtMoTa);
        btnLuuPhim = findViewById(R.id.btnLuuPhim);
        btnHuyThemPhim = findViewById(R.id.btnHuyThemPhim);

        cinemaDB = new CinemaDB(this);


        btnHuyThemPhim.setOnClickListener(v -> finish());


        btnLuuPhim.setOnClickListener(v -> {
            String ten = edtTenPhim.getText().toString().trim();
            String giaStr = edtGiaVe.getText().toString().trim();
            String thoiLuong = edtThoiLuong.getText().toString().trim();
            String ngonNgu = edtNgonNgu.getText().toString().trim();
            String moTa = edtMoTa.getText().toString().trim();


            if (ten.isEmpty() || giaStr.isEmpty() || thoiLuong.isEmpty() || ngonNgu.isEmpty() || moTa.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            double gia = Double.parseDouble(giaStr);


            boolean isSuccess = cinemaDB.themPhim(ten, gia, thoiLuong, ngonNgu, moTa);
            if (isSuccess) {
                Toast.makeText(this, "🎉 Thêm phim thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Lỗi! Không thể lưu phim.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}