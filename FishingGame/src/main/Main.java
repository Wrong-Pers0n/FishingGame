package main;

import fish.KoiFish;
import fish.Swarms;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel  {

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

    UtilityTool tool = new UtilityTool();
    public Player player = new Player(this, upscaleBy, playableAreaWidth, playableAreaHeight);
    Listeners listener = new Listeners(this);
    Background background = new Background(this,upscaleBy,playableAreaWidth,playableAreaHeight);
    UI ui = new UI();


    ArrayList<KoiFish> koiFish = new ArrayList<>();
    ArrayList<Swarms> swarms = new ArrayList<>();

    public static void main(String[] args) {
        new Framework();
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
                //long startTime = System.nanoTime();

                set();
                repaint();

                //double output = (System.nanoTime()-startTime)/1000000.0;
                //System.out.println("Total Set Time: " + output + "ms");
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

        background.drawBackground(g);

        player.drawPlayer(g);

        for(KoiFish fish : koiFish) {
            fish.drawFish(g);
        }

        background.drawOverlay(g, upscaleBy);

        for(Swarms swarm : swarms) {
            swarm.draw(g);
        }

        ui.drawCoordinates(g,player);
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
}