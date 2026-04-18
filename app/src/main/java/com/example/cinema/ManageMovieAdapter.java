package com.example.cinema;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ManageMovieAdapter extends RecyclerView.Adapter<ManageMovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> list;
    private CinemaDB db;

    public ManageMovieAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
        this.db = new CinemaDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_manage_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Movie m = list.get(position);
        h.tvTitle.setText(m.getTitle());
        h.tvPrice.setText(new DecimalFormat("#,###").format(m.getPrice()) + " VNĐ");


        int resId = context.getResources().getIdentifier(m.getImage(), "drawable", context.getPackageName());
        h.img.setImageResource(resId != 0 ? resId : android.R.drawable.ic_menu_gallery);


        h.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa phim '" + m.getTitle() + "'?")
                    .setPositiveButton("Xóa luôn", (d, w) -> {
                        if (db.xoaPhim(m.getId())) {
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa vĩnh viễn!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Phim đã bán vé! Chỉ chuyển sang Ngừng chiếu.", Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("Hủy", null).show();
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTitle, tvPrice;
        ImageButton btnDelete;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgManagePoster);
            tvTitle = v.findViewById(R.id.tvManageTitle);
            tvPrice = v.findViewById(R.id.tvManagePrice);
            btnDelete = v.findViewById(R.id.btnDeleteMovie);
        }
    }
}