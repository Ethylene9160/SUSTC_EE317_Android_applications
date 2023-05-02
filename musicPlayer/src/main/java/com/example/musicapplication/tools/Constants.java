package com.example.musicapplication.tools;

import com.example.musicapplication.R;
import com.example.musicapplication.my_music.MusicDemo;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final char REFRESH = 'Q', FIND_MUSIC = 'W', CONFIRM_MUSIC = 'E';
    public static MusicDemo ErGe = new MusicDemo("快乐儿歌","无","佚名", R.drawable.cover,R.raw.song1);
    public final static MusicDemo Jiangjunling = new MusicDemo("将军令","五月天", "YOUR LEGEND ~燃烧的生命~", R.drawable.legend,R.raw.song2);

    public static List<MusicDemo> musicLists = getMusicLists();

    public static List<MusicDemo> getMusicLists(){
        ArrayList<MusicDemo> musicDemos = new ArrayList<>();
        musicDemos.add(ErGe);
        musicDemos.add(Jiangjunling);
        musicDemos.add(new MusicDemo("转眼","五月天","自传", R.drawable.history,R.raw.zhuanyan));
        musicDemos.add(new MusicDemo("兄弟","五月天","自传", R.drawable.history,R.raw.brother));
        musicDemos.add(new MusicDemo("顽固","五月天","自传", R.drawable.history,R.raw.tough));
        musicDemos.add(new MusicDemo("派对动物","五月天","自传", R.drawable.history,R.raw.party_animal));
        musicDemos.add(new MusicDemo("干杯","五月天","第二人生-明日版", R.drawable.second_life,R.raw.cheers));
        musicDemos.add(new MusicDemo("星空","五月天","第二人生-明日版", R.drawable.second_life,R.raw.star));
        return musicDemos;
    }
}
