package com.example.cinema;

import android.content.Intent; // Đã thêm import Intent
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; // Đã thêm import TextView
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    CinemaDB cinemaDB;
    TextView tvGoToRegister; // Khai báo dòng chữ Tạo tài khoản

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);


        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        cinemaDB = new CinemaDB(this);


        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    int role = checkLogin(user, pass);


                    if (role == 1) {
                        Toast.makeText(MainActivity.this, "Xin chào Quản lý!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    } else if (role == 2) {
                        Toast.makeText(MainActivity.this, "Xin chào Khách hàng!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, UserHomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private int checkLogin(String username, String password) {
        SQLiteDatabase db = cinemaDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAQUYEN FROM NHANVIEN WHERE TENDN = ? AND MATKHAU = ?", new String[]{username, password});

        int role = -1;
        if (cursor.moveToFirst()) {
            role = cursor.getInt(0); // Lấy cột MAQUYEN ra
        }
        cursor.close();
        return role;
    }
}