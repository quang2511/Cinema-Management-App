package com.example.cinema;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class TrailerActivity extends AppCompatActivity {

    VideoView videoView;
    ImageButton btnClose;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        videoView = findViewById(R.id.videoViewTrailer);
        btnClose = findViewById(R.id.btnCloseTrailer);
        loading = findViewById(R.id.loadingTrailer);


        String videoName = getIntent().getStringExtra("VIDEO_NAME");

        if (videoName != null) {
            int videoResId = getResources().getIdentifier(videoName, "raw", getPackageName());
            if (videoResId != 0) {
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
                videoView.setVideoURI(videoUri);

                videoView.setOnPreparedListener(mp -> {
                    loading.setVisibility(View.GONE);
                    videoView.start();
                });

                videoView.setOnCompletionListener(mp -> videoView.start());
            } else {
                loading.setVisibility(View.GONE); // Ẩn loading nếu không tìm thấy video
            }
        }

        btnClose.setOnClickListener(v -> finish());
    }
}