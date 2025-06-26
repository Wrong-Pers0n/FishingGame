package fish;

import main.Main;

import java.awt.*;
import java.util.Random;

public class KoiFish extends ParentFish {

    Main main;
    double x;
    double y;
    boolean movingLeft = true;
    double upscaleBy;
    int screenWidth;
    double uniqueMoveSpeed;
    Random random = new Random();
    boolean clickedOn = false;
    boolean isMovingUp = true;
    double swimYOffset = 0;
    double swimYOffsetMultiplier = 1;

    public KoiFish(int x, int y, double upscaleBy, int screen, int height, Main main) {
// delete this comment <3
        int[] details = randomSpawn(screen, height);

        this.main = main;

        this.x = (double) details[0];
        this.y = (double) details[1];
        if(details[2] == 1) movingLeft = true;
        else movingLeft = false;

        this.upscaleBy = upscaleBy;
        this.screenWidth = screen;
        uniqueMoveSpeed = random.nextDouble(0.5,3);
        swimYOffset = random.nextInt(-25, 25);
        isMovingUp = random.nextBoolean();
        swimYOffsetMultiplier = random.nextInt(23,33);
    }


    public void drawFish() {

        //if(!clickedOn) { g.setColor(Color.red); }
        //else { g.setColor(Color.black); }

        int scaledX = (int) Math.round((x + main.screenWidth/2.0-main.globalCameraOffsetX)*upscaleBy);
        int scaledY = (int) Math.round((swimYOffset+y + main.screenHeight/2.0-main.globalCameraOffsetY)*upscaleBy);



        //g.fillOval(scaledX,scaledY, 30, 30);
    }


    public void moveFish() {
        /*
        if(Math.abs(swimYOffset) > 15) {
            if(isMovingUp) swimYOffset+= 0.5;
            else swimYOffset-=0.5;
        } else {
            if(isMovingUp) swimYOffset++;
            else swimYOffset--;
        }
        */

        swimYOffset = Math.sin(x/swimYOffsetMultiplier)*10;


        //if(Math.abs(swimYOffset) > 30) isMovingUp = !isMovingUp;



        if(movingLeft) x += 1*uniqueMoveSpeed;
        else x-= 1*uniqueMoveSpeed;
        if(x > screenWidth*0.97 || x < screenWidth*0.01) {
            movingLeft = !movingLeft;
        }
    }

    @Override
    void interact() {

    }

    public boolean checkClick(int x, int y) {
        x = (int) Math.round(x/upscaleBy);
        y = (int) Math.round(y/upscaleBy);

        if(Math.abs(this.x - x) < 15 && Math.abs(this.y - y) < 15) {
            return true;
        }
        return false;
    }


    int[] randomSpawn(int width, int height) {
        int x = random.nextInt((int) (width*0.1), (int) (width*0.9));
        int y = random.nextInt((int) (height*0.25), (int) (height*0.9));
        int moveDirection =(int) Math.round(random.nextDouble(0,1));
        return new int[]{x, y, moveDirection};
    }


}
