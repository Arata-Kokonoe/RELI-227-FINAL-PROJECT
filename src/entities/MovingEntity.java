package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import core.MoveOrder;
import core.Vector2D;
import main.Camera;
import main.GameState;
import room.Room;

public abstract class MovingEntity extends Entity{

    //=============================================================================================================
    //  MEMBER VARIABLES
        protected Vector2D velocity;
        protected int speed;
        protected boolean up, down, left, right;
        protected BufferedImage animationSheet;
        protected AnimationManager animationManager;
        protected MoveOrder moveOrder;
    //=============================================================================================================


    //=============================================================================================================
    //  ABSTRACT METHODS
        @Override
        public void update(GameState gameState){
            setMovement(gameState);

            int deltaX = 0;
            int deltaY = 0;
        
            if(left) deltaX--;
            if(right) deltaX++;
            if(up) deltaY--;
            if(down) deltaY++;

            velocity = new Vector2D(deltaX, deltaY);
            
            velocity.multiply(speed);

            handleCollisions(gameState.getCollidingGameObjects(this.getHitbox()), gameState);
            
            animationManager.update();
        }

            @Override
        public void draw(Graphics2D g2, Camera camera) {
            g2.drawImage(
                animationManager.getSprite(),
                this.getPosition().intX() - this.getSize().getWidth()/4  - camera.getPosition().intX(),
                this.getPosition().intY() - (int)(this.getSize().getHeight()/1.8) - camera.getPosition().intY(),
                null
                ); 
            /*g2.drawImage(
                animationManager.getSprite(),
                this.getPosition().intX() - this.getSize().getWidth()/4 ,
                this.getPosition().intY() - (int)(this.getSize().getHeight()/1.8),
                null
                );
            drawToMove(g2);
            drawHitboxOnCamera(g2, camera);
            drawHitbox(g2);*/
        }

        public void drawHitboxOnCamera(Graphics2D g2, Camera camera){
            this.getHitbox().drawOnCamera(g2, camera);
        }

        public void drawHitbox(Graphics2D g2){
            g2.setColor(Color.RED);
            this.getHitbox().draw(g2);
        }

        public void drawToMove(Graphics2D g2){
            g2.setColor(Color.BLUE);
            this.getHitbox().apply(new Vector2D(velocity.getX(), velocity.getY())).draw(g2);
        }

        public boolean isMoving(){
            if (this.velocity.length() > 0) return true;
            else return false;
        }

        protected abstract void setMovement(GameState gameState);
        protected abstract void handleCollisions(List<Entity> collided, GameState gameState);
    //=============================================================================================================


    //=============================================================================================================
    //  GETTERS
        public BufferedImage getAnimationSheet(){
            return animationSheet;
        }

        public MoveOrder getMoveOrder(){
            return moveOrder;
        }

        public Vector2D getVelocity(){
            return velocity;
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
    
            return "null";
        }
    //=============================================================================================================

}
