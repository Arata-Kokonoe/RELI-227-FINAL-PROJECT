//  PACKAGE
package entities;

import java.awt.Color;
//=================================================================================================================
//  IMPORTS
    import java.awt.Graphics2D;
    import java.awt.Rectangle;
    import java.awt.image.BufferedImage;

    import javax.imageio.ImageIO;

    import core.Position;
    import core.Size;
    import main.Camera;
//=================================================================================================================
import main.GameState;


//=================================================================================================================
public abstract class Entity {
//=================================================================================================================


    //=============================================================================================================
    //  MEMBER VARIABLES
        protected Position position;
        protected Size size;
        protected BufferedImage sprite;
    //=============================================================================================================


    //=============================================================================================================
    //  ABSTRACT METHODS
        public abstract void update(GameState gameState);
        public abstract void draw(Graphics2D g2, Camera camera);
        public abstract void setSpriteSheet();
    //=============================================================================================================


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


    //=============================================================================================================
        public boolean collidesWith(Entity other){
            return this.getHitbox().collidesWith(other.getHitbox());
        } // collidesWith(Entity other)
    //  Returns a boolean that tells whether or not an entity is colliding with another.
    //=============================================================================================================


    public void drawHitboxOnCamera(Graphics2D g2, Camera camera){
        this.getHitbox().drawOnCamera(g2, camera);
    }

    public void drawHitbox(Graphics2D g2){
        g2.setColor(Color.RED);
        this.getHitbox().draw(g2);
    }

    //=============================================================================================================
    //  GETTERS
        public Hitbox getHitbox(){
            return new Hitbox(new Rectangle(position.intX(), position.intY(), size.getWidth(), size.getHeight()), this);
        }

        public Size getSize(){
            return size;
        }

        public Position getPosition(){
            return position;
        }
    //=============================================================================================================


//=================================================================================================================
}   // class Entity
//=================================================================================================================
