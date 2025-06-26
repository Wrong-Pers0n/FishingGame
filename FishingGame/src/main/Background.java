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
    float lightDensity = 0f;

    int resizeWidth, resizeHeight;

    BufferedImage bufferedImageResult;

    public Background(Main main, double upscaleBy, int resizeWidth, int resizeHeight) {
        this.main = main;
        loadBackground();
        this.upscaleBy = upscaleBy;
        this.resizeHeight = (int) (resizeHeight*upscaleBy);
        this.resizeWidth = (int) (resizeWidth*upscaleBy);

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

    public void drawOverlay(Graphics2D g, double upscaleBy) {
        if(lightDensity != 0f) {
            long startTime = System.nanoTime();
            g.setColor(Color.black);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightDensity));
            g.fillRect(0, 0, (int) Math.ceil(main.screenWidth * upscaleBy), (int) Math.ceil(main.screenHeight * upscaleBy));
            System.out.println("Total Background Draw Time: " + (System.nanoTime() - startTime) / 1000000.0 + "ms");

            System.out.println("Width: " + (int) Math.ceil(main.screenWidth * upscaleBy) + " Height: " + (int) Math.ceil(main.screenHeight * upscaleBy));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void calculateOverlayDensity(double y) {
        if(y < 500) {
            lightDensity = 0f;
            return;
        } else if(y >= 1260) {
            lightDensity = 0.95f;
            return;
        }
        lightDensity = (float) ((y-500) / 800.0);
    }

    private void loadBackground() {
        backgroundImage = main.tool.loadImage("/background/fishingBackground.png");
    }

}
