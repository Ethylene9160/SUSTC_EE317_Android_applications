package web;

import web.string_server.StringServer;

import java.io.*;
import java.net.Socket;

public class WebController {
    public static String SERVER_ADDRESS = "127.0.0.1";//服务器地址。TCP服务器本身用不上。
    public static final int SERVER_PORT = 8888;
    private WebInterface web;
    private Socket serverSocket;
    private Sender sender;
    private Receiver receiver;
    private Thread receiveThread;

    public WebController(Socket serverSocket, WebInterface web){
        creatWebController(serverSocket,web);
    }

    public WebController(WebInterface web){
        creatWebController(web);
    }
    public void send(String message){
        sender.send(message);
    }

    public Socket getSocket(){
        return this.serverSocket;
    }
    public void creatWebController(Socket serverSocket, WebInterface web){
        new Thread(()->{
            if(this.receiveThread != null) return;
            this.web = web;
            changeWebController(serverSocket);
        }).start();
//        this.serverSocket = serverSocket;
//        this.receiver = new Receiver(serverSocket, web);
//        this.receiveThread = new Thread(receiver);
//        receiveThread.start();
//        this.sender = new Sender(serverSocket);
    }

    public void creatWebController(WebInterface web){
        new Thread(()->{
            try {
                this.creatWebController(new Socket(SERVER_ADDRESS, SERVER_PORT), web);
            } catch (IOException e) {
                e.printStackTrace();
                web.webAction(String.valueOf(StringServer.ERROR));
            }
        }).start();
    }

    public void changeWebController(Socket serverSocket){
        if(this.web == null) return;
        this.serverSocket = serverSocket;
        this.receiver = new Receiver(serverSocket, web);
        this.receiveThread = new Thread(receiver);
        receiveThread.start();
        this.sender = new Sender(serverSocket);
    }
}

class Sender implements Closeable{
    private DataOutputStream outputStream;
    //private boolean flag = true;

    /**
     * impliment of multiple thread to realize <code>send(String str)</code> method, to avoid taking up current thread.
     * @param str message to be sent
     */
    void send(String str) {
        new Thread(()->{
            try {
                this.outputStream.writeUTF(str);
                this.outputStream.flush();
            } catch (IOException var3) {
                //this.flag = false;
                WebUtil.closeAll(this.outputStream);
                var3.printStackTrace();
            }
        }).start();
    }

    void sendFile(String fileName){
        try{
            //read the file
            InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
            byte[] buffer = new byte[1024];
            while(inputStream.read(buffer) != -1){
                outputStream.write(buffer);
            }
            inputStream.close();
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
            //todo!
//            WebUtil.closeAll();
        }
    }

    public Sender(Socket client) {
        try {
            this.outputStream = new DataOutputStream(client.getOutputStream());
        } catch (IOException var3) {
            //this.flag = false;
            WebUtil.closeAll(this.outputStream, client);
            var3.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }
}

class Receiver implements Runnable, Closeable {
    private DataInputStream inputStream;

    private boolean flag = true;
    private WebInterface web;
    public String getData;

    public Receiver(Socket client, WebInterface web) {
        this.web = web;
        try {
            this.inputStream = new DataInputStream(client.getInputStream());
        } catch (IOException var4) {
            this.flag = false;
            WebUtil.closeAll(this.inputStream, client);
        }

    }

    public DataInputStream getInputStream() {
        return this.inputStream;
    }

    private String getMessage() {
        try {
            return this.inputStream.readUTF();
        } catch (IOException var3) {
//            JOptionPane.showMessageDialog((Component) null, "错误：没有连接到服务器。\n请退出游戏或者检查网络连接。", "错误", 0);
            this.flag = false;
            WebUtil.closeAll(this.inputStream);
            var3.printStackTrace();
        }
        return null;
    }

    private void readFile(){

    }

    @Override
    public void run() {
        while (this.flag) {
            this.getData = this.getMessage();
            this.web.webAction(getData);
            System.out.println("ck Receivevr: 获取到数据：" + this.getData);
        }
    }

    @Override
    public void close() throws IOException {
        WebUtil.closeAll(inputStream);
    }
}