package main;

import java.awt.*;

public class UI {



    public void drawCoordinates(Graphics2D g, Player player) {
        g.setColor(Color.green);
        String coordinates = "X: " + String.valueOf(player.x) + " Y: " + String.valueOf(player.y);
        g.drawString(coordinates, 20, 20);
    }
}
