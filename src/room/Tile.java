package room;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Tile {

    private BufferedImage image;
    private boolean collision;

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


    public BufferedImage getSprite(){
        return image;
    }
    public void setSprite(BufferedImage sprite){
        image = sprite;
    }

    public boolean getCollision(){
        return collision;
    }

    public void setCollision(boolean collision){
        this.collision = collision;
    }

}
