package com.example.musicapplication.my_music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comments{
    private List<SingleComment> commentList;
    public Comments(SingleComment...singleComment){
        commentList = new ArrayList<>();
        commentList.addAll(Arrays.asList(singleComment));
    }

    //todo: 完成对list的遍历
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (SingleComment comment : commentList) {
            builder.append(comment).append("\n\n");
        }
        return builder.toString();
    }

    public List<SingleComment> getList(){
        return this.commentList;
    }

    public void refresh(SingleComment...newComments){
        if(newComments == null) return;
        commentList.clear();
        commentList.addAll(Arrays.asList(newComments));
    }

    public void put(SingleComment c){
        this.commentList.add(0,c);
//        this.commentList.add;
    }
}

