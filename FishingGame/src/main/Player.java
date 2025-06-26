package main;

import java.awt.*;

@SuppressWarnings("ALL")
public class Player {

    Main main;

    public double x = 20;
    public double y = 20;
    int displayX = (int) y;
    int displayY = (int) x;
    double upscaleBy;
    int width = 25;
    int height = 25;

    double movingX = 0;
    double movingY = 0;

    double accelSpeed = 0.25;
    double maxSpeed = 2.5;
    double decelSpeed = 20;
    double minSpeed = 0.2;

    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean upPressed = false;
    boolean downPressed = false;

    int playableX;
    int playableY;

    public Player(Main main, double upscaleBy, int playableAreaX, int playableAreaY) {
        this.main = main;
        this.upscaleBy = upscaleBy;

        width = (int) Math.round(width*upscaleBy);
        height = (int) Math.round(height*upscaleBy);

        x = playableAreaX/2;
        this.playableX = playableAreaX;
        this.playableY = playableAreaY;
        y = 20;
        moveCamera();
    }

    public void drawPlayer(Graphics2D g) {

        g.setColor(Color.green);
        g.fillRect(displayX, displayY, width, height);

        g.setColor(Color.red);
        g.fillRect(main.screenWidth, main.screenHeight, 10,10);
    }

    public void movePlayer() {

        if(upPressed) {
            if(movingY > -maxSpeed) {
                movingY -= accelSpeed;
            }
        } else if(downPressed) {
            if(movingY < maxSpeed) {
                movingY += accelSpeed;
            }
        } else {
            movingY -= movingY/decelSpeed;
        }

        if(leftPressed) {
            if(movingX > -maxSpeed) {
                movingX -= accelSpeed;
            }
        } else if(rightPressed) {
            if(movingX < maxSpeed) {
                movingX += accelSpeed;
            }
        } else {
            movingX -= movingX/decelSpeed;
        }

        double speed = Math.sqrt(movingX * movingX + movingY * movingY);

        // only apply the standartization if you are moving too fast
        if(speed > maxSpeed) {
            movingX = (movingX / speed) * maxSpeed;
            movingY = (movingY / speed) * maxSpeed;
        }


        if(speed != 0) {
            x += movingX;
            y += movingY;
        }

        if(movingX < minSpeed && movingX > -minSpeed) movingX = 0;
        if(movingY < minSpeed && movingY > -minSpeed) movingY = 0;

        if(x > main.playableAreaWidth-width/2) {
            x = main.playableAreaWidth-width/2;
            movingX = 0;
        } else if(x < width/2) {
            x = width/2;
            movingX = 0;
        }

        if(y > main.playableAreaHeight-height/2) {
            y = main.playableAreaHeight-height/2;
            movingY = 0;
        } else if(y < height/2) {
            y = height/2;
            movingY = 0;
        }


        moveCamera();
    }

    public void moveCamera() {

        int roundedX = (int) x;
        int roundedY = (int) y;

        main.globalCameraOffsetX = roundedX;
        main.globalCameraOffsetY = roundedY;

        int halfScreenW = main.screenWidth / 2;
        int halfScreenH = main.screenHeight / 2;

        main.globalCameraOffsetX = Math.max(halfScreenW, Math.min(main.globalCameraOffsetX, main.playableAreaWidth - halfScreenW));
        main.globalCameraOffsetY = Math.max(halfScreenH, Math.min(main.globalCameraOffsetY, main.playableAreaHeight - halfScreenH));

        double tempX = Math.round(roundedX - main.globalCameraOffsetX + halfScreenW);
        double tempY = Math.round(roundedY - main.globalCameraOffsetY + halfScreenH);

        if(tempX > main.playableAreaWidth - halfScreenW) tempX = main.playableAreaWidth - halfScreenW;
        if(tempY > main.playableAreaHeight - halfScreenW) tempX = main.playableAreaHeight - halfScreenH;

        displayX = (int) ((tempX-width/2/upscaleBy) * upscaleBy);
        displayY = (int) ((tempY-height/2/upscaleBy) * upscaleBy);


    }
}
