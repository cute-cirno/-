package calculator;

import javax.swing.*;
import java.awt.*;

public class CFrame extends JFrame {
    JPanel root = new JPanel();                         //主面板
    JLabel inputWindow = new JLabel("");
    JLabel outputLine = new JLabel("");

    boolean lock = false;                               //操作符锁
    boolean elock = false;                              //等于号锁
    boolean init = true;                                //是否是第一次运算(初始化)

    String[] operators = {"null","%","CE","C","<X","1/x","x²","√x","÷","7","8","9","×","4","5","6","-","1","2","3","+","+/-","0",".","="};//所有的按键
    String num ="0123456789.";                          //数据字符串
    String Sv1 = "",operator = "",Sv2 = "",equal = "";  //输出栏字符串
    StringBuffer Sv3 = new StringBuffer("0");           //输入栏字符串

    double v1 = 0,v2,v3=0;                              //运算数据


    public CFrame(String tittle){
        super(tittle);                                  //计算器窗体
        this.setContentPane(root);
        inputWindow.setFont(new Font("Terminal Regular", Font.BOLD,32));
        inputWindow.setPreferredSize(new Dimension(400,34));
        root.add(outputLine);
        outputLine.setPreferredSize(new Dimension(400,20));
        outputLine.setFont(new Font("Terminal Regular", Font.BOLD,14));
        root.add(inputWindow);
        addButton();

        refresh();                                      //初始化刷新
    }
    public void refresh() {                             //刷新
        outputLine.setText(Utils.check(Sv1)+" "+operator+" "+Utils.check(Sv2)+" "+equal);
        inputWindow.setText(Utils.check(Sv3.toString()));
    }
    public void addButton(){
        for(int i = 1;i < 25;i++){
            root.add(new MyButton(operators[i],90,60));
        }
    }


    class MyButton extends JButton{
        public MyButton(String name,int weight,int height){
            super(name);

            this.setPreferredSize(new Dimension(weight,height));
            this.addActionListener((e -> {                  //监听用户操作并计算
                try {                                       //除零错误的捕捉处理
                    calculate(name);
                }catch (Exception a){
                    inputWindow.setText(a.getMessage());
                }
            }));
        }
    }
    public void init(){
        lock = false;
        elock = false;
        v1 = 0;
        v2 = 0;
        v3 = 0;
        Sv1 = "";
        operator = "";
        Sv2 = "";
        equal = "";
        Sv3 = new StringBuffer("0");
        init = true;
    }
    public void calculate(String name)throws Exception{
        if (num.contains(name)){                            //数据按键的处理逻辑
            if(Sv3.toString().equals("0"))
                Sv3 = new StringBuffer();
            if(!elock){
                if(!lock){
                    Sv3.append(name);
                    v3 = Double.parseDouble(Sv3.toString());
                }
                else {
                    Sv3 = new StringBuffer();
                    Sv3.append(name);
                    v3 = Double.parseDouble(Sv3.toString());
                    lock = false;
                }
            }
            else{
                init();
                Sv3 = new StringBuffer(name);
                v3 = Double.parseDouble(Sv3.toString());
            }
        }
        else{
            switch (name){                                  //操作符按键的处理逻辑
                case "+":
                case "-":
                case "×":
                case "÷":
                    if(!elock){
                        if(!lock){
                            if(init){
                                v3 = Double.parseDouble(Sv3.toString());
                                Sv1 = Sv3.toString();
                                v1 = v3;
                                init = false;
                            }
                            else {
                                v3 = Double.parseDouble(Sv3.toString());
                                v1 = Operator.calculate(operator,v1,v3);
                                Sv1 = String.valueOf(v1);
                                v3 = v1;
                                Sv3 = new StringBuffer(String.valueOf(v3));
                            }
                        }
                        operator = name;
                    }
                    else {
                        elock = false;
                        operator = name;
                        equal = "";
                        Sv2 = "";
                        v2 = 0;
                        Sv1 = Sv3.toString();
                        v1 = Double.parseDouble(Sv1);
                    }
                    lock = true;break;
                case "=":                               //等号逻辑单独处理
                    if(!elock){
                        if(lock){
                            equal = "=";
                            Sv2 = Sv1;
                            v2 = Double.parseDouble(Sv2);
                            Sv1 = Sv3.toString();
                            v1 = Double.parseDouble(Sv3.toString());
                            v3 = Operator.calculate(operator,v1,v2);
                            Sv3 = new StringBuffer(String.valueOf(v3));
                            lock = false;
                        }
                        else {
                            equal = "=";
                            Sv2 = Sv3.toString();
                            v2 = Double.parseDouble(Sv2);
                            v3 = Operator.calculate(operator,v1,v2);
                            Sv3 = new StringBuffer(String.valueOf(v3));
                        }
                        elock = true;
                    }
                    else{
                        v1 = v3;
                        Sv1 = String.valueOf(v1);
                        equal = "=";
                        v3 = Operator.calculate(operator,v1,v2);
                        Sv3 = new StringBuffer(String.valueOf(v3));
                    }break;
                case "C":                                   //初始化
                    init();break;
                case "<X":
                    if(!Sv3.toString().equals("0")&&!elock&&!lock){
                        Sv3 = new StringBuffer(Sv3.substring(0,Sv3.length()-1));
                        if(Sv3.isEmpty())
                            Sv3.append("0");
                        v3 = Double.parseDouble(Sv3.toString());
                    }break;
                case "CE":
                    Sv3 = new StringBuffer("0");
                    v3 = Double.parseDouble(Sv3.toString());break;
                case "%":
                    Sv3 = new StringBuffer(String.valueOf(v3 = v3/100.0));break;
                case "√x":
                    Sv3 = new StringBuffer(String.valueOf(v3 = Operator.sqrt(v3)));break;
                case "x²":
                    Sv3 = new StringBuffer(String.valueOf(v3 = Operator.square(v3)));break;
                case "1/x":
                    Sv3 = new StringBuffer(String.valueOf(v3 = Operator.divide(1,v3)));break;
                case "+/-":
                    Sv3 = new StringBuffer(String.valueOf(v3 = Operator.multiply(-1,v3)));break;
            }
        }
        refresh();
    }
}
