package ChatServer;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Retransmission extends Thread {                                            //消息中转线程
    List<Socket> sockets;
    List<DataOutputStream> doss;
    List<String> usernameList;
    Socket s;
    DataInputStream din;
    public Retransmission(Socket s, List<Socket> sockets, List<DataOutputStream> doss,List<String> usernameList){
        this.s = s;
        this.sockets = sockets;
        this.doss = doss;
        this.usernameList = usernameList;
        try{
            System.out.println("当前有"+sockets.size()+"个连接");
            din = new DataInputStream(s.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
        while (true){
            try{
                String data;

                if(!(data = din.readUTF()).equals("")) {
                    System.out.println("未解析内容:"+data);
                    DataUnpack unpack = new DataUnpack(data);
                    if(unpack.type.equals("message")) {
                        System.out.println(unpack.type);
                        System.out.println(unpack.sender+"：" + unpack.getContent());
                        if(unpack.receiver.equals("All")){                                              //群聊消息转发
                            for (int i = 0; i < sockets.size(); i++) {
                                if (doss.get(i) != null) {
                                    doss.get(i).writeUTF(DataPack.toDataPack(unpack.type,unpack.sender,unpack.receiver,unpack.getContent()));
                                }
                            }
                        }else {
                            if(usernameList.contains(unpack.receiver)) {
                                System.out.println(unpack.receiver + unpack.getContent());              //私聊消息转发
                                doss.get(usernameList.indexOf(unpack.receiver)).writeUTF(DataPack.toDataPack(unpack.type, unpack.sender, unpack.receiver, unpack.getContent()));
                            }
                        }

                    }else if(unpack.type.equals("login")){                                              //用户上线处理
                        System.out.println(DataPack.toDataPack(unpack.type,unpack.sender,unpack.receiver,unpack.getContent()));
                        if(!usernameList.contains(unpack.sender)) {
                            usernameList.add(unpack.sender);
                            for (int i = 0; i < sockets.size(); i++) {
                                if (doss.get(i) != null) {
                                    doss.get(i).writeUTF("login@server@"+unpack.sender+"@pass");
                                    doss.get(i).writeUTF("message@server@"+"All@"+unpack.sender+"加入了群聊");
                                }
                            }
                        }
                        else doss.get(sockets.indexOf(s)).writeUTF("login@server@"+unpack.sender+"@refuse");
                    }
                }
            }catch (IOException |NullPointerException a){                                           //连接回收(Warning: 无同步锁)
                System.out.println("接收数据失败"+a.getMessage());
                try{
                    String tempName = null;
                    System.out.println(sockets.size());
                    System.out.println(usernameList.size());
                    System.out.println(doss.size());
                    System.out.println("尝试回收连接"+s);
                    if(usernameList.size()==sockets.size()){
                        tempName = usernameList.get(sockets.indexOf(s));
                        System.out.println("尝试回收昵称"+usernameList.get(sockets.indexOf(s)));
                        usernameList.remove(sockets.indexOf(s));
                    }
                    doss.remove(sockets.indexOf(s));
                    sockets.remove(s);
                    s.close();
                    s = null;
                    din.close();
                    din = null;
                    System.out.println("回收完成");
                    for(int i = 0;i<sockets.size();i++){
                        if(tempName != null)
                        doss.get(i).writeUTF("message@server@"+"All@"+tempName+"离开了群聊");
                        System.out.println(sockets.get(i));
                        System.out.println(usernameList.get(i));
                        System.out.println(doss.get(i));
                    }
                    break;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}