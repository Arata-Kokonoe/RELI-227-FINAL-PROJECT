package room;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

import javax.imageio.ImageIO;

public class Tile {

    public BufferedImage image;
    public boolean collision;

    public Tile(){
        image = setup("/tiles/blank_tile");
        collision = false;
    }

    //=============================================================================================================
        protected BufferedImage setup(String imagePath){

            BufferedImage image = null;

            try {
                image = ImageIO.read(getClass().getResourceAsStream("/res" + imagePath + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return image;
        } // setup(String imagePath)
    //  Reads an image from specified image path in res folder and returns it.
    //=============================================================================================================


    public BufferedImage getImage(){
        return image;
    }
}
