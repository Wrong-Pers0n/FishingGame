package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Background {

    Main main;
    private BufferedImage backgroundImage;
    private int displayX, displayY;
    double upscaleBy;

    int resizeWidth, resizeHeight;

    BufferedImage bufferedImageResult;

    public Background(Main main, double upscaleBy, int resizeWidth, int resizeHeight) {
        this.main = main;
        loadBackground();
        this.upscaleBy = upscaleBy;
        this.resizeHeight = resizeHeight*2;
        this.resizeWidth = resizeWidth*2;

        bufferedImageResult = new BufferedImage(
                this.resizeWidth,
                this.resizeHeight,
                backgroundImage.getType()
        );

    }


    public void drawBackground(Graphics2D g) {
        displayX = (int) Math.round((main.screenWidth/2.0-main.globalCameraOffsetX)*upscaleBy);
        displayY = (int) Math.round((main.screenHeight/2.0-main.globalCameraOffsetY)*upscaleBy);

        //Graphics2D g2d = bufferedImageResult.createGraphics();
        g.drawImage(
                backgroundImage,
                displayX,
                displayY,
                resizeWidth,
                resizeHeight,
                null
        );


        //g.drawImage(backgroundImage, displayX, displayY, null);

    }

    public void drawOverlay(Graphics2D g, float lightDensity, double upscaleBy) {
        if(lightDensity != 0f) {
            long startTime = System.nanoTime();
            g.setColor(Color.black);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightDensity));
            g.fillRect(0, 0, (int) Math.ceil(main.screenWidth*upscaleBy), (int) Math.ceil(main.screenHeight*upscaleBy));
            System.out.println("Total Background Draw Time: " + (System.nanoTime()-startTime)/1000000.0 + "ms");

            System.out.println("Width: "+(int) Math.ceil(main.screenWidth*upscaleBy)+" Height: "+(int) Math.ceil(main.screenHeight*upscaleBy));
        }
    }

    private void loadBackground() {
        backgroundImage = getImage("/background/fishingBackground.png");
    }
    private BufferedImage getImage(String filepath) {
        try {
            InputStream in = getClass().getResourceAsStream(filepath);
            return ImageIO.read(in);
        } catch (IOException e) {
            System.err.println("Error: Image could not be loaded. Filepath: "+filepath+" Exception: "+ e);
        }
        return null;
    }
}
