package ChatClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientA {
    public static void main(String[] args){
        try{
            String name = "蓝白";
            Socket s = new Socket("localhost",50001);
            System.out.println("蓝白已经连接到了服务端"+s);
            Receive receive = new Receive(s);
            receive.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("login@蓝白@server@login");      //注册用户名
            while (true){
                String mytmp = br.readLine();
                dos.writeUTF("message@"+name+"@"+"All"+"@"+mytmp);
                if(mytmp.trim().equals("bye")){
                    dos.close();
                    s.close();
                    br.close();
                    break;
                }
            }

        }catch (UnknownHostException e){
            System.out.println("连接服务器失败："+e.getMessage());
        }catch (IOException e){
            System.out.println("发送数据失败："+e.getMessage());
        }
    }
}
