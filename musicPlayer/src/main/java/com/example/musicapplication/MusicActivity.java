package com.example.musicapplication;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicapplication.my_music.MusicDemo;
import com.example.musicapplication.tools.Constants;
import com.example.musicapplication.web.string_server.StringServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicActivity extends AppCompatActivity{
    private ImageView iv_cover;
    boolean isSameVolumn = false, isPause = false, isStart = false;
    List<MusicDemo> sameVolumn = new ArrayList<>();

    public static MusicDemo currentDemo;
    private static int song = 0, iv = 0;
    private static SeekBar sb;
    private static TextView tv_title, tv_progress,tv_total;

    private Button modeButton, pauseButton;


    public void setCurrentDemo(MusicDemo currentDemo) {
        MusicActivity.currentDemo = currentDemo;
        tv_title.setText(currentDemo.getMusicName());
        this.iv_cover.setImageResource(currentDemo.getImg());
//        control.setSong(currentDemo.getMusicFile());
        modeButton = findViewById(R.id.btn_mode);
        pauseButton = findViewById(R.id.btn_pause);
        modeButton.setOnClickListener(v->setIsSameVolumn());
        song=currentDemo.getMusicFile();
    }

    public String getMusicInfo(){
        if(currentDemo != null){
            return currentDemo.toString();
        }
        return null;
    }

    public MusicDemo getCurrentDemo() {
        return currentDemo;
    }

    private Button btn_play,btn_pause, btn_comment,btn_exit;

    private ObjectAnimator animator; //声明一个动画组件ObjectAnimator

    public static MusicService.MusicControl control;//声明MusicService中的音乐控制器

    public static ServiceConnection connection = new ServiceConnection() { //声明服务连接
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            control = (MusicService.MusicControl) iBinder;//实例化音乐控制对象，即control。
            control.setSong(song);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }

    };

    public void setIsSameVolumn(){
        if(!isSameVolumn) {
            sameVolumn.clear();
            for (MusicDemo demo : Constants.musicLists) {
                if(demo.getVolumn().equals(currentDemo.getVolumn())){
                    this.sameVolumn.add(demo);
                }
            }
        }
        isSameVolumn = !isSameVolumn;
        modeButton.setText(isSameVolumn? "专辑内随机":"资料库随机");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        init();

        setCurrentDemo(currentDemo);

//        startMusic();
//        pause();
    }

    public void changeMusicInfo(MusicDemo demo){
        control.stopPlay();
        this.iv = demo.getImg();
        this.song = demo.getMusicFile();
        resetControl();
        control.play();
    }

    private void resetControl(){
        control.setSong(song);
    }
    public void init(){

        iv_cover = findViewById(R.id.iv_cover);
        sb = findViewById(R.id.sb);
        tv_title = findViewById(R.id.tv_title);
        tv_progress = findViewById(R.id.tv_progress);
        tv_total = findViewById(R.id.tv_total);

//        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_comment = findViewById(R.id.btn_commence);
        btn_exit = findViewById(R.id.btn_next);

        OnClick monclick = new OnClick();
//        btn_play.setOnClickListener(monclick);
        btn_pause.setOnClickListener(monclick);
        btn_comment.setOnClickListener(monclick);
        btn_exit.setOnClickListener(monclick);

        //执行动画的对象是iv_cover，// 动画效果是0-360°旋转（用的是浮点数，所以加个f）。
        animator = ObjectAnimator.ofFloat(iv_cover,"rotation",0.0f,360.0f);
        animator.setDuration(10000); //旋转一周的时长，单位是毫秒，此处设置了10s
        animator.setInterpolator(new LinearInterpolator());//设置匀速转动
        animator.setRepeatCount(-1);//设置循环，此处设置的是无限循环。如果是正值，意味着转动多少圈。

        //声明一个意图，该意图进行服务的启动，意思是将MusicService里面的服务要传到主程序这里来。
        Intent mintent = new Intent(MusicActivity.this,MusicService.class);
        bindService(mintent,connection,BIND_AUTO_CREATE);//建立意图中MainActivity与MusicService两对象的服务连接

        seekBarListener msbListener = new seekBarListener();
        sb.setOnSeekBarChangeListener(msbListener);


    }
    // 设置播放、暂停、继续和退出按钮的监听（或点击）事件
    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
//                case R.id.btn_play:
//                    startMusic();
//                    break;
                case R.id.btn_pause://切换音乐
                    if(!isStart){
                        startMusic();
                        isStart = true;
                        return;
                    }
                    pause();
                    break;
                case R.id.btn_commence:
                    if(!MainActivity.isConnected()) {
                        Toast.makeText(getApplicationContext(), "你还没有连接服务器！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CommentActivity.currentDemo = MusicActivity.currentDemo;
//                    Toast.makeText(getApplicationContext(), "get commence: "+currentDemo.toString(), Toast.LENGTH_SHORT).show();
                    MainActivity.controller.send(StringServer.GET_COMMENCE + "0#"+currentDemo.toString());
//                    try {
//                        Thread.sleep(800);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
//                    startActivity(intent);
                    break;
                case R.id.btn_next:
                    if(!isSameVolumn) {
                        int i = new Random().nextInt(Constants.musicLists.size());
                        changeMusic(Constants.musicLists.get(i));
                    }else{
                        int i = new Random().nextInt(sameVolumn.size());
                        changeMusic(sameVolumn.get(i));
                    }


                    break;
            }
        }
    }

    private void startMusic() {
        isPause = false;
        pauseButton.setText("暂停");
        //播放音乐
        control.play();
        //光盘开始转
        animator.start();
    }

    private void pause(){
        if(isPause){
            //继续播放音乐
            control.continuePlay();
            //光盘继续转
            animator.resume();
            pauseButton.setText("暂停");
        }else{
            //停止播放音乐
            control.pausePlay();
            //光盘停止转
            animator.pause();
            pauseButton.setText("播放");
        }
        isPause = !isPause;
    }

    private void changeMusic(MusicDemo demo){
        setCurrentDemo(demo);
        control.setSong(song);
        startMusic();
        //声明一个意图，该意图进行服务的启动，意思是将MusicService里面的服务要传到主程序这里来。

//                    resetControl();
//                    try {
//                        control.getMediaPlayer().setDataSource("raw//song1.mp3");
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
    }

//    @Override
//    protected void onDestroy() {
//        control.stopPlay();
//        unbindService(connection);
//        super.onDestroy();
//    }

    //Handler主要用于异步消息的处理，在这里是处理子线程MusicService传来的消息
    public static Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");//把音乐时长放在bundle里
            int currentDuration = bundle.getInt("currentDuration");//把音乐当前播放时长放在bundle里

            sb.setMax(duration);
            sb.setProgress(currentDuration);

            //显示总时长
            int minite = duration / 1000 /60;
            int second = duration / 1000 % 60;

            String strMinite = "";
            String strSecond = "";

            if (minite < 10){
                strMinite = "0" +minite;
            }else {
                strMinite = minite + "";
            }

            if (second < 10){
                strSecond = "0" + second;
            }else {
                strSecond = second + "";
            }

            tv_total.setText(strMinite + ":" + strSecond);


            //显示播放时长
            minite = currentDuration / 1000 /60;
            second = currentDuration / 1000 % 60;

            if (minite < 10){
                strMinite = "0" +minite;
            }else {
                strMinite = minite + "";
            }
            if (second < 10){
                strSecond = "0" + second;
            }else {
                strSecond = second + "";
            }
            tv_progress.setText(strMinite + ":" + strSecond);
        }
    };

    //给进度条设置监听
    class seekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        //进度条行进过程的监听
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i == seekBar.getMax()){
                animator.pause();
            }
            if (b){//判断是否来自用户
                control.seekTo(i);
            }
        }

        @Override
        //用户开始滑动进度条的监听
        public void onStartTrackingTouch(SeekBar seekBar) {
            control.pausePlay();
            animator.pause();
        }

        @Override
        //用户停止滑动进度条的监听
        public void onStopTrackingTouch(SeekBar seekBar) {
            control.continuePlay();
            animator.resume();
        }
    }


}
