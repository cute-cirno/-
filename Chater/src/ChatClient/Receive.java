package ChatClient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive extends Thread{                                        //命令行版本客户端-消息接收线程
    Socket s;
    DataInputStream din;
    String name;
    public Receive(Socket s){
        this.s = s;
        try{
            din = new DataInputStream(s.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
        while (true){
            try{ //"type@sender@receiver@content"
                String tmp = din.readUTF();
//                System.out.println(tmp);
                if(tmp.split("@")[0].equals("message")){
                    String name;
                    String content;
                    name = tmp.split("@")[1];

                    content = tmp.substring(tmp.indexOf("@")+name.length()+3+tmp.split("@")[2].length());
                    System.out.println(name+"：" + content);
                }else if (tmp.split("@")[0].equals("loginRefresh")){
//                    DataUnpack data = new DataUnpack(tmp);
//                    for(String o:data.getUserList())
//                    System.out.println("在线:"+o);
                }
            }catch (IOException e){
                System.out.println("接收数据失败"+e.getMessage());
                break;
            }
        }
    }
}
