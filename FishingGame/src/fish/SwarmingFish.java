package fish;


import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import main.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SwarmingFish extends ParentFish {

    Main main;
    Random random = new Random();
    double x = 0, y = 0;
    double upscaleBy;
    double swarmOffsetX, swarmOffsetY;
    double width = 3;
    double height = 1.5;

    double angle = random.nextInt(0,360);
    double angleTowardsCenter = 0;
    double avoidAngle = 0;
    int avoidDistance = 90 + random.nextInt(-15,15);
    double steerX = 0, steerY = 0;

    double swarmCenterWeight = 3;
    double avoidPlayerWeight = 5;

    double codeAvoidPlayerWeight = avoidPlayerWeight;

    double moveSpeed = 15;
    double turnSpeed = random.nextDouble(2,3);

    int currentFrame = 0;
    int upscaledWidth;
    int upscaledHeight;
    double distance;
    int upscaledX;
    int upscaledY;
    BufferedImage fish;
    Texture fishTexture;

    public SwarmingFish(Main main, double upscaleBy, double swarmX, double swarmY) {
        this.main = main;
        this.upscaleBy = upscaleBy;
        this.swarmOffsetX = random.nextInt(-25,25);
        this.swarmOffsetY = random.nextInt(-25,25);
        this.x = this.swarmOffsetX;
        this.y = this.swarmOffsetY;


        upscaledWidth = (int) Math.round(width*upscaleBy);
        upscaledHeight = (int) Math.round(height*upscaleBy);


        double xDifference = main.player.x - x;
        double yDifference = main.player.y - y;
        steerFromPlayer(Math.sqrt(xDifference * xDifference + yDifference * yDifference));

        if(Math.abs(x - swarmX + swarmOffsetX) > 200 || Math.abs(y - swarmY + swarmOffsetY ) > 200) {
            x = swarmX + swarmOffsetX;
            y = swarmY + swarmOffsetY;
            angle = random.nextInt(0,360);
        }

        fish = new BufferedImage(upscaledWidth, upscaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = fish.createGraphics();
        // Disable antialiasing if not needed
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setColor(Color.CYAN);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0, 0, upscaledWidth, upscaledHeight);
        g.dispose();

    }



    public void drawFish(boolean glows) {
        if(distance > main.screenWidth) return;

        upscaledX = (int) ((x + main.screenWidth/2-main.globalCameraOffsetX)*upscaleBy);
        upscaledY = (int) ((y + main.screenHeight/2-main.globalCameraOffsetY)*upscaleBy);

        main.drawImage(fishTexture,upscaledX,upscaledY,upscaledWidth, upscaledHeight, 1f);

        //g.drawImage(fish,upscaledX,upscaledY, null);
    }

    @Override
    public void interact() {
// delete this comment <3
    }

    @Override
    public boolean checkClick(int x, int y) {
        return false;
    }

    public void moveFish(double swarmX, double swarmY) {

        double xDifference = main.player.x - x;
        double yDifference = main.player.y - y;
        distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);
        if(distance > main.screenWidth*1.1) return;

        double moveDirection = Math.atan2(steerY,steerX);
        double moveDegrees = Math.toDegrees(moveDirection);
        if (moveDegrees < 0) moveDegrees += 360;

        double difference = moveDegrees - angle;
        if (difference > 180) difference -= 360;
        if (difference < -180) difference += 360;


        if (difference > turnSpeed) difference = turnSpeed;
        if (difference < -turnSpeed) difference = -turnSpeed;
        angle += difference;

        if (angle >= 360) angle -= 360;
        else if (angle < 0) angle += 360;

        double radians = Math.toRadians(angle);
        x += (Math.cos(radians)*moveSpeed/30);
        y += (Math.sin(radians)*moveSpeed/30);




    }

    public void algorithmSet(double swarmX, double swarmY) {
        if(distance > main.screenWidth*1.1) return;

        currentFrame++;
        if(currentFrame % 30 == 0) {
            currentFrame = 0;
            avoidDistance = 90+random.nextInt(-15,15);
        }

        calculateAngleForSwarmCenter(swarmX, swarmY);
        steerFromPlayer(distance);

        steerX = Math.cos(angleTowardsCenter) * swarmCenterWeight + Math.cos(avoidAngle) * codeAvoidPlayerWeight;
        steerY = Math.sin(angleTowardsCenter) * swarmCenterWeight + Math.sin(avoidAngle) * codeAvoidPlayerWeight;
    }

    void calculateAngleForSwarmCenter(double swarmX, double swarmY) {
        angleTowardsCenter = Math.atan2(swarmY + swarmOffsetY - y, swarmX + swarmOffsetX - x);
    }

    void steerFromPlayer(double distance) {
        if(distance < avoidDistance) {
            codeAvoidPlayerWeight = avoidPlayerWeight;
            avoidAngle = Math.toDegrees(Math.atan2(y - main.player.y, x - main.player.x));
            if(avoidAngle >= 360) avoidAngle -= 360;
            else if(avoidAngle < 0) avoidAngle += 360;
            avoidAngle = Math.toRadians(avoidAngle);
        } else {
            codeAvoidPlayerWeight = 0;
        }
    }

    public void initFish(GLAutoDrawable gl) {
        GLProfile profile = gl.getGLProfile();
        fishTexture = AWTTextureIO.newTexture(profile, fish, true);
    }
}
