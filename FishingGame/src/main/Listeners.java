package main;

import javax.swing.*;
import java.awt.event.*;

public class Listeners implements ActionListener, MouseListener, KeyListener {

    Main main;



    public Listeners(Main main) {
        this.main = main;


        InputMap im = main.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = main.getActionMap();


        // All inputs that are used:
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up-pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "up-released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "down-pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "down-released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left-pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "left-released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right-pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "right-released");



        // Declarations for what inputs do

        am.put("up-pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.upPressed = true;
            }
        });
        am.put("up-released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.upPressed = false;
            }
        });
        am.put("down-pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.downPressed = true;
            }
        });
        am.put("down-released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.downPressed = false;
            }
        });
        am.put("left-pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.leftPressed = true;
            }
        });
        am.put("left-released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.leftPressed = false;
            }
        });
        am.put("right-pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.rightPressed = true;
            }
        });
        am.put("right-released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.player.rightPressed = false;
            }
        });
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        main.checkClick(e.getX(), e.getY());
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
