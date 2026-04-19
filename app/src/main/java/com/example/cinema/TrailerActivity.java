package com.example.cinema;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class TrailerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String videoName = getIntent().getStringExtra("VIDEO_NAME");


        String youtubeUrl = "https://www.youtube.com/watch?v=odM92ap8_c0";

        if (videoName != null) {
            switch (videoName.toLowerCase()) {
                case "panda":
                    youtubeUrl = "https://www.youtube.com/watch?v=_inKs4eeHiI";
                    break;
                case "godzilla":
                    youtubeUrl = "https://www.youtube.com/watch?v=lV1OOlGwExM";
                    break;
                case "mai":
                    youtubeUrl = "https://www.youtube.com/watch?v=1b-Vd2K7bA8";
                    break;
                case "latmat":
                    youtubeUrl = "https://www.youtube.com/watch?v=uK1XWMBX_A0";
                    break;
                case "dune":
                    youtubeUrl = "https://www.youtube.com/watch?v=Way9Dexny3w";
                    break;
                case "deadpool":
                    youtubeUrl = "https://www.youtube.com/watch?v=73_1biukkMQ";
                    break;
                case "exhuma":
                    youtubeUrl = "https://www.youtube.com/watch?v=pY5m_vRkHqI";
                    break;
                case "insideout":
                    youtubeUrl = "https://www.youtube.com/watch?v=LEjhY15eCx0";
                    break;
                case "dao":
                    youtubeUrl = "https://www.youtube.com/watch?v=7A64b5w5WxE";
                    break;
            }
        }


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
        startActivity(intent);


        finish();
    }
}