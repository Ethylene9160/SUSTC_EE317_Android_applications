package com.example.musicapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicapplication.my_music.MusicDemo;
import com.example.musicapplication.my_music.MyDate;
import com.example.musicapplication.my_music.SingleComment;
import com.example.musicapplication.web.string_server.StringServer;

public class CommentActivity extends AppCompatActivity {

    private static String DF = "还没有评论噢~";
    public static TextView iv_comments;
    public static String comment = DF;

    private EditText cmtText;
    public static MusicDemo currentDemo;

    private Button cmtBtn;

    public static void setComment(String comment) {
        if(comment != null && !comment.isEmpty())
            CommentActivity.comment = comment;
        else CommentActivity.comment = DF;
        if(iv_comments != null) iv_comments.setText(comment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        iv_comments=findViewById(R.id.tv_comments);
            iv_comments.setText(comment);
        cmtText = findViewById(R.id.comment_input);
        cmtBtn = findViewById(R.id.send_button);
        cmtBtn.setOnClickListener(v->{
            if(MainActivity.isRegisted()){
                MainActivity.controller.send(StringServer.ANNOUNCE_COMMENCE + currentDemo.toString() + "#" + cmtText.getText().toString());
                if(comment.equals(DF)) comment = "";
                setComment(new SingleComment(MainActivity.user, cmtText.getText().toString(), new MyDate())+"\n\n"+comment);
                cmtText.setText("");
            }else{
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "您还没有登录。请登陆后尝试。", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}