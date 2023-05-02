package com.example.musicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicapplication.web.string_server.StringServer;

// LoginActivity.java
public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextAccount;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextAccount = findViewById(R.id.edit_text_account);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mButtonLogin = findViewById(R.id.button_login);
        mButtonRegister = findViewById(R.id.button_register);

        mButtonLogin.setOnClickListener(v->toLogin());

        mButtonRegister.setOnClickListener(v->toRegister());
    }

    private void toRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void toLogin(){
        if(MainActivity.isRegisted()) {
            Toast.makeText(getApplicationContext(), "您已登录。", Toast.LENGTH_SHORT).show();
        }else{
            String account = mEditTextAccount.getText().toString();
            String password = mEditTextPassword.getText().toString();
            MainActivity.controller.send(StringServer.LOG_IN + "#" + account + "#" + password);
        }
    }
}
