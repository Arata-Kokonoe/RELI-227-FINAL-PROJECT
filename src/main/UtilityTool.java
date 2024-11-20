package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class UtilityTool {
    public static BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        
        return scaledImage;
    }

    public static BufferedImage loadSprite(String imgPath){
        BufferedImage image = null;

        try {
            image = ImageIO.read(main.Main.class.getResourceAsStream("/res/" + imgPath + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }
}
