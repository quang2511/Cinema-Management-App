package com.example.cinema;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private ArrayList<Seat> seatList;
    private OnSeatSelectedListener listener;

    public interface OnSeatSelectedListener {
        void onSeatSelected(Seat seat);
    }

    public SeatAdapter(ArrayList<Seat> seatList, OnSeatSelectedListener listener) {
        this.seatList = seatList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        holder.tvSeatName.setText(seat.getName());


        if (seat.getStatus() == 0) {
            holder.tvSeatName.setBackgroundColor(Color.parseColor("#EEEEEE"));
            holder.tvSeatName.setTextColor(Color.BLACK);
        } else if (seat.getStatus() == 1) {
            holder.tvSeatName.setBackgroundColor(Color.parseColor("#4CAF50"));
            holder.tvSeatName.setTextColor(Color.WHITE);
        } else { // Đã đặt
            holder.tvSeatName.setBackgroundColor(Color.parseColor("#9E9E9E"));
            holder.tvSeatName.setTextColor(Color.WHITE);
        }


        holder.itemView.setOnClickListener(v -> {
            if (seat.getStatus() == 2) return;

            if (seat.getStatus() == 0) {
                seat.setStatus(1);
            } else if (seat.getStatus() == 1) {
                seat.setStatus(0);
            }
            notifyItemChanged(position);
            listener.onSeatSelected(seat);
        });
    }

    @Override
    public int getItemCount() { return seatList.size(); }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeatName;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeatName = itemView.findViewById(R.id.tvSeatName);
        }
    }
}