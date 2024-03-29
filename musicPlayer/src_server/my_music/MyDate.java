package my_music;


import java.util.Calendar;

public class MyDate implements Comparable<MyDate>{
//    private static Calendar c = Calendar.getInstance();
    private int year, month, day, h, m, s;

    public MyDate(int year, int month, int day, int h, int m, int s) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public MyDate(){
        this(Calendar.getInstance());

//        this(2023,3,24,14,21,55);
    }

    public MyDate(Calendar c){
        this(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH)+1,
                c.get(Calendar.DATE)-1,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND)
        );
    }

    /**
     * 打包方法：直接打包成字符串
     *
     * @param msg
     */
    public MyDate(String msg) {
        this(msg.charAt(0), msg.charAt(1), msg.charAt(2), msg.charAt(3), msg.charAt(4), msg.charAt(5));
    }

    public String getDetailTime() {
        return String.format("%d,%2d,%2d %02d:%02d:%02d", year, month, day, h, m, s);
    }

    public String getDay() {
        return String.format("%d,%d,%d", year, month, day);
    }

    @Override
    public int compareTo(MyDate o) {
        if(this.year != o.year){
            return cp(this.year, o.year);
        }
        if(this.month != o.month){
            return cp(this.month, o.month);
        }
        if(this.day != o.day){
            return cp(this.day, o.day);
        }
        if(this.m != o.m){
            return cp(this.m, o.m);
        }
        if(this.h != o.h){
            return cp(this.h, o.h);
        }
        if(this.s != o.s){
            return cp(this.s, o.s);
        }
        return 0;
    }

    private int cp(int a, int b){
        return b-a;
    }


}
