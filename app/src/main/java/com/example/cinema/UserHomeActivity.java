package com.example.cinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {

    RecyclerView rvTrending, rvNowShowing, rvComingSoon;
    MovieAdapter trendingAdapter, nowShowingAdapter, comingSoonAdapter;
    ArrayList<Movie> trendingList, nowShowingList, comingSoonList;
    CinemaDB cinemaDB;


    TextView navProfile, navCinema, tvHeaderCinemaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        cinemaDB = new CinemaDB(this);
        trendingList = new ArrayList<>();
        nowShowingList = new ArrayList<>();
        comingSoonList = new ArrayList<>();

        rvTrending = findViewById(R.id.rvTrendingMovies);
        rvNowShowing = findViewById(R.id.rvNowShowingMovies);
        rvComingSoon = findViewById(R.id.rvComingSoonMovies);
        navProfile = findViewById(R.id.navProfile);
        navCinema = findViewById(R.id.navCinema);

        tvHeaderCinemaName = findViewById(R.id.tvHeaderCinemaName);


        capNhatTenRapTrenHeader();

        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvNowShowing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvComingSoon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadMoviesFromDB();

        trendingAdapter = new MovieAdapter(trendingList);
        nowShowingAdapter = new MovieAdapter(nowShowingList);
        comingSoonAdapter = new MovieAdapter(comingSoonList);

        rvTrending.setAdapter(trendingAdapter);
        rvNowShowing.setAdapter(nowShowingAdapter);
        rvComingSoon.setAdapter(comingSoonAdapter);

        navCinema.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomeActivity.this, CinemaActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }


    private void capNhatTenRapTrenHeader() {
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaApp", MODE_PRIVATE);

        String cinemaName = sharedPreferences.getString("SELECTED_CINEMA", "Cinema UTC Hà Nội");
        tvHeaderCinemaName.setText(cinemaName);
    }

    private void loadMoviesFromDB() {
        SQLiteDatabase db = cinemaDB.getReadableDatabase();

        String query1 = "SELECT MAPHIM, TENPHIM, HINHANH, GIAVE, THOILUONG, NGONNGU, MOTA FROM PHIM";
        Cursor c1 = db.rawQuery(query1, null);
        if (c1.moveToFirst()) {
            do {
                trendingList.add(new Movie(c1.getInt(0), c1.getString(1), c1.getString(2), c1.getDouble(3), c1.getString(4), c1.getString(5), c1.getString(6)));
            } while (c1.moveToNext());
        }
        c1.close();

        String query2 = "SELECT MAPHIM, TENPHIM, HINHANH, GIAVE, THOILUONG, NGONNGU, MOTA FROM PHIM WHERE TINHTRANG = 'Đang chiếu'";
        Cursor c2 = db.rawQuery(query2, null);
        if (c2.moveToFirst()) {
            do {
                nowShowingList.add(new Movie(c2.getInt(0), c2.getString(1), c2.getString(2), c2.getDouble(3), c2.getString(4), c2.getString(5), c2.getString(6)));
            } while (c2.moveToNext());
        }
        c2.close();

        String query3 = "SELECT MAPHIM, TENPHIM, HINHANH, GIAVE, THOILUONG, NGONNGU, MOTA FROM PHIM WHERE TINHTRANG = 'Sắp chiếu'";
        Cursor c3 = db.rawQuery(query3, null);
        if (c3.moveToFirst()) {
            do {
                comingSoonList.add(new Movie(c3.getInt(0), c3.getString(1), c3.getString(2), c3.getDouble(3), c3.getString(4), c3.getString(5), c3.getString(6)));
            } while (c3.moveToNext());
        }
        c3.close();
    }
}