package fish;

import main.Main;

import java.awt.*;
import java.util.ArrayList;

public class Swarms {

    ArrayList<SwarmingFish> swarmingFish = new ArrayList<>();
    double x, y;
    Main main;
    double swarmAvoidDistance = 150;
    int defaultSwarmMoveWeight = 1;
    int swarmMoveWeight = defaultSwarmMoveWeight;
    double movementAngle = 0;

    double steerX,steerY;
    double moveSpeed = 1/60.0;

    boolean canMove = true;
    boolean glows = false;
    double distance;

    int swarmSize;

    public Swarms(int amount, double swarmX, double swarmY, double upscaleBy, Main main, boolean canMove) {
        this.main = main;
        swarmSize = amount;
        System.out.println("Attempting to spawn " + amount + " fish.");
        this.x = swarmX;
        this.y = swarmY;
        this.canMove = canMove;
        for(int i = 0; i < amount; i++) {
            swarmingFish.add(new SwarmingFish(main, upscaleBy,x,y));
        }
        System.out.println("Successfully spawned "+amount+" fish");
    }

    public void draw(Graphics2D g) {
        long startTime = System.nanoTime();
        if(distance > main.screenWidth) return;
        for(SwarmingFish fish : swarmingFish) {
            fish.drawFish(g,glows);
        }
        double output = (System.nanoTime()-startTime)/1000000.0;
        //System.out.println("Total Draw Time of "+swarmSize+" Fish: " + output + "ms");

    }

    public void move() {
        double xDifference = main.player.x - x;
        double yDifference = main.player.y - y;
        distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);
        if(distance > main.screenWidth*1.2) return;


        for(SwarmingFish fish : swarmingFish) {
            fish.moveFish(x,y);
        }

        if(!canMove) return;
        if(distance < swarmAvoidDistance) {
            double moveDirection = Math.atan2(steerY,steerX);
            x += (Math.cos(moveDirection)*((swarmAvoidDistance-distance)/1000.0));
            y += (Math.sin(moveDirection)*((swarmAvoidDistance-distance)/1000.0));
        }

    }

    public void algorithmSet() {
        if(distance > main.screenWidth*1.2) return;

        if(distance < swarmAvoidDistance*1.5) glows = true;
        else glows = false;

        for(SwarmingFish fish : swarmingFish) {
            fish.algorithmSet(x,y);
        }

        if(!canMove) return;
        moveAwayFromPlayer();
    }

    void moveAwayFromPlayer() {
        double xDifference = main.player.x - x;
        double yDifference = main.player.y - y;

        double distance = Math.sqrt(xDifference*xDifference + yDifference*yDifference);
        if(distance < swarmAvoidDistance) {
            swarmMoveWeight = defaultSwarmMoveWeight;
            movementAngle = Math.atan2(y - main.player.y, x - main.player.x);
        } else {
            swarmMoveWeight = 0;
        }

        steerX = Math.cos(movementAngle) * swarmMoveWeight;
        steerY = Math.sin(movementAngle) * swarmMoveWeight;
    }
}
