package com.example.cinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CinemaActivity extends AppCompatActivity {

    Toolbar toolbarCinema;
    RecyclerView rvCinemas;
    TextView navHome, navProfile;
    CinemaAdapter adapter;
    ArrayList<Cinema> cinemaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        toolbarCinema = findViewById(R.id.toolbarCinema);
        rvCinemas = findViewById(R.id.rvCinemas);
        navHome = findViewById(R.id.navHome);
        navProfile = findViewById(R.id.navProfile);


        cinemaList = new ArrayList<>();
        cinemaList.add(new Cinema("Cinema UTC Hà Nội", "Số 3 Cầu Giấy, Láng Thượng, Đống Đa, Hà Nội"));
        cinemaList.add(new Cinema("Cinema Cầu Giấy", "241 Xuân Thủy, Dịch Vọng Hậu, Cầu Giấy, Hà Nội"));
        cinemaList.add(new Cinema("Cinema Hai Bà Trưng", "Tầng 5 Vincom Bà Triệu, Lê Đại Hành, Hà Nội"));
        cinemaList.add(new Cinema("Cinema Hà Đông", "Tầng 4 Mac Plaza, Trần Phú, Hà Đông, Hà Nội"));


        adapter = new CinemaAdapter(cinemaList);
        rvCinemas.setLayoutManager(new LinearLayoutManager(this));
        rvCinemas.setAdapter(adapter);


        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(CinemaActivity.this, UserHomeActivity.class);
            startActivity(intent);
            finish();
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(CinemaActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }


    class Cinema {
        String name, address;
        public Cinema(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }


    class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {
        ArrayList<Cinema> list;

        public CinemaAdapter(ArrayList<Cinema> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
            return new CinemaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
            Cinema cinema = list.get(position);
            holder.tvName.setText(cinema.name);
            holder.tvAddress.setText(cinema.address);


            View.OnClickListener chonRapListener = v -> {
                SharedPreferences sharedPreferences = getSharedPreferences("CinemaApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SELECTED_CINEMA", cinema.name);
                editor.apply();

                Toast.makeText(CinemaActivity.this, "Đã chọn rạp: " + cinema.name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CinemaActivity.this, UserHomeActivity.class);
                startActivity(intent);
                finish();
            };


            holder.btnSelect.setOnClickListener(chonRapListener);


            holder.itemView.setOnClickListener(chonRapListener);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class CinemaViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvAddress;
            Button btnSelect; // Khai báo thêm cái nút

            public CinemaViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvCinemaName);
                tvAddress = itemView.findViewById(R.id.tvCinemaAddress);
                btnSelect = itemView.findViewById(R.id.btnSelectCinema); // Ánh xạ cái nút
            }
        }
    }
}