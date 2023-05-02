package com.example.musicapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicapplication.my_music.MusicDemo;
import com.example.musicapplication.my_music.User;
import com.example.musicapplication.tools.Constants;
import com.example.musicapplication.web.WebController;
import com.example.musicapplication.web.WebInterface;
import com.example.musicapplication.web.string_server.StringServer;


public class MainActivity extends AppCompatActivity implements WebInterface {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    public static WebController controller;//app共用一个网络代理
    private static boolean isConnected, isRegisted;//状态记录

    public static User user;//app共用一个登录用户信息

    public static boolean isConnected() {
        return isConnected;
    }

    public static boolean isRegisted() {
        return isRegisted;
    }

    private RecyclerView rv_musics;

    private Button loginButton, connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.connectButton = findViewById(R.id.connect_button);
        this.loginButton = findViewById(R.id.login_button0);
        this.loginButton.setOnClickListener((v -> login()));
        rv_musics = findViewById(R.id.song_list);
        connectButton.setOnClickListener(v -> {
            if (!isConnected) {
                controller = new WebController(this);
//                isConnected = true;
            }else Toast.makeText(getApplicationContext(), "您已连接到服务器！", Toast.LENGTH_SHORT).show();
        });
        //设置布局管理器
        rv_musics.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //设置适配器
        rv_musics.setAdapter(new MyAdapter());
        //设置itemView间的间隔
        rv_musics.addItemDecoration(new MyDecoration());
//        new Intent(this, LocalMusicActivity.class);
//        new LocalMusicActivity().onCreate(savedInstanceState);
//        controller = new WebController(this);

    }

    private void login() {
        if (!MainActivity.isConnected()) {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "还没有连接网络。", Toast.LENGTH_SHORT).show());
            return;
        }
        Intent intent;
        if(isRegisted){
            intent = new Intent(this, UserActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }


    public void selectMusic(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");
        startActivityForResult(intent, 1);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            //todo: Do something with the URI of the selected music file
        }
    }

    @Override
    public void webAction(String message) {
        msgProcess(message.charAt(0), message.substring(1));
    }

    /**
     * 对来自服务器的信息进行处理
     * @param c 标记
     * @param msg 信息
     */
    private void msgProcess(char c, String msg) {
        String info[] = msg.split("#");
        switch (c) {
            case StringServer.CONNECTED_SUCCESS:
                isConnected = true;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "成功连接到服务器。", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case StringServer.TRUE_ANSWER:
                isRegisted = true;
                user = new User(info[0], info[1], info[2], null);
                runOnUiThread(new Runnable() {
                    public void run() {
                        loginButton.setText(user.getName());
                        Toast.makeText(getApplicationContext(), "登陆成功。\n欢迎"+user.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case StringServer.WRONG_ANSWER:
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "账号或密码错误。", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case StringServer.ERROR:
                controller = null;
                isConnected = false;
                isRegisted = false;
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        loginButton.setText("登录");
                        Toast.makeText(getApplicationContext(), "网络异常，连接失败。", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case StringServer.GET_COMMENCE:
                CommentActivity.setComment(msg);
//                CommentActivity.iv_comments.setText(msg);
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),CommentActivity.class);
                        startActivity(intent);
                    }
                });
                break;
                case StringServer.SYSTEM_INFO:
                    runOnUiThread(()->{
                        Toast.makeText(getApplicationContext(), "系统消息:\n"+msg, Toast.LENGTH_SHORT).show();
                    });
                    break;
            case StringServer.LOG_OUT:
                isRegisted = false;
                runOnUiThread(()->{
                    loginButton.setText("登录");
                    Toast.makeText(getApplicationContext(), "您已成功登出。", Toast.LENGTH_SHORT).show();
                });
                break;
                case StringServer.CONTACT:
                    runOnUiThread(()->{
//                        loginButton.setText("登录");
                        Toast.makeText(getApplicationContext(), info[0]+"发送了一条私信：\n"+info[1], Toast.LENGTH_SHORT).show();
                    });
                    break;
            case StringServer.SIGN_UP:
                runOnUiThread(()->{
                    Toast.makeText(getApplicationContext(), "注册成功! 你的id为："+msg, Toast.LENGTH_LONG).show();
        });
                break;

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        @NonNull
        @Override
        //将itemlayout布局转成转成视图，给MyHolder。
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getApplicationContext(), R.layout.itemlayout, null);//将itemlayout布局转成转成视图
            MyHolder myHolder = new MyHolder(view);//将得到的视图给MyHolder。
            return myHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        //制定每个holder里的每个控件的具体内容；
        public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
            MusicDemo demo = Constants.musicLists.get(position);
            holder.miv_image.setBackgroundResource(demo.getImg());
            holder.mtv_name.setText(demo.getMusicName());
            holder.mtv_info.setText(demo.getArtist() + " " + demo.getVolumn());
            //给每个itemView设置点击事件，跳转到相应的界面。
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mintent = null;
                    MusicActivity.currentDemo = demo;
                    mintent = new Intent(MainActivity.this, MusicActivity.class);
                    startActivity(mintent);
                }
            });

        }

        @Override
        //给出item的数量
        public int getItemCount() {
            return Constants.musicLists.size();
        }

        //声明MyHolder里面都有哪些控件，并和itemlayout里面的控件一一对应
        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView miv_image;
            TextView mtv_name, mtv_info;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                miv_image = itemView.findViewById(R.id.iv_image);
                mtv_name = itemView.findViewById(R.id.tv_name);
                mtv_info = itemView.findViewById(R.id.tv_information);
            }
        }
    }

    //自定义itemView的间隔
    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, 20);
//            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }

    @Override
    protected void onDestroy() {
        MusicActivity.control.stopPlay();
        unbindService(MusicActivity.connection);
        super.onDestroy();
    }

}
