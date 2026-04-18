package com.example.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView imgDetailPoster;
    TextView tvDetailName, tvDetailInfo, tvDetailSynopsis;
    Button btnDetailTrailer, btnDetailBookTicket, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        imgDetailPoster = findViewById(R.id.imgDetailPoster);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailInfo = findViewById(R.id.tvDetailInfo);
        tvDetailSynopsis = findViewById(R.id.tvDetailSynopsis);
        btnDetailTrailer = findViewById(R.id.btnDetailTrailer);
        btnDetailBookTicket = findViewById(R.id.btnDetailBookTicket);
        btnBack = findViewById(R.id.btnBack);


        Intent intent = getIntent();


        int movieId = intent.getIntExtra("MA_PHIM", 1);

        String movieName = intent.getStringExtra("TEN_PHIM");
        String imageName = intent.getStringExtra("HINH_ANH");
        double price = intent.getDoubleExtra("GIA_VE", 0);
        String duration = intent.getStringExtra("THOI_LUONG");
        String language = intent.getStringExtra("NGON_NGU");
        String synopsis = intent.getStringExtra("MO_TA");

        // 3. In dữ liệu lên màn hình
        tvDetailName.setText(movieName);
        tvDetailInfo.setText("⏱ Thời lượng: " + duration + "\n🗣 Ngôn ngữ: " + language);
        tvDetailSynopsis.setText(synopsis);

        if (imageName != null) {
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (imageResId != 0) {
                imgDetailPoster.setImageResource(imageResId);
            }
        }


        btnBack.setOnClickListener(v -> finish());


        btnDetailTrailer.setOnClickListener(v -> {
            Intent trailerIntent = new Intent(MovieDetailActivity.this, TrailerActivity.class);
            String videoFileName = imageName;
            if (videoFileName != null && videoFileName.startsWith("poster_")) {
                videoFileName = videoFileName.replace("poster_", "");
            }
            trailerIntent.putExtra("VIDEO_NAME", videoFileName);
            startActivity(trailerIntent);
        });


        btnDetailBookTicket.setOnClickListener(v -> {
            Intent seatIntent = new Intent(MovieDetailActivity.this, SeatSelectionActivity.class);
            seatIntent.putExtra("TEN_PHIM", movieName);
            seatIntent.putExtra("GIA_VE", price);


            seatIntent.putExtra("MA_PHIM", movieId);

            startActivity(seatIntent);
        });
    }
}