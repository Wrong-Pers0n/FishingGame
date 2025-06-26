package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UtilityTool {


    public BufferedImage loadImage(String filepath) {
        try {
            InputStream in = getClass().getResourceAsStream(filepath);
            return ImageIO.read(in);
        } catch (IOException e) {
            System.err.println("Error: Image could not be loaded. Filepath: "+filepath+" Exception: "+ e);
        }
        return null;
    }
}
