package com.example.cinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbarPayment;
    TextView tvPayMovie, tvPaySeats, tvPayTotal;
    RadioGroup rgPaymentMethod;
    Button btnConfirmPayment;


    int maPhim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        anhXa();


        Intent intent = getIntent();
        String movie = intent.getStringExtra("TEN_PHIM");


        maPhim = intent.getIntExtra("MA_PHIM", 1);

        String seats = intent.getStringExtra("GHE_DA_CHON");
        double total = intent.getDoubleExtra("TONG_TIEN", 0);


        SharedPreferences sharedPreferences = getSharedPreferences("CinemaApp", MODE_PRIVATE);
        String cinemaName = sharedPreferences.getString("SELECTED_CINEMA", "Cinema UTC Hà Nội");

        setSupportActionBar(toolbarPayment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Thanh toán");
            toolbarPayment.setNavigationOnClickListener(v -> finish());
        }


        tvPayMovie.setText("Phim: " + movie + "\nRạp chiếu: " + cinemaName);
        tvPaySeats.setText("Ghế đang chọn: " + seats);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvPayTotal.setText("Tổng tiền: " + formatter.format(total) + " VNĐ");

        btnConfirmPayment.setOnClickListener(v -> {
            int selectedId = rgPaymentMethod.getCheckedRadioButtonId();
            RadioButton rbSelected = findViewById(selectedId);
            String method = rbSelected.getText().toString();


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = sdf.format(new Date());


            CinemaDB db = new CinemaDB(PaymentActivity.this);


            boolean isSuccess = db.datVeSuccess(2, maPhim, currentDate, total);

            if (isSuccess) {
                Toast.makeText(this, "🎉 Đặt vé tại " + cinemaName + " thành công!", Toast.LENGTH_LONG).show();


                Intent homeIntent = new Intent(PaymentActivity.this, UserHomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                finish();
            } else {
                Toast.makeText(this, "Lỗi! Chưa lưu được doanh thu vào hệ thống.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        toolbarPayment = findViewById(R.id.toolbarPayment);
        tvPayMovie = findViewById(R.id.tvPayMovie);
        tvPaySeats = findViewById(R.id.tvPaySeats);
        tvPayTotal = findViewById(R.id.tvPayTotal);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);
    }
}