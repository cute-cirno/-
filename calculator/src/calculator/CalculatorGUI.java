package calculator;

import javax.swing.*;

public class CalculatorGUI {
    public static void main(String[] args){
        CFrame frame = new CFrame("Calculator");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭响应

        frame.setSize(440, 500);

        frame.setVisible(true);
    }
}
