package main;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.*;

public class Framework {

    static Main panel;
    static GLWindow window;
    double upscaleBy;

    public Framework() {
        // Initialize OpenGL profile and capabilities
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);

        // Create GLWindow
        window = GLWindow.create(caps);
        window.setTitle("Fishing game V0.1");

        // Set resolution based on screen size
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        upscaleBy = screenSize.getWidth() / 683;
        int width = (int) Math.round(683 * upscaleBy);
        int height = (int) Math.round(384 * upscaleBy);
        window.setSize(width, height);
        window.setPosition(
                (int) (screenSize.getWidth() / 2 - width / 2),
                (int) (screenSize.getHeight() / 2 - height / 2)
        );

        // Initialize game logic
        panel = new Main();
        panel.upscaleBy = upscaleBy;

        // Add OpenGL renderer
        window.addGLEventListener(new JOGLRenderer(panel));

        // Key input
        window.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //panel.keyPressed(e.getKeyCode());
            }
        });

        // Show window
        window.setVisible(true);

        // Start animation
        //final com.jogamp.opengl.util.Animator animator = new com.jogamp.opengl.util.Animator(window);
        //animator.start();
    }
}
