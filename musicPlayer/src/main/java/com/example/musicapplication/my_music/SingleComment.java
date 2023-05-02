package com.example.musicapplication.my_music;

public class SingleComment {
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
        return String.format("%s %s\n%s", user.getName(), myDate.getDetailTime(), comment);
    }
}
