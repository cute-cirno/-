package ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginFrame extends JFrame {
    public static Socket s = null;
    public static boolean loginPassed = false;
    String username;
    JLabel nameNotice = new JLabel("请输入你的昵称");
    JTextField getUsernameInput = new JTextField(10);
    MyButton submit = new MyButton("提交",60,20);
    JLabel notice = new JLabel();


    public LoginFrame(String title){
        super(title);
        JPanel login = new JPanel();
        this.setContentPane(login);

        getUsernameInput.addKeyListener(new MyKeyListener() {
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    check();
                }
            }
        });
        login.add(nameNotice);
        login.add(getUsernameInput);
        login.add(submit);
        login.add(notice);
    }

    class MyButton extends JButton {
        public MyButton(String name, int weight, int height) {
            super(name);
            this.setPreferredSize(new Dimension(weight, height));
            MyActionListener listener = new MyActionListener();
            this.addActionListener(listener);
        }
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == submit){
                check();
            }

        }
    }
    public void check(){                                //用户名校验

        System.out.println("haha");
        username = getUsernameInput.getText();
        if(username.contains("@")||username.equals("")||username.equals("All")) {
            getUsernameInput.setText("不允许使用'@'或'All'为空");
        }else {
            try{
                s = new Socket("localhost",50001);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                System.out.println("新建了链接"+s+"\n"+dataPack.toDataPack("login",username,"server","login"));
                dos.writeUTF(dataPack.toDataPack("login",username,"server","login"));
//                        System.out.println(dataPack.toDataPack("login",username,"server","login"));
                ReceiverForLogin receive = new ReceiverForLogin(s);
                receive.start();
                Thread.sleep(100);
                if(!loginPassed){
//                            s.close();
                    notice.setText("该昵称已被占用");
                }
            }catch (IOException | InterruptedException a) {
                a.printStackTrace();
            }
        }
    }
}