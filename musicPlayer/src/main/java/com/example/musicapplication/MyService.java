package com.example.musicapplication;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class MyService extends Service {

    private AlertDialog alertDialog;
    public static String is = "";
    @Override
    public void onCreate() {
        super.onCreate();
        showAlertDialog(is);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showAlertDialog(String is) {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("注册成功！")
                .setMessage("你的id为："+is)
                .setCancelable(true)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确认按钮后的逻辑处理
//                        finish();
                        dialog.dismiss();
                        // ...
                    }
                })
                .create();
        alertDialog.show();
    }
}
