package ChatServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class LoginRefreshNoticeThread extends Thread{                   //每5秒向所有人广播在线状态
    List<Socket> sockets;
    List<DataOutputStream> doss;
    List<String> usernameList;

    public LoginRefreshNoticeThread(List<Socket> sockets,List<DataOutputStream> doss,List<String> usernameList){
        this.sockets = sockets;
        this.doss = doss;
        this.usernameList = usernameList;
        System.out.println("广播线程创建了");
    }

    public void run(){
        while (true){
            try{
                String content = DataPack.toDataPack("loginRefresh", "server", "all", usernameList);
                for (DataOutputStream dataOutputStream : doss) {
                    if (dataOutputStream != null)
                        dataOutputStream.writeUTF(content);
                }
//                System.out.println(content);
                Thread.sleep(5000);
            }catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
