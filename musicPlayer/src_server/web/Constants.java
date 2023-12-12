package web;

import my_music.*;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static MusicDemo ErGe = new MusicDemo("快乐儿歌","无","佚名",0,0);
    public final static MusicDemo Jiangjunling = new MusicDemo("将军令","五月天", "YOUR LEGEND ~燃烧的生命~", 0,0);


    private static final User u1 = new User("Spark", "12121", "000", 0);
    private static final User u2 = new User("降临者", "12121", "000", 0);

    public static List<MusicDemo> musicLists = getMusicLists();


    public static List<MusicDemo> getMusicLists(){
        ArrayList<MusicDemo> musicDemos = new ArrayList<>();
        Comments comments = new Comments();
        //一些例子
        comments.put(new SingleComment(u1, "太打动我了这首曲子！", new MyDate(2023,4,30,0,11,52)));
        comments.put(new SingleComment(u2, "一个小评论！", new MyDate(2023,4,30,0,16,52)));
        comments.put(new SingleComment(u1, "这是一个评论", new MyDate(2023,1,30,14,23,12)));
        comments.put(new SingleComment(u1, "这是一个长长长长长长长长长长长长长长长的评的的的的论", new MyDate(2022,11,6,13,11,52)));
        comments.put(new SingleComment(u1, "这是依然一个长长长长长长长长长长长长长长长的评的的的的论", new MyDate(2023,4,30,0,11,52)));
        comments.put(new SingleComment(u2, "这是一个长长长长长长长长长长长长长长长的评的的的的论", new MyDate(2023,4,30,0,11,52)));
        comments.put(new SingleComment(new User("Knight", "16121", "000", 0), "好听", new MyDate(2023,4,30,0,11,52)));
        musicDemos.add(ErGe);
        Jiangjunling.setComments(comments);
        musicDemos.add(Jiangjunling);
        MusicDemo demo = new MusicDemo("转眼", "五月天", "自传", 0, 0);
        demo.setComments(new Comments(new SingleComment(u1, "泪目", new MyDate(2022,10,14,16,29,4))));
        musicDemos.add(demo);
        musicDemos.add(new MusicDemo("兄弟","五月天","自传", 0,0));
        musicDemos.add(new MusicDemo("顽固","五月天","自传", 0,0));
        musicDemos.add(new MusicDemo("派对动物","五月天","自传",0,0));
        musicDemos.add(new MusicDemo("干杯","五月天","第二人生-明日版",0,0));
        return musicDemos;
    }
}
