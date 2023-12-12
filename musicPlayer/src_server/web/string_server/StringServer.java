package web.string_server;


import my_music.MusicDemo;
import my_music.MyDate;
import my_music.SingleComment;
import my_music.User;
import web.Constants;
import web.WebController;
import web.WebInterface;

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
    public static Map<String, MusicDemo> musicList = getMusicList(null);
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
//            new DataOutputStream(socket.getOutputStream()).writeUTF('a'+Integer.toString(webID));
            new DataOutputStream(socket.getOutputStream()).writeUTF(String.valueOf(CONNECTED_SUCCESS));
            listMap.put(String.valueOf(webID), stringChannel);
        }
    }

    private static Map<String, User> getUsers(String path){
        //todo!
//        return null;
        HashMap<String, User> map = new HashMap<>();
        map.put("10001", new User("降临者","10001", "123456",0));
        map.put("10002", new User("Spark","10002", "123456",0));
        return map;
        //return new HashMap<>();
    }

    static void save(String outputFile){
        //todo: save users to a local file.
    }
    static boolean isUser(String id, String key){
        User u = users.get(id);
        if(u == null) return false;
        return u.getKey().equals(key);
    }

    static Map<String, MusicDemo> getMusicList(String path) {
        HashMap<String, MusicDemo> map = new HashMap<>();
        for (MusicDemo demo : Constants.musicLists) {
            map.put(demo.toString(), demo);
        }
        return map;
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
        try {
            messageProcess(message.charAt(0), message.substring(1));
        }catch (NullPointerException e){
            controller.send(String.valueOf(StringServer.ERROR));
            e.printStackTrace();
            StringServer.listMap.remove(ownID);
        }
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
                        StringServer.listMap.remove(ownID);
                        ownID = info[1];
                        StringServer.listMap.put(info[1], this);
                        myself = StringServer.users.get(info[1]);
                        StringChannel.this.controller.send(StringServer.TRUE_ANSWER +myself.getName() +"#"+myself.getId()+"#"+myself.getKey());
                        System.out.println("successful!"+ownID);
                    }else{
                        StringChannel.this.controller.send(StringServer.WRONG_ANSWER + "");
                        System.out.println("failed!");
                    }
                }).start();
                break;
            case StringServer.SIGN_UP:
                //name#key
                ownID = StringServer.users.size() + 10001 + "";
                User user = new User(info[0], ownID, info[1]);
                StringServer.users.put(ownID, user);
                this.controller.send(StringServer.SIGN_UP + ownID);
//                myself = StringServer.users.get(info[1]);
                break;
            case StringServer.FIND_PLAYER:
                //output: id&name
                this.controller.send(StringServer.findOnlinePlayer());
                break;
                case StringServer.CHANGE_NAME:
                    this.myself.setName(msg);
                    this.controller.send(StringServer.SYSTEM_INFO+"用户名修改成功！");
                    break;
            case StringServer.CONTACT:
                //please make sure that, in info[1], there is your personal id or name to ensure the target confirm who you are.
                User u0 = StringServer.users.get(info[0]);
                if(u0 == null) {
                    this.controller.send(StringServer.SYSTEM_INFO + "查无此人。请检查ID是否正确");
                    return;
                }
                StringChannel u = StringServer.listMap.get(info[0]);
                if(u == null) this.controller.send(StringServer.SYSTEM_INFO + "发送失败，对方不在线。");
                else u.controller.send(StringServer.CONTACT+myself.getName()+"#"+info[1]);
//                for (StringChannel value : StringServer.listMap.values()) {
//                    if(value != this){
//                        value.controller.send(msg);
//                    }
//                }


                break;
            case StringServer.LOG_OUT:
                StringServer.listMap.remove(myself.getId());
                myself = null;
                this.controller.send(String.valueOf(StringServer.LOG_OUT));
                break;
                case StringServer.CHANGE_PASSWARD:
                    myself.setKey(msg);
                    this.controller.send(StringServer.SYSTEM_INFO+"密码修改成功！");
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
        return StringServer.GET_COMMENCE+StringServer.musicList.get(musicName).getComments().toString();
    }
}


