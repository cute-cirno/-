package ChatClient;

import javax.swing.*;


public class ChatClientGUI {
    public static String username;
    public static void main(String[] args){


        JFrame frame = new LoginFrame("聊天室入口");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭响应
        frame.setSize(150, 150);
        frame.setLocation(800,400);

        frame.setVisible(true);


        while (true){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(LoginFrame.loginPassed){             //循环校验用户名是否通过
                JFrame frame2 = new CFrame("聊天室",LoginFrame.s,username);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭响应
                frame2.setSize(750, 500);
                frame2.setLocation(550,300);
                frame2.setVisible(true);
                frame.setVisible(false);
                break;
            }
        }


    }
}
 