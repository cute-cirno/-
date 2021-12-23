package ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CFrame extends JFrame {
    Socket s = null;
    String username;
    DataInputStream din;
    DataOutputStream dos;
    JPanel root = new JPanel();
    JPanel northPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel westPanel = new JPanel();
    JPanel southPanel = new JPanel();

    JCheckBox messageType = new JCheckBox("群发");

    JLabel serverReceiver = new JLabel("All");



    JTextArea dialogue= new JTextArea(22,38);
    JTextArea onlineArea = new JTextArea(22,12);

    JTextField receiver = new JTextField(10);
    JTextField input = new JTextField(30);
    JButton submit = new MyButton("发送",70,20);

    public void init(){                                         //面板初始化
        this.setContentPane(root);
        root.setLayout(new BorderLayout());

        MyActionListener listenr = new MyActionListener();

        root.add(northPanel, BorderLayout.NORTH);
        northPanel.setPreferredSize(new Dimension(750, 50));
        root.add(eastPanel, BorderLayout.EAST);
        eastPanel.setPreferredSize(new Dimension(150, 450));
        root.add(westPanel, BorderLayout.WEST);
        root.add(southPanel,BorderLayout.SOUTH);
        southPanel.setPreferredSize(new Dimension(750, 50));

        JPanel centerPanel = new JPanel();
        root.add(centerPanel,BorderLayout.CENTER);
//            centerPanel.add(dialogue);
        JScrollPane jsp = new JScrollPane(dialogue,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(jsp);

        JScrollPane jsp1 = new JScrollPane(onlineArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        eastPanel.add(jsp1);


//            messageType.setPreferredSize(new Dimension(50,20));
        messageType.addActionListener(listenr);

        receiver.setVisible(false);
        messageType.setSelected(true);
        southPanel.add(new JLabel("收信人:"));
        southPanel.add(serverReceiver);
        southPanel.add(receiver);
        southPanel.add(messageType);
        southPanel.add(input);
        southPanel.add(submit);


        westPanel.setPreferredSize(new Dimension(150, 450));

        MyButton mainRoom = new MyButton("主聊天室", 150, 30);
        westPanel.add(mainRoom);

        JLabel statueBar = new JLabel("昵称："+username+"             服务器:127.0.01:50000");
        statueBar.setFont(new Font("宋体", Font.BOLD, 16));
        statueBar.setPreferredSize(new Dimension(700, 50));
        statueBar.setHorizontalAlignment(SwingConstants.LEFT);
        northPanel.add(statueBar);

        input.addKeyListener(new MyKeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar()=='\n') {
                    try {
                        send();
                        clear();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    public CFrame(String tittle,Socket s,String username) {
        super(tittle);
        this.s = s;
        this.username = username;
        init();
        ReceiverForChatRoom receiver = new ReceiverForChatRoom(s,dialogue,onlineArea,username); //根据用户名创建接收线程
        receiver.start();
    }

    class MyButton extends JButton {
        public MyButton(String name, int weight, int height) {
            super(name);
            this.setPreferredSize(new Dimension(weight, height));
            this.addActionListener((e -> {//监听用户操作并计算
                try {
                    if(e.getSource()==submit){
                        send();
                        clear();
                    }
                } catch (Exception a) {
                }
            }));
        }
    }

    public void send() throws IOException {                 //消息发送逻辑
        if(!input.getText().equals(""))
            if(messageType.isSelected()){
                System.out.println(input.getText());
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(dataPack.toDataPack("message",ChatClientGUI.username,"All",input.getText()));
            }else {
                System.out.println(input.getText());
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(dataPack.toDataPack("message",ChatClientGUI.username,receiver.getText(),input.getText()));
                dialogue.append("\n你对"+receiver.getText()+"说:"+input.getText()+"\n");
            }
    }
    public void clear(){
        input.setText("");
    }           //输入框清空

    class MyActionListener implements ActionListener{   //切换群聊私聊
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==messageType){
                if(messageType.isSelected()){
                    serverReceiver.setVisible(true);
                    receiver.setVisible(false);
                }else {
                    receiver.setVisible(true);
                    serverReceiver.setVisible(false);
                    receiver.setText("");
                }
            }
        }
    }
}