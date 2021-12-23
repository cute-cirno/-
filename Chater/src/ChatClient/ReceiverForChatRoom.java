package ChatClient;


import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiverForChatRoom extends Thread{                            //群聊私聊接收线程
    JTextArea textArea;
    JTextArea onlineArea;
    Socket s;
    DataInputStream din;
    String name;
    public ReceiverForChatRoom(Socket s, JTextArea textArea, JTextArea onlineArea, String name){
        this.onlineArea = onlineArea;
        this.s = s;
        this.textArea = textArea;
        this.name = name;
    }

    public void run(){
        try{
            din = new DataInputStream(s.getInputStream());
            while (true) {//"type@sender@receiver@content"
                Thread.sleep(100);
                String tmp = din.readUTF();
                if (tmp.split("@")[0].equals("message")) {
                    System.out.println(tmp);
                    if(tmp.split("@")[2].equals("All"))
                    {
                        String name;
                        String content;
                        name = tmp.split("@")[1];
                        content = tmp.substring(tmp.indexOf("@") + name.length() + 3 + tmp.split("@")[2].length());
                        System.out.println(name + "：" + content);
                        textArea.append(name + "：" + cutString(content) + "\n");
                    }else {
                        String name;
                        String content;
                        name = tmp.split("@")[1];
                        content = tmp.substring(tmp.indexOf("@") + name.length() + 3 + tmp.split("@")[2].length());
                        System.out.println(name + "：" + content+"(私聊)");
                        textArea.append(name + "：" + cutString(content) + "(私聊)\n");
                    }
                }else if (tmp.split("@")[0].equals("loginRefresh")){
                    DataUnpack data = new DataUnpack(tmp);
                    onlineArea.setText("");
                    onlineArea.append("在线人数:"+data.getUserList().length);
                    for(String o:data.getUserList()){
                        System.out.println(o);
                        onlineArea.append("\n"+o);
                    }
                }
            }
        }catch (IOException | InterruptedException e) {
            System.out.println("接收数据失败" + e.getMessage());
        }
    }

    public String cutString(String content){
        int i = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("\n        ");
        if(content.length()>15){
            while (content.substring(i).length()>15){
                sb.append(content.substring(i,i+15)+"\n        ");
                i += 15;
            }
            sb.append(content.substring(i));
        }else {
            sb.append(content);
        }
        return sb.toString();
    }

    class MyButton extends JButton {
        public MyButton(String name, int weight, int height) {
            super(name);
            this.setPreferredSize(new Dimension(weight, height));
            this.addActionListener((e -> {//监听用户操作并计算
                try {

                } catch (Exception a) {
                }
            }));
        }
    }
}