package my_music;

public class MusicDemo {
    private String musicName;
    private String artist;
    private String volumn;
    private int img;
    private int musicFile;
    private Comments comments;
    private int price;

//    public MusicDemo(String musicName, String artist, String volumn, Image img, File musicFile, Comments comments) {
//        this.musicName = musicName;
//        this.artist = artist;
//        this.volumn = volumn;
//        this.img = img;
//        this.musicFile = musicFile;
//        this.comments = comments;
//    }

    public MusicDemo(String musicName, String artistm, String volumn, int img, int musicFile){
        this.musicName = musicName;
        this.artist = artistm;
        this.volumn = volumn;
        this.img = img;
        this.musicFile = musicFile;
        comments = new Comments();
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

//    public MusicDemo(String musicName, String artist, String volumn, Image img, File musicFile, Comments comments, int price){
//        this.musicName = musicName;
//        this.artist = artist;
//        this.volumn = volumn;
//        this.img = img;
//        this.musicFile = musicFile;
//        this.comments = comments;
//        this.price = price;
//    }

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

    public void setComments(Comments c){
        comments = c;
    }

    public void addComments(SingleComment s){
        comments.put(s);
    }

//    @Override
//    public String toString(){
//        return String.format("%s%s%s", musicName, artist, volumn);
//    }
//
//    @Override
//    public boolean equals(Object o){
//        if(!(o instanceof MusicDemo)) return false;
//        return this.toString().equals(o.toString());
//    }
//
//    @Override
//    public int hashCode(){
//        return this.toString().hashCode();
//    }

    @Override
    public String toString(){
        return String.format("%s%s%s", musicName, artist, volumn);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof MusicDemo)) return false;
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
}
