package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SeatSelectionActivity extends AppCompatActivity {

    Toolbar toolbarSeat;
    RecyclerView rvSeats;
    TextView tvTotalInfo;
    Button btnContinuePayment;

    SeatAdapter seatAdapter;
    ArrayList<Seat> seatList;
    ArrayList<Seat> selectedSeats = new ArrayList<>();

    String movieTitle;
    double ticketPrice;


    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        anhXa();


        Intent intent = getIntent();
        movieTitle = intent.getStringExtra("TEN_PHIM");
        ticketPrice = intent.getDoubleExtra("GIA_VE", 0);


        movieId = intent.getIntExtra("MA_PHIM", 1);


        setSupportActionBar(toolbarSeat);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movieTitle);
            toolbarSeat.setNavigationOnClickListener(v -> finish());
        }

        setupSeats();


        btnContinuePayment.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 ghế!", Toast.LENGTH_SHORT).show();
            } else {

                StringBuilder seatsText = new StringBuilder();
                for (Seat s : selectedSeats) {
                    seatsText.append(s.getName()).append(", ");
                }
                if (seatsText.length() > 0) seatsText.setLength(seatsText.length() - 2);

                double total = selectedSeats.size() * ticketPrice;


                Intent paymentIntent = new Intent(SeatSelectionActivity.this, PaymentActivity.class);
                paymentIntent.putExtra("TEN_PHIM", movieTitle);
                paymentIntent.putExtra("GHE_DA_CHON", seatsText.toString());
                paymentIntent.putExtra("TONG_TIEN", total);


                paymentIntent.putExtra("MA_PHIM", movieId);

                startActivity(paymentIntent);
            }
        });
    }

    private void anhXa() {
        toolbarSeat = findViewById(R.id.toolbarSeat);
        rvSeats = findViewById(R.id.rvSeats);
        tvTotalInfo = findViewById(R.id.tvTotalInfo);
        btnContinuePayment = findViewById(R.id.btnContinuePayment);
    }

    private void setupSeats() {
        seatList = new ArrayList<>();

        String[] rows = {"A", "B", "C", "D", "E", "F"};
        for (String row : rows) {
            for (int i = 1; i <= 5; i++) {
                int status = 0; // Trống

                if ((row.equals("C") && i == 3) || (row.equals("D") && i == 4)) {
                    status = 2;
                }
                seatList.add(new Seat(row + i, status));
            }
        }

        seatAdapter = new SeatAdapter(seatList, seat -> {
            if (seat.getStatus() == 1) {
                selectedSeats.add(seat);
            } else {
                selectedSeats.remove(seat);
            }
            updateTotalInfo();
        });


        rvSeats.setLayoutManager(new GridLayoutManager(this, 5));
        rvSeats.setAdapter(seatAdapter);
    }

    private void updateTotalInfo() {
        StringBuilder seatsText = new StringBuilder();
        for (Seat s : selectedSeats) {
            seatsText.append(s.getName()).append(", ");
        }
        if (seatsText.length() > 0) seatsText.setLength(seatsText.length() - 2); // Xóa dấu phẩy dư

        double total = selectedSeats.size() * ticketPrice;
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        tvTotalInfo.setText("Ghế đang chọn: " + (seatsText.length() == 0 ? "Chưa có" : seatsText.toString()) +
                "\nTổng tiền: " + formatter.format(total) + " VNĐ");
    }
}