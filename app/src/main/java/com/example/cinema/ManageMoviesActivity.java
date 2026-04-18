package com.example.cinema;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ManageMoviesActivity extends AppCompatActivity {

    ImageButton btnBack;
    RecyclerView rvManageMovies;
    CinemaDB cinemaDB;
    ManageMovieAdapter adapter;
    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_movies);

        btnBack = findViewById(R.id.btnBackManage);
        rvManageMovies = findViewById(R.id.rvManageMovies);
        cinemaDB = new CinemaDB(this);


        btnBack.setOnClickListener(v -> finish());


        rvManageMovies.setLayoutManager(new LinearLayoutManager(this));

        loadDanhSachPhim();
    }


    private void loadDanhSachPhim() {
        movieList = cinemaDB.getAllMovies();
        adapter = new ManageMovieAdapter(this, movieList);
        rvManageMovies.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadDanhSachPhim();
    }
}