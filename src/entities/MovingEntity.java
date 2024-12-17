package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Vector2D;
import main.Camera;
import main.GameState;

public abstract class MovingEntity extends Entity{

    //=============================================================================================================
    //  MEMBER VARIABLES
        private Vector2D velocity;
        private int speed;
        protected boolean up, down, left, right;
        protected String direction;
        protected BufferedImage animationSheet;
        protected AnimationManager animationManager;
    //=============================================================================================================


    //=============================================================================================================
    //  ABSTRACT METHODS
        @Override
        public void update(GameState gameState){
            Hitbox tempHitbox = this.getHitbox();

            setMovement(gameState);

            int deltaX = 0;
            int deltaY = 0;
        
            if(left) deltaX--;
            if(right) deltaX++;
            if(up) deltaY--;
            if(down) deltaY++;

            velocity = new Vector2D(deltaX, deltaY);
            velocity.normalize();
            
            velocity.multiply(speed);

            if(velocity.length() > 0){
                
                tempHitbox.apply(velocity);

            }

            handleCollisions(gameState.getCollidingGameObjects(tempHitbox));
            
            animationManager.update(getDirection());
        }

            @Override
        public void draw(Graphics2D g2, Camera camera) {
            g2.drawImage(
                animationManager.getSprite(),
                this.getPosition().intX() - camera.getPosition().intX() - this.getSize().getWidth() / 2,
                this.getPosition().intY() - camera.getPosition().intY() - this.getSize().getHeight() / 2,
                null
                ); 
        }

        protected abstract void setMovement(GameState gameState);
    //=============================================================================================================


    //=============================================================================================================
    //  GETTERS
        public BufferedImage getAnimationSheet(){
            return animationSheet;
        }

        public String getDirection(){
            double x = velocity.getX();
            double y = velocity.getY();
        
            if(x == 0 && y > 0) return "S";
            if(x < 0 && y == 0) return "W";
            if(x == 0 && y < 0) return "N";
            if(x > 0 && y == 0) return "E";
            if(x < 0 && y > 0) return "SW";
            if(x < 0 && y < 0) return "NW";
            if(x > 0 && y < 0) return "NE";
            if(x > 0 && y > 0) return "SE";
    
            return "S";
        }
    //=============================================================================================================

}
