package com.example.cinema;

import android.app.AlertDialog; // Đã thêm import để làm khung thông báo
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // Đã thêm import để làm thông báo chữ nhỏ
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<Movie> list;

    public MovieAdapter(ArrayList<Movie> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = list.get(position);

        holder.tvName.setText(movie.getTitle());

        Context context = holder.itemView.getContext();
        int imageResId = context.getResources().getIdentifier(movie.getImage(), "drawable", context.getPackageName());
        if(imageResId != 0) {
            holder.imgPoster.setImageResource(imageResId);
        }


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);

            intent.putExtra("MA_PHIM", movie.getId());
            intent.putExtra("TEN_PHIM", movie.getTitle());
            intent.putExtra("HINH_ANH", movie.getImage());
            intent.putExtra("GIA_VE", movie.getPrice());
            intent.putExtra("THOI_LUONG", movie.getDuration());
            intent.putExtra("NGON_NGU", movie.getLanguage());
            intent.putExtra("MO_TA", movie.getSynopsis());
            context.startActivity(intent);
        });


        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Cảnh báo xóa phim")
                    .setMessage("Bạn có chắc chắn muốn xóa phim '" + movie.getTitle() + "' khỏi hệ thống không?")
                    .setPositiveButton("Xóa luôn", (dialog, which) -> {

                        CinemaDB db = new CinemaDB(context);
                        boolean isDeletedCompletely = db.xoaPhim(movie.getId());

                        if (isDeletedCompletely) {

                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, list.size());
                            Toast.makeText(context, "Đã xóa vĩnh viễn!", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(context, "Phim đã có vé bán ra! Đã tự động đổi trạng thái thành 'Ngừng chiếu'.", Toast.LENGTH_LONG).show();
                        }

                    })
                    .setNegativeButton("Hủy", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgMoviePoster);
            tvName = itemView.findViewById(R.id.tvMovieName);
        }
    }
}