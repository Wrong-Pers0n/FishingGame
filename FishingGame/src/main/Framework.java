package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Framework extends JFrame {

    static Main panel;
    GraphicsDevice graphicsDevice;

    double upscaleBy;

    public Framework() {


        panel = new Main();
        panel.setBackground(Color.BLUE);
        this.setLayout(null); // Use no layout manager to manually set the size and position of the panel


        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();

        if (graphicsDevice.isFullScreenSupported()) {
            setUndecorated(true);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
            setVisible(true);
        }

        this.add(panel);
        resizePanel();



        // Add key listener
        //addKeyListener(new KeyChecker(panel));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizePanel();
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        upscaleBy = screenSize.getWidth()/683;
        int width = (int) Math.round(683*upscaleBy);
        int height = (int) Math.round(384*upscaleBy);

        panel.upscaleBy = upscaleBy;

        setSize(width,height);

        setBackground(Color.green);

        setLocation((int) Math.round((screenSize.getWidth()/2 - getSize().getWidth()/2)), (int) Math.round((screenSize.getHeight()/2 - getSize().getHeight()/2)));


        setResizable(false);
        setTitle("Fishing game V0.1");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.setProperty("sun.java2d.opengl", "true");
    }

    private void resizePanel() {
        if (graphicsDevice.getFullScreenWindow() == this || getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            // Get the current size of the JFrame
            Dimension frameSize = this.getSize();
            panel.setSize(frameSize.width, frameSize.height);
            panel.setLocation(0, 0);

            // Optionally revalidate and repaint
            panel.revalidate();
            panel.repaint();
        }
    }



}






