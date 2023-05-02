package com.example.musicapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicapplication.web.string_server.StringServer;

public class UserActivity extends AppCompatActivity {

    private Button changePasswardButton, changeNameButton, msgButton;
    private Button logoutButton;
    private TextView tv_name, tv_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        changePasswardButton = findViewById(R.id.change_password_button);
        logoutButton = findViewById(R.id.logout_button);
        changeNameButton = findViewById(R.id.change_name_button);
        msgButton = findViewById(R.id.message_button);

        tv_name = findViewById(R.id.name);
        tv_id = findViewById(R.id.id);
        tv_name.append(MainActivity.user.getName());
        tv_id.append(MainActivity.user.getId());
        changePasswardButton.setOnClickListener(v->changePassward());
        logoutButton.setOnClickListener(v->logout());
        changeNameButton.setOnClickListener(v->changeName());
        msgButton.setOnClickListener(v-> contact());


    }

    /**
     * 点击”发送“按钮后的事件
     */
    private void contact() {
        // 创建一个 AlertDialog.Builder 对象
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("发送消息");

        // 设置弹窗的布局
        LinearLayout layout = new LinearLayout(UserActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // 第一行输入框，输入对方ID
        final EditText toIDEditText = new EditText(UserActivity.this);
        toIDEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        toIDEditText.setHint("对方ID");
        layout.addView(toIDEditText);

        // 第二行输入框，输入消息内容
        final EditText contentEditText = new EditText(UserActivity.this);
        contentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        contentEditText.setHint("消息内容");
        layout.addView(contentEditText);

        builder.setView(layout);

        // 添加发送按钮
        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String toID = toIDEditText.getText().toString();
                String content = contentEditText.getText().toString();
                // 在这里执行发送消息的逻辑
                MainActivity.controller.send(StringServer.CONTACT + toID + "#" + content);
            }
        });

        // 添加取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // 显示弹窗
        builder.show();
    }

    /**
     * 改密码事件
     */
    private void changePassward(){
        // 弹出弹框
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("修改密码");
        EditText editText = new EditText(UserActivity.this);
        editText.setTransformationMethod(new PasswordTransformationMethod());
        builder.setView(editText);

        // 添加确认按钮
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取输入的新密码
                String newPassword = editText.getText().toString().trim();
                // 检查新密码是否符合要求
                if (checkNewPassword(newPassword)) {
                    // TODO: 更新密码
                    MainActivity.controller.send(StringServer.CHANGE_PASSWARD + newPassword);
                } else {
                    // 提示用户新密码不符合要求
                    runOnUiThread(() ->Toast.makeText(UserActivity.this, "新密码不符合要求！", Toast.LENGTH_SHORT).show());
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }


    /**检查密码长度、内容是否符合要求。
     * 这个方法同时用来检查username是否符合标准，因为放假了所以没有专门写针对username的检查方法。
     * @param newPassword 新密码（新用户名）
     * @return 是否合法
     */
    private boolean checkNewPassword(String newPassword) {
        // 检查新密码长度是否符合要求
        if (newPassword.length() < 6 || newPassword.length() > 10) {
            return false;
        }
        // 检查新密码是否包含非法字符
        if (newPassword.contains("&") || newPassword.contains("#")) {
            return false;
        }
        return true;
    }

    /**登出
     *
     */
    private void logout(){
        MainActivity.controller.send(String.valueOf(StringServer.LOG_OUT));
        finish();
    }

    /**改用户名
     *
     */
    private void changeName(){
        // 弹出弹框
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("修改用户名");
        EditText editText = new EditText(UserActivity.this);
        editText.setHint("用户名长度需要在6-10位，不含&或#");
        builder.setView(editText);

        // 添加确认按钮
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取输入的新用户名
                String newPassword = editText.getText().toString().trim();
                // 检查新用户名是否符合要求
                if (checkNewPassword(newPassword)) {
                    // TODO: 更新用户名
                    MainActivity.controller.send(StringServer.CHANGE_NAME + newPassword);
                    tv_name.setText("用户名："+ newPassword);
                } else {
                    // 提示用户新密码不符合要求
                    runOnUiThread(() ->Toast.makeText(UserActivity.this, "新用户名不符合要求！", Toast.LENGTH_SHORT).show());
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}