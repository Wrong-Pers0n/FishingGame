package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import fish.KoiFish;
import fish.Swarms;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel {

    //JOGLRenderer renderer = new JOGLRenderer(this);

    public int screenWidth = 683;
    public int screenHeight = 384;
    public int playableAreaWidth = screenWidth*3;
    public int playableAreaHeight = screenWidth*5;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public double upscaleBy = screenSize.getWidth()/683;

    public double globalCameraOffsetX = 0;
    public double globalCameraOffsetY = 0;

    Timer gameTimer;
    Timer algorithmTimer;

    static Framework framework;

    UtilityTool tool = new UtilityTool();
    public Player player = new Player(this, upscaleBy, playableAreaWidth, playableAreaHeight);
    Listeners listener = new Listeners(this);
    Background background = new Background(this,upscaleBy,playableAreaWidth,playableAreaHeight);
    UI ui = new UI();

    GL2 gl;



    ArrayList<KoiFish> koiFish = new ArrayList<>();
    ArrayList<Swarms> swarms = new ArrayList<>();

    public static void main(String[] args) {
        framework = new Framework();
    }

    public Main() {

        // Setup OpenGL profile and capabilities
        //GLProfile profile = GLProfile.get(GLProfile.GL2);
        //GLCapabilities caps = new GLCapabilities(profile);
//
        //// Create a canvas to draw on
        //GLCanvas canvas = new GLCanvas(caps);
//
        //// Add this class as the OpenGL event listener
        //canvas.addGLEventListener(this);
        //System.setProperty("sun.java2d.opengl", "true");


        //System.setProperty("sun.java2d.opengl", "true");
        addMouseListener(listener);


    }

    public void setup(GL2 gl) {

        this.gl = gl;

        try {
            System.out.println("Spawning all the fish...");
            for(int i = 0; i < 50; i++) {
                koiFish.add(new KoiFish(playableAreaWidth/2, 200, upscaleBy, screenWidth, screenHeight, this));
            }
            System.out.println("Koi fish successfully spawned.");

            //swarms.add(new Swarms(150, 400, 700, upscaleBy, this));
            swarms.add(new Swarms(150, 500, 500, upscaleBy, this,true));

            swarms.add(new Swarms(150, 350, 3200, upscaleBy, this, false));
            swarms.add(new Swarms(200, 450, 3100, upscaleBy, this, false));
            swarms.add(new Swarms(400, 550, 3000, upscaleBy, this, false));
            swarms.add(new Swarms(200, 650, 3200, upscaleBy, this, false));


            System.out.println("All fish have been successfully spawned");
        } catch (Exception e) {
            System.out.println("Error: Something went wrong with the fish spawning: " + e);
            throw new RuntimeException(e);
        }

        gameTimer = new Timer();
        algorithmTimer = new Timer();

        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long startTime = System.nanoTime();

                set();

                Framework.window.display();
                //repaint();

                double output = (System.nanoTime()-startTime)/1000000.0;
                System.out.println("Total Set Time: " + output + "ms");
            }
        }, 0, 17);
        algorithmTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                algorithmSet();
            }
        }, 0, 51);
    }

    @Override
    public void paint(Graphics gtd) {
        super.paint(gtd);
        Graphics2D g = (Graphics2D) gtd;

        background.drawBackground();

        player.drawPlayer();

        for(KoiFish fish : koiFish) {
            fish.drawFish();
        }

        //background.drawOverlay(upscaleBy);

        for(Swarms swarm : swarms) {
            swarm.draw();
        }

        //ui.drawCoordinates(player);
    }

    private void set() {
        player.movePlayer();

        for(Swarms swarm : swarms) {
            swarm.move();
        }
        for(KoiFish fish : koiFish) {
            fish.moveFish();
        }
    }

    private void algorithmSet() {
        background.calculateOverlayDensity(player.y);

        for(Swarms swarm : swarms) {
            swarm.algorithmSet();
        }
    }

    public void checkClick(int x, int y) {
        for(KoiFish fish : koiFish) {
            fish.checkClick(x,y);
        }
    }

    public void drawImage(Texture tex, int x, int y, int width, int height, float opacity) {
        // Convert image to texture
        tex.enable(gl);
        tex.bind(gl);

        // Enable blending for opacity
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(1f, 1f, 1f, opacity); // Set opacity

        // Draw textured quad
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(x, y);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(x + width, y);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(x + width, y + height);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(x, y + height);
        gl.glEnd();

        tex.disable(gl);
    }

    public void createTextures(GLAutoDrawable gl) {

    }
}