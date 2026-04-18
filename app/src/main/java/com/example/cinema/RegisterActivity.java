package com.example.cinema;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtConfirmPassword;
    Button btnRegister;
    TextView tvGoToLogin;


    CinemaDB cinemaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edtUsername = findViewById(R.id.edtRegUsername);
        edtPassword = findViewById(R.id.edtRegPassword);
        edtConfirmPassword = findViewById(R.id.edtRegConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);


        cinemaDB = new CinemaDB(this);


        btnRegister.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            String confirmPass = edtConfirmPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirmPass)) {
                Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }


            SQLiteDatabase db = cinemaDB.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("TENDN", user);
            values.put("MATKHAU", pass);
            values.put("MAQUYEN", 2);


            long result = db.insert("NHANVIEN", null, values);

            if (result == -1) {

                Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Nút quay lại
        tvGoToLogin.setOnClickListener(v -> finish());
    }
}