package ChatServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static void main(String[] args){
        try{
            List<Socket> sockets = new ArrayList<>();
            List<String> usernameList = new ArrayList<>();
            List<DataOutputStream> doss = new ArrayList<>();
            ServerSocket sc = new ServerSocket(50001);
            System.out.println("服务端已启动，正在等待连接");
            acceptListener listener = new acceptListener(sc,sockets,doss,usernameList);
            System.out.println("监听线程创建了");
            listener.start();
            System.out.println("监听线程启动了");
            LoginRefreshNoticeThread announcer = new LoginRefreshNoticeThread(sockets,doss,usernameList);
            announcer.start();
            System.out.println("广播线程启动了");
        }catch (IOException e){
            System.out.println("转发数据失败："+e.getMessage());
        }
    }
}
