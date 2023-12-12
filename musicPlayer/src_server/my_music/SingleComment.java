package my_music;

public class SingleComment implements Comparable<SingleComment>{
    private User user;
    private MyDate myDate;
    private String comment;

    public SingleComment(User user, String comment, MyDate myDate) {
        this.user = user;
        this.myDate = myDate;
        this.comment = comment;
    }

    @Override
    public String toString() {
//        System.out.println(user.getName());
        return String.format("%s %s\n%s", user.getName(), myDate.getDetailTime(), comment);
    }

    @Override
    public int compareTo(SingleComment o) {
        return this.myDate.compareTo(o.myDate);
    }
}
