package ChatServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class acceptListener extends Thread{                                     //监听并建立连接
    List<Socket> sockets;
    ServerSocket sc;
    List<DataOutputStream> doss;
    List<String > usernameList;
    public acceptListener(ServerSocket sc, List<Socket> sockets, List<DataOutputStream> doss,List<String> usernameList){
        this.sockets = sockets;
        this.sc = sc;
        this.doss = doss;
        this.usernameList = usernameList;
    }
    public void run(){
        while (true){
            try {
                int i = sockets.size();
                if(i<256) {
                    sockets.add(sc.accept());
                    System.out.println("新连接建立了"+sockets.get(sockets.size()-1));
                    i = sockets.size()-1;
                    doss.add(new DataOutputStream(sockets.get(i).getOutputStream()));
                    Retransmission retransmission = new Retransmission(sockets.get(i),sockets,doss,usernameList);
                    retransmission.start();
                }
                else break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
