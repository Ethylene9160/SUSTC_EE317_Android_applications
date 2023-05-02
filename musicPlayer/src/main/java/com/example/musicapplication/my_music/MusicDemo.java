package com.example.musicapplication.my_music;

public class MusicDemo {
    private String musicName;
    private String artist;
    private String volumn;
    private int img;//专辑图片
    private int musicFile;//音乐资源id
    private Comments comments;//该音乐对应的评论
    private int price;

    public MusicDemo(String musicName, String artistm, String volumn, int img, int musicFile){
        this.musicName = musicName;
        this.artist = artistm;
        this.volumn = volumn;
        this.img = img;
        this.musicFile = musicFile;
    }

    public int getPrice() {
        return price;
    }

    public boolean canBuy(int given){
        return !(given < this.price);
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getMusicName() {
        return musicName;
    }

    public String getArtist() {
        return artist;
    }

    public String getVolumn() {
        return volumn;
    }

    public int getImg() {
        return img;
    }

    public int getMusicFile() {
        return musicFile;
    }

    public Comments getComments() {
        return comments;
    }

    @Override
    public String toString(){
        return String.format("%s%s%s", musicName, artist, volumn);
    }

    //重写equals方法，对每个musicDemo来说，其歌手、专辑、歌名即可对应一个demo。
    @Override
    public boolean equals(Object o){
        if(!(o instanceof MusicDemo)) return false;
        return this.toString().equals(o.toString());
    }

    //重写hashCode方法，以便能够直接提高该类在java的数据表（包括但不限于数组、链表、哈希映射等
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
