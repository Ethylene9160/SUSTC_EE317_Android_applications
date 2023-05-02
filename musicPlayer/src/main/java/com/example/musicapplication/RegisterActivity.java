package com.example.musicapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicapplication.web.string_server.StringServer;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    private EditText name;
    private Button buttonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextPassword = findViewById(R.id.edit_text_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password);
        buttonRegister = findViewById(R.id.button_register);
        name = findViewById(R.id.edit_text_name);

        buttonRegister.setOnClickListener(view -> {
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            String username = name.getText().toString();


            if(username.length() > 10 || username.length() < 6) {
                Toast.makeText(this, "用户名长度应在6-10位之间", Toast.LENGTH_SHORT).show();
                return;
            }

            if(username.isEmpty()){
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if(username.contains("&") || username.contains("#")){
                Toast.makeText(this, "用户名不能包含&或#", Toast.LENGTH_SHORT).show();
                return;
            }

            // 检查密码是否一致
            if (!TextUtils.equals(password, confirmPassword)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            // 检查密码长度
            if (password.length() < 6 || password.length() >= 10) {
                Toast.makeText(this, "密码长度必须为6-10位", Toast.LENGTH_SHORT).show();
                return;
            }

            // 检查密码是否包含&或#
            if (password.contains("&") || password.contains("#")) {
                Toast.makeText(this, "密码不能包含&或#", Toast.LENGTH_SHORT).show();
                return;
            }


            // TODO: 完成注册逻辑
            MainActivity.controller.send(StringServer.SIGN_UP + username + "#"+password);
//            finish();
            //Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });
    }
}
