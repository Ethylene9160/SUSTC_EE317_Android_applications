package com.example.musicapplication.web.string_server;


import com.example.musicapplication.my_music.MusicDemo;
import com.example.musicapplication.my_music.MyDate;
import com.example.musicapplication.my_music.SingleComment;
import com.example.musicapplication.my_music.User;
import com.example.musicapplication.web.WebController;
import com.example.musicapplication.web.WebInterface;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StringServer{
    public static final char ANNOUNCE_COMMENCE = 'a', GET_COMMENCE = 'b', LOG_IN = 'c', SIGN_UP = 'd', WRONG_ANSWER = 'e', TRUE_ANSWER = 'f',
                        FIND_PLAYER = 'g', CONTACT = 'h', SYSTEM_INFO = 'i', CONNECTED_SUCCESS = 'j', ERROR = 'k', LOG_OUT = 'l',CHANGE_PASSWARD = 'm',
                        CHANGE_NAME = 'n';
    public static Map<String, MusicDemo> musicList = new HashMap<>();
    public static Map<String, User> users;//todo
    public static Map<String, StringChannel> listMap = new HashMap<>();
    public static int webID;
    public static void main(String[] args) throws IOException {
        users = getUsers(null);
        webID  = 100;//
        System.out.println("start");
        ServerSocket moveServer = new ServerSocket(8888);
        while(true){
            webID ++;
            Socket socket = moveServer.accept();
            StringChannel stringChannel = new StringChannel(socket);
            stringChannel.ownID = String.valueOf(webID);
            System.out.println("someone comes"+(listMap.size()+1));
            //
            new DataOutputStream(socket.getOutputStream()).writeUTF('a'+Integer.toString(webID));
            listMap.put(String.valueOf(webID), stringChannel);
        }
    }

    private static Map<String, User> getUsers(String path){
        //todo!
        return new HashMap<>();
    }

    static boolean isUser(String id, String key){
        return users.get(id).getKey().equals(key);
    }

    static String findOnlinePlayer(){
        StringBuilder sb = new StringBuilder();
        for (StringChannel value : listMap.values()) {
            if(value.ownID.length() > 3){
                sb.append(Objects.requireNonNull(users.get(value.ownID)).getGeneralInfo()).append("#");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}

class StringChannel implements WebInterface {
    String ownID;
    User myself;
    WebController controller;
    public StringChannel(Socket clientSocket){
        controller = new WebController(clientSocket, this);
    }
    @Override
    public void webAction(String message) {
        messageProcess(message.charAt(0), message.substring(1));
    }
    private void messageProcess(char ch, String msg){
        String[] info = msg.split("#");
        switch (ch){
            case StringServer.ANNOUNCE_COMMENCE:
                //musicName#comment
                announceCommence(info[0], info[1]);
                break;
            case StringServer.GET_COMMENCE:
                this.controller.send(getCommence(info[1]));
                break;
            case StringServer.LOG_IN:
                //old_id#id#key
                new Thread(()->{
                    if(StringServer.isUser(info[1], info[2])){
                        StringServer.listMap.remove(info[0]);
                        StringServer.listMap.put(info[1], this);
                        StringChannel.this.controller.send(StringServer.TRUE_ANSWER + "");
                        myself = StringServer.users.get(info[1]);
                    }else{
                        StringChannel.this.controller.send(StringServer.WRONG_ANSWER + "");
                    }
                }).start();
                break;
            case StringServer.SIGN_UP:
                //name#key
                ownID = StringServer.users.size() + 10001 + "";
                User user = new User(info[0], ownID, info[1]);
                StringServer.users.put(ownID, user);
                this.controller.send(StringServer.SYSTEM_INFO + "Success! Your id is: " + ownID);
                myself = StringServer.users.get(info[1]);
                break;
            case StringServer.FIND_PLAYER:
                //output: id&name
                this.controller.send(StringServer.findOnlinePlayer());
                break;
            case StringServer.CONTACT:
                //please make sure that, in info[1], there is your personal id or name to ensure the target confirm who you are.
                Objects.requireNonNull(StringServer.listMap.get(info[0])).controller.send(info[1]);
//                for (StringChannel value : StringServer.listMap.values()) {
//                    if(value != this){
//                        value.controller.send(msg);
//                    }
//                }


                break;
            case StringServer.LOG_OUT:
                StringServer.users.remove(myself.getId());
                myself = null;
                this.controller.send(StringServer.SYSTEM_INFO + "You have logged out successfully.");
                break;

            //todo: a way for buying the song
            //todo: a way for getting the user online
            //todo: add friend, also re-imply in User.java
            //todo: delete friend, also re-imply in User.java
            //todo: sub-comment
        }
    }
    private void announceCommence(String musicName, String comment){
        SingleComment singleComment = new SingleComment(myself, comment, new MyDate());
        StringServer.musicList.get(musicName).getComments().put(singleComment);
    }
    private String getCommence(String musicName){
        return StringServer.musicList.get(musicName).getComments().toString();
    }
}


