package the_game.input;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
    public boolean[] key = new boolean[68836];

    public static int MOUSE_X;
    public static int MOUSE_Y;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode > 0 && keyCode < key.length) key[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        key[keyCode] = false;
        //Arrays.fill(key, false);
    }

    @Override
    public void focusLost(FocusEvent e) {
        Arrays.fill(key, false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MOUSE_X = e.getX();
        MOUSE_Y = e.getY();
    }
}
