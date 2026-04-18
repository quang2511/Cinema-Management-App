package com.example.cinema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class CinemaDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuanLyRapPhim.db";
    private static final int DATABASE_VERSION = 7;

    public CinemaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE QUYEN (MAQUYEN INTEGER PRIMARY KEY AUTOINCREMENT, TENQUYEN TEXT)");
        db.execSQL("CREATE TABLE NHANVIEN (MANV INTEGER PRIMARY KEY AUTOINCREMENT, HOTENNV TEXT, TENDN TEXT UNIQUE NOT NULL, MATKHAU TEXT NOT NULL, EMAIL TEXT, SDT TEXT, GIOITINH TEXT, NGAYSINH TEXT, MAQUYEN INTEGER REFERENCES QUYEN(MAQUYEN))");
        db.execSQL("CREATE TABLE THELOAI (MATHELOAI INTEGER PRIMARY KEY AUTOINCREMENT, TENTHELOAI TEXT, HINHANH BLOB)");
        db.execSQL("CREATE TABLE PHIM (MAPHIM INTEGER PRIMARY KEY AUTOINCREMENT, TENPHIM TEXT NOT NULL, GIAVE REAL, TINHTRANG TEXT, HINHANH TEXT, THOILUONG TEXT, NGONNGU TEXT, MOTA TEXT, MATHELOAI INTEGER REFERENCES THELOAI(MATHELOAI))");
        db.execSQL("CREATE TABLE GHE (MAGHE INTEGER PRIMARY KEY AUTOINCREMENT, TENGHE TEXT, TINHTRANG TEXT)");
        db.execSQL("CREATE TABLE HOADON (MAHD INTEGER PRIMARY KEY AUTOINCREMENT, MAGHE INTEGER REFERENCES GHE(MAGHE), MANV INTEGER REFERENCES NHANVIEN(MANV), NGAYDAT TEXT, TONGTIEN REAL, TINHTRANG TEXT)");
        db.execSQL("CREATE TABLE CHITIETHOADON (MAHD INTEGER REFERENCES HOADON(MAHD), MAPHIM INTEGER REFERENCES PHIM(MAPHIM), SOLUONG INTEGER CHECK(SOLUONG > 0), PRIMARY KEY(MAHD, MAPHIM))");

        // Chèn dữ liệu danh mục
        db.execSQL("INSERT INTO QUYEN (TENQUYEN) VALUES ('Quản lý'), ('Khách hàng')");
        db.execSQL("INSERT INTO NHANVIEN (HOTENNV, TENDN, MATKHAU, MAQUYEN) VALUES ('Quang Admin', 'admin', '123456', 1)");
        db.execSQL("INSERT INTO NHANVIEN (HOTENNV, TENDN, MATKHAU, MAQUYEN) VALUES ('Khách vãng lai', 'khach', '123456', 2)");


        db.execSQL("INSERT INTO PHIM (TENPHIM, GIAVE, TINHTRANG, HINHANH, THOILUONG, NGONNGU, MOTA, MATHELOAI) VALUES " +
                "('Dune: Part Two', 120000, 'Đang chiếu', 'poster_dune', '166 phút', 'Phụ đề Việt', 'Paul Atreides hợp nhất cùng Chani và người Fremen để trả thù...', 1), " +
                "('Mai', 90000, 'Đang chiếu', 'poster_mai', '131 phút', 'Tiếng Việt', 'Cuộc đời nhiều thăng trầm của Mai - người phụ nữ làm nghề mát-xa...', 2), " +
                "('Godzilla x Kong', 110000, 'Đang chiếu', 'poster_godzilla', '115 phút', 'Phụ đề Việt', 'Hai siêu quái thú phải hợp sức chống lại kẻ thù giấu mặt...', 1), " +
                "('Exhuma: Quật Mộ', 100000, 'Đang chiếu', 'poster_exhuma', '134 phút', 'Phụ đề Việt', 'Hai pháp sư quật một ngôi mộ cổ lên và tai họa ập đến...', 4), " +
                "('Đào, Phở và Piano', 50000, 'Đang chiếu', 'poster_dao', '100 phút', 'Tiếng Việt', 'Cuộc chiến đấu anh dũng bảo vệ thủ đô Hà Nội...', 2), " +
                "('Kung Fu Panda 4', 100000, 'Sắp chiếu', 'poster_panda', '94 phút', 'Lồng tiếng', 'Gấu Po phải đối mặt với kẻ thù có khả năng sao chép chiêu thức...', 3), " +
                "('Lật Mặt 7', 95000, 'Sắp chiếu', 'poster_latmat', '120 phút', 'Tiếng Việt', 'Câu chuyện cảm động về tình mẫu tử thiêng liêng...', 2), " +
                "('Inside Out 2', 105000, 'Sắp chiếu', 'poster_insideout', '100 phút', 'Lồng tiếng', 'Riley bước vào tuổi dậy thì với những cảm xúc mới phức tạp...', 3), " +
                "('Deadpool & Wolverine', 130000, 'Sắp chiếu', 'poster_deadpool', '127 phút', 'Phụ đề Việt', 'Hai người hùng bất đắc dĩ bắt tay nhau cứu dòng thời gian...', 1)");

        db.execSQL("INSERT INTO GHE (TENGHE, TINHTRANG) VALUES ('A1', 'Trống'), ('A2', 'Trống'), ('VIP1', 'Trống')");

        // Chèn hóa đơn mẫu để test doanh thu
        db.execSQL("INSERT INTO HOADON (MANV, NGAYDAT, TONGTIEN, TINHTRANG) VALUES (2, '15/04/2026', 150000, 'Đã thanh toán')");
        db.execSQL("INSERT INTO HOADON (MANV, NGAYDAT, TONGTIEN, TINHTRANG) VALUES (2, '15/04/2026', 240000, 'Đã thanh toán')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CHITIETHOADON");
        db.execSQL("DROP TABLE IF EXISTS HOADON");
        db.execSQL("DROP TABLE IF EXISTS GHE");
        db.execSQL("DROP TABLE IF EXISTS PHIM");
        db.execSQL("DROP TABLE IF EXISTS THELOAI");
        db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
        db.execSQL("DROP TABLE IF EXISTS QUYEN");
        onCreate(db);
    }

    // --- CÁC HÀM CHO ADMIN ---

    public double getTotalRevenue() {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(TONGTIEN) FROM HOADON", null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public int getCustomerCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM HOADON", null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public ArrayList<String> getRevenueDetails() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAHD, NGAYDAT, TONGTIEN FROM HOADON ORDER BY MAHD DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                double total = cursor.getDouble(2);

                java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,###");
                String formattedTotal = formatter.format(total);

                list.add("🎟 Hóa đơn #" + id + "  |  Ngày: " + date + "\n💰 Doanh thu: " + formattedTotal + " VNĐ");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public boolean datVeSuccess(int maNV, int maPhim, String ngayDat, double tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {

            ContentValues hdValues = new ContentValues();
            hdValues.put("MANV", maNV);
            hdValues.put("NGAYDAT", ngayDat);
            hdValues.put("TONGTIEN", tongTien);
            hdValues.put("TINHTRANG", "Đã thanh toán");

            long hdId = db.insert("HOADON", null, hdValues);
            if (hdId == -1) return false;


            ContentValues ctValues = new ContentValues();
            ctValues.put("MAHD", hdId);
            ctValues.put("MAPHIM", maPhim);
            ctValues.put("SOLUONG", 1);

            long ctId = db.insert("CHITIETHOADON", null, ctValues);
            if (ctId == -1) return false;

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {

            android.util.Log.d("SQL_ERROR", "Lỗi lưu vé: " + e.getMessage());
            return false;
        } finally {
            db.endTransaction();
        }
    }


    public Cursor getLichSuDatVe(int maNV) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT h.MAHD, p.TENPHIM, h.NGAYDAT, h.TONGTIEN " +
                "FROM HOADON h " +
                "JOIN CHITIETHOADON ct ON h.MAHD = ct.MAHD " +
                "JOIN PHIM p ON ct.MAPHIM = p.MAPHIM " +
                "WHERE h.MANV = ? " +
                "ORDER BY h.MAHD DESC";
        return db.rawQuery(sql, new String[]{String.valueOf(maNV)});
    }

    public java.util.ArrayList<String> getDanhSachLuotKhach() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();


        String sql = "SELECT h.MAHD, nv.HOTENNV, h.NGAYDAT " +
                "FROM HOADON h " +
                "JOIN NHANVIEN nv ON h.MANV = nv.MANV " +
                "ORDER BY h.MAHD DESC";

        android.database.Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int maHD = cursor.getInt(0);
                String tenKhach = cursor.getString(1);
                String ngay = cursor.getString(2);

                list.add("👤 Khách hàng: " + tenKhach + "\n🎟 Hóa đơn #" + maHD + "  |  Ngày: " + ngay);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean themPhim(String tenPhim, double giaVe, String thoiLuong, String ngonNgu, String moTa) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();

        values.put("TENPHIM", tenPhim);
        values.put("GIAVE", giaVe);
        values.put("TINHTRANG", "Đang chiếu");
        values.put("HINHANH", "poster_dune");
        values.put("THOILUONG", thoiLuong);
        values.put("NGONNGU", ngonNgu);
        values.put("MOTA", moTa);
        values.put("MATHELOAI", 1);

        long result = db.insert("PHIM", null, values);
        return result != -1; // Nếu insert thành công sẽ trả về true, lỗi thì false
    }

    public boolean xoaPhim(int maPhim) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();


        android.database.Cursor cursor = db.rawQuery("SELECT * FROM CHITIETHOADON WHERE MAPHIM = ?", new String[]{String.valueOf(maPhim)});

        if (cursor.getCount() > 0) {
            cursor.close();

            android.content.ContentValues values = new android.content.ContentValues();
            values.put("TINHTRANG", "Ngừng chiếu");
            db.update("PHIM", values, "MAPHIM = ?", new String[]{String.valueOf(maPhim)});
            return false; // Trả về false để báo cho bên ngoài biết là "Xóa không đứt, chỉ ẩn đi"
        }
        cursor.close();


        int result = db.delete("PHIM", "MAPHIM = ?", new String[]{String.valueOf(maPhim)});
        return result > 0;
    }
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PHIM", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Movie(
                        cursor.getInt(0),    // id
                        cursor.getString(1), // title
                        cursor.getString(4), // image
                        cursor.getDouble(2), // price
                        cursor.getString(5), // duration
                        cursor.getString(6), // language
                        cursor.getString(7)  // synopsis
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
