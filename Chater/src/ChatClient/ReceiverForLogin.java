package ChatClient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiverForLogin extends Thread{                   //用户名校验线程
    Socket s;
    DataInputStream din;
    public ReceiverForLogin(Socket s){
        this.s = s;
    }
    public void run(){
        try{
            din = new DataInputStream(s.getInputStream());
            while (true) {//"type@sender@receiver@content"
                Thread.sleep(100);
                String tmp = din.readUTF();
                System.out.println("1接收线程启动了");
                System.out.println("get"+tmp);
                System.out.println(tmp);
                if (tmp.split("@")[3].equals("pass")) {
                    System.out.println("Pass!!!");
                    LoginFrame.loginPassed = true;
                    ChatClientGUI.username = tmp.split("@")[2];
                    break;
                }else {
                    System.out.println("重复了！");
                    break;
                }
            }
        }catch (IOException | InterruptedException e) {
            System.out.println("接收数据失败" + e.getMessage());
        }
    }
}
