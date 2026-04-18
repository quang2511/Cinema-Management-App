package com.example.cinema;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbarProfile;
    Button btnLogout;
    TextView btnHistory, btnChangePassword;
    CinemaDB cinemaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        cinemaDB = new CinemaDB(this);

        toolbarProfile = findViewById(R.id.toolbarProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnHistory = findViewById(R.id.btnHistory);
        btnChangePassword = findViewById(R.id.btnChangePassword);


        setSupportActionBar(toolbarProfile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarProfile.setNavigationOnClickListener(v -> finish());
        }


        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());


        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, TicketHistoryActivity.class);
            startActivity(intent);
        });
    }


    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtUser = view.findViewById(R.id.edtUsernameDialog);
        EditText edtOldPass = view.findViewById(R.id.edtOldPasswordDialog);
        EditText edtNewPass = view.findViewById(R.id.edtNewPasswordDialog);
        Button btnConfirm = view.findViewById(R.id.btnConfirmChangePass);

        btnConfirm.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String oldPass = edtOldPass.getText().toString().trim();
            String newPass = edtNewPass.getText().toString().trim();

            if (user.isEmpty() || oldPass.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = cinemaDB.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM NHANVIEN WHERE TENDN=? AND MATKHAU=?", new String[]{user, oldPass});

            if (cursor.getCount() > 0) {
                ContentValues values = new ContentValues();
                values.put("MATKHAU", newPass);
                db.update("NHANVIEN", values, "TENDN=?", new String[]{user});
                Toast.makeText(this, "🎉 Đổi mật khẩu thành công!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "❌ Sai Tên đăng nhập hoặc Mật khẩu cũ!", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

        dialog.show();
    }
}