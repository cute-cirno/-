package ChatClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class MyKeyListener implements KeyListener {                                //方法遮盖
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
}
