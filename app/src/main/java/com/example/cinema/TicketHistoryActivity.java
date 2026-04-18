package com.example.cinema;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TicketHistoryActivity extends AppCompatActivity {

    Toolbar toolbarHistory;
    RecyclerView rvTicketHistory;
    TicketAdapter adapter;
    ArrayList<Ticket> ticketList;
    CinemaDB cinemaDB; // Khai báo Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);

        toolbarHistory = findViewById(R.id.toolbarHistory);
        rvTicketHistory = findViewById(R.id.rvTicketHistory);

        // Nút Back
        setSupportActionBar(toolbarHistory);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarHistory.setNavigationOnClickListener(v -> finish());
        }

        // Khởi tạo Database và Danh sách
        cinemaDB = new CinemaDB(this);
        ticketList = new ArrayList<>();

        // Cài đặt danh sách
        adapter = new TicketAdapter(ticketList);
        rvTicketHistory.setLayoutManager(new LinearLayoutManager(this));
        rvTicketHistory.setAdapter(adapter);
    }

    // 👇 CHÌA KHÓA Ở ĐÂY: Hàm này chạy mỗi khi mở màn hình, giúp tự động tải lại vé mới
    @Override
    protected void onResume() {
        super.onResume();
        loadRealHistoryData();
    }

    // Hàm moi móc dữ liệu từ Két sắt (Database)
    private void loadRealHistoryData() {
        ticketList.clear(); // Xóa sạch dữ liệu cũ trên màn hình

        // Lấy lịch sử của Khách hàng có MANV = 2 (Giống hệt lúc nãy mình code thanh toán)
        Cursor cursor = cinemaDB.getLichSuDatVe(2);

        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ 4 cột mà câu SQL JOIN trả về: MAHD, TENPHIM, NGAYDAT, TONGTIEN
                int maHD = cursor.getInt(0);
                String tenPhim = cursor.getString(1);
                String ngayDat = cursor.getString(2);
                double tongTien = cursor.getDouble(3);

                // Định dạng tiền tệ
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                String tienDep = formatter.format(tongTien) + " VNĐ";

                // Nhét vào danh sách vé (Mượn chỗ Ghế để hiển thị Mã Hóa Đơn cho ngầu)
                ticketList.add(new Ticket(tenPhim, ngayDat, "Hệ thống", "Mã HĐ: #" + maHD, tienDep));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Báo cho Adapter biết là đã có dữ liệu mới, hãy vẽ lại giao diện đi!
        adapter.notifyDataSetChanged();
    }

    // ==========================================
    // CLASS CHỨA DỮ LIỆU 1 CÁI VÉ
    // ==========================================
    class Ticket {
        String movieName, date, payment, seats, total;

        public Ticket(String movieName, String date, String payment, String seats, String total) {
            this.movieName = movieName;
            this.date = date;
            this.payment = payment;
            this.seats = seats;
            this.total = total;
        }
    }

    // ==========================================
    // ADAPTER ĐỂ ĐỔ DỮ LIỆU LÊN GIAO DIỆN
    // ==========================================
    class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
        ArrayList<Ticket> list;

        public TicketAdapter(ArrayList<Ticket> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_history, parent, false);
            return new TicketViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
            Ticket ticket = list.get(position);
            holder.tvMovie.setText(ticket.movieName);
            holder.tvDate.setText("Thời gian: " + ticket.date);
            // Hiện mã hóa đơn thay cho ghế
            holder.tvSeats.setText(ticket.seats + " | TT qua: " + ticket.payment);
            holder.tvTotal.setText("Tổng tiền: " + ticket.total);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TicketViewHolder extends RecyclerView.ViewHolder {
            TextView tvMovie, tvDate, tvSeats, tvTotal;
            public TicketViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMovie = itemView.findViewById(R.id.tvTicketMovie);
                tvDate = itemView.findViewById(R.id.tvTicketDate);
                tvSeats = itemView.findViewById(R.id.tvTicketSeats);
                tvTotal = itemView.findViewById(R.id.tvTicketTotal);
            }
        }
    }
}