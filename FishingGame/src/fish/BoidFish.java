package fish;

import main.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BoidFish extends ParentFish{

    Main main;

    Random random = new Random();
    double x;
    double y;
    boolean movingLeft = true;
    double upscaleBy;
    int screenWidth;
    double uniqueMoveSpeed = random.nextDouble(0.5,3);
    int direction = random.nextInt(0, 360);
    //int direction = 52;
    double directionOffest = 0;

    // The bigger the number, the lower the weight!! (ik it makes no sense)
    double cohesionWeight = 8;
    double seperationWeight = 15;
    double alignmentWeight = 10;


    int frameCount = 0;
    double cohesionDir = 0;
    double alignmentDir= 0;
    double separationDir= 0;
    double vx = 0;
    double vy = 0;



    public BoidFish(int x, int y, double upscaleBy, int screen, int height, Main main) {
        this.upscaleBy = upscaleBy;
        this.screenWidth = screen;

        this.main = main;

        this.x = x+random.nextInt(-50, 50);
        this.y = y+random.nextInt(-50, 50);

    }


// delete this comment <3
    public void drawFish(Graphics2D g) {
        g.setColor(Color.cyan);
        int scaledX = (int) Math.round((x + main.screenWidth/2.0-main.globalCameraOffsetX)*upscaleBy);
        int scaledY = (int) Math.round((y + main.screenHeight/2.0-main.globalCameraOffsetY)*upscaleBy);
        g.fillOval(scaledX,scaledY, 6, 6);
    }


    public void moveFish(ArrayList<SwarmingFish> fishes) {




        double influenceDir = Math.atan2(vy, vx);
        double influenceDeg = Math.toDegrees(influenceDir);
        if (influenceDeg < 0) influenceDeg += 360;


        double diff = influenceDeg - direction;
        if (diff > 180) diff -= 360;
        if (diff < -180) diff += 360;

        int turningClamp = (y > 300 || y < 60) ? 120 : 30;
        direction += (int) Math.round(directionOffest = Math.max(-turningClamp, Math.min(turningClamp, diff / turningClamp)));

        if (direction >= 360) direction -= 360;
        else if (direction < 0) direction += 360;

        double radians = Math.toRadians(direction);
        x += Math.sin(radians);
        y -= Math.cos(radians);

        if (x < 0) x += 683;
        else if (x > 683) x -= 683;

        if (y < 70) y += 314;
        else if (y > 384) y -= 314;


    }

    private void runAlgorithm(ArrayList<SwarmingFish> fishes) {

    }

    @Override
    void interact() {

    }

    @Override
    boolean checkClick(int x, int y) {
        return false;
    }

    public int averageLocation(ArrayList<BoidFish> list) {

        double averageX = 0;
        double averageY = 0;

        for(BoidFish fish : list) {
            averageX += fish.x;
            averageY += fish.y;
        }


        averageX /= list.size();
        averageY /= list.size();



        //double averageX = 300;
        //double averageY = 150;

        //if(averageY > 300) {
        //    averageY = 300;
        //} else if(averageY < 100) {
        //    averageY = 100;
        //}

        int solution = (int) Math.round(Math.toDegrees(Math.atan2( averageY - y, averageX - x)));

        return solution;
    }

    public int averageDirection(ArrayList<BoidFish> list) {
        double sumSin = 0;
        double sumCos = 0;

        for (BoidFish fish : list) {
            double radians = Math.toRadians(fish.direction);
            sumSin += Math.sin(radians);
            sumCos += Math.cos(radians);
        }

        double avgRadians = Math.atan2(sumSin, sumCos);
        int averageDirection = (int) Math.toDegrees(avgRadians);
        if (averageDirection < 0) averageDirection += 360;

        return averageDirection;
    }

    public int seperationDirection(ArrayList<BoidFish> list) {
        double sumX = 0;
        double sumY = 0;
        int count = 0;
        // Tweak this for more precise stuff btw
        double separationRadius = 20;

        for (BoidFish fish : list) {
            if (fish == this) continue;
            double dx = x - fish.x;
            double dy = y - fish.y;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < separationRadius && dist > 0) {
                sumX += dx / dist;
                sumY += dy / dist;
                count++;
            }


        }

        if (count == 0) return direction;
        double avgX = sumX / count;
        double avgY = sumY / count;

        double avoidAngle = Math.toDegrees(Math.atan2(avgY, avgX));
        if (avoidAngle < 0) avoidAngle += 360;

        return (int) avoidAngle;
    }


    public void cauculateAlgorithm(ArrayList<BoidFish> boidFish) {
        //frameCount++;
        //if(frameCount % 10 == 0) {
        //frameCount = 1;

        cohesionDir = Math.toRadians(averageLocation(boidFish));
        alignmentDir = Math.toRadians(averageDirection(boidFish));
        separationDir = Math.toRadians(seperationDirection(boidFish));

        vx = Math.cos(cohesionDir) / cohesionWeight + Math.cos(alignmentDir) / alignmentWeight + Math.cos(separationDir) / seperationWeight;
        vy = Math.sin(cohesionDir) / cohesionWeight + Math.sin(alignmentDir) / alignmentWeight + Math.sin(separationDir) / seperationWeight;
        //}
    }
}
