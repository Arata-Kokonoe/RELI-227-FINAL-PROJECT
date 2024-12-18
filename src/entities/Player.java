package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.GlyphMetrics;
import java.awt.image.BufferedImage;
import java.util.List;

import core.MoveOrder;
import core.Position;
import core.Size;
import core.Vector2D;
import main.Camera;
import main.GameFrame;
import main.GameLoop;
import main.GameState;
import main.Input;
import main.UtilityTool;
import room.Tile;

public class Player extends MovingEntity {

    public Player(){
        position = new Position(50, 50);
        size = new Size(GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE*2);
        speed = 2;
        animationSheet = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        setSpriteSheet();
        animationManager = new AnimationManager(this);
        moveOrder = new MoveOrder();
    }

    @Override
    protected void handleCollisions(Hitbox toMove, List<Entity> collided, Tile[][] roomArr) {
        //deal with tile collisions
        String direction = getDirection();
        System.out.println(direction);
        
        //first, check if we will collide into a tile that has collision based on where we want to move
        Tile topLeft = roomArr[(int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
        Tile topRight = roomArr[(int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
        Tile botLeft = roomArr[(int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
        Tile botRight = roomArr[(int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
        if( topLeft.getCollision() ||
            topRight.getCollision() ||
            botLeft.getCollision() ||
            botRight.getCollision()){
                
                //then, do something different based on direction we were moving

                //if N or S, make our vertical velocity 0 so we stop moving upwards/downwards
                if(direction == "N" || direction == "S") velocity.multiplyY(0);

                //if E or W, make horizontal velocity 0
                else if(direction == "E" || direction == "W") velocity.multiplyX(0);

                //if NE, must check if we are colliding into a tile above or to the right of us
                else if(direction == "NE"){
                    Hitbox temp = toMove;

                    Tile corner = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    //check the two tiles that are to the right of us
                    toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
                    Tile right1 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile right2 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    //check the two tiles that are above us
                    toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
                    Tile up1 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile up2 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
 
                    //if there is a tile to the right of us, but not above us, only move up
                    if((right1.getCollision() || right2.getCollision()) && !(up1.getCollision() || up2.getCollision())){
                        position.setX(((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE) * GameFrame.ORIGINAL_TILE_SIZE + (size.getWidth()/4) -1);
                        velocity.multiplyX(0);
                        System.out.println("there is a tile to the right of us, but not above us, only move up");
                    }
                    //if there is a tile above us, but not to the right of up, only move right
                    else if((up1.getCollision() || up2.getCollision()) && !(right1.getCollision() || right2.getCollision())){
                        position.setY(((int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE) * GameFrame.ORIGINAL_TILE_SIZE -3);
                        velocity.multiplyY(0);
                    }
                    //if there is tile above and to the right of us, dont move at all
                    else if((up2.getCollision()) && (right1.getCollision())){
                        velocity.multiply(0);
                    }
                    else if(corner.getCollision()){
                        position.setX(((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE) * GameFrame.ORIGINAL_TILE_SIZE + (size.getWidth()/4) -1);
                        position.setY(((int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE) * GameFrame.ORIGINAL_TILE_SIZE -3);
                        velocity.multiplyX(0);
                        System.out.println("there is a tile to the corner, stop");
                    }
                    //otherwise,  move normally
                    toMove = temp;
                }

                //if NE, must check if we are colliding into a tile above or to the left of us
                else if(direction == "NW"){
                    Hitbox temp = toMove;

                    //check the two tiles that are to the left of us
                    toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
                    Tile left1 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile left2 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    //check the two tiles that are above us
                    toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
                    Tile up1 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile up2 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    
                    //if there is a tile to the left of us, but not above us, only move up
                    if((left1.getCollision() || left2.getCollision()) && !(up1.getCollision() || up2.getCollision())){
                        velocity.multiplyX(0);
                    }
                    //if there is a tile above us, but not to the left of up, only move left
                    else if((up1.getCollision() || up2.getCollision()) && !(left1.getCollision() || left2.getCollision())){
                        velocity.multiplyY(0);
                    }
                    //if there is tile above and to the left of us, dont move at all
                    else if((up1.getCollision() || up2.getCollision()) && (left1.getCollision() || left2.getCollision())){
                        velocity.multiply(0);
                    }
                    //otherwise,  move normally
                    toMove = temp;
                }

                //if NE, must check if we are colliding into a tile below or to the right of us
                else if(direction == "SE"){
                    Hitbox temp = toMove;

                    //check the two tiles that are to the right of us
                    toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
                    Tile right1 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile right2 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    //check the two tiles that are below us
                    toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
                    Tile down1 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile down2 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    
                    //if there is a tile to the right of us, but not below us, only move down
                    if((right1.getCollision() || right2.getCollision()) && !(down1.getCollision() || down2.getCollision())){
                        velocity.multiplyX(0);
                    }
                    //if there is a tile below us, but not to the right of up, only move right
                    else if((down1.getCollision() || down2.getCollision()) && !(right1.getCollision() || right2.getCollision())){
                        velocity.multiplyY(0);
                    }
                    //if there is tile below and to the right of us, dont move at all
                    else if((down1.getCollision() || down2.getCollision()) && (right1.getCollision() || right2.getCollision())){
                        velocity.multiply(0);
                    }
                    //otherwise,  move normally
                    toMove = temp;
                }

                //if NE, must check if we are colliding into a tile below or to the left of us
                else if(direction == "SW"){
                    Hitbox temp = toMove;

                    //check the two tiles that are to the left of us
                    toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
                    Tile left1 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile left2 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMinY()/GameFrame.ORIGINAL_TILE_SIZE];
                    //check the two tiles that are below us
                    toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
                    Tile down1 = roomArr[((int)toMove.getBounds().getMaxX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    Tile down2 = roomArr[((int)toMove.getBounds().getMinX()/GameFrame.ORIGINAL_TILE_SIZE)][(int)toMove.getBounds().getMaxY()/GameFrame.ORIGINAL_TILE_SIZE];
                    
                    //if there is a tile to the left of us, but not below us, only move up
                    if((left1.getCollision() || left2.getCollision()) && !(down1.getCollision() || down2.getCollision())){
                        velocity.multiplyX(0);
                    }
                    //if there is a tile below us, but not to the left of up, only move left
                    else if((down1.getCollision() || down2.getCollision()) && !(left1.getCollision() || left2.getCollision())){
                        velocity.multiplyY(0);
                    }
                    //if there is tile below and to the left of us, dont move at all
                    else if((down1.getCollision() || down2.getCollision()) && (left1.getCollision() || left2.getCollision())){
                        velocity.multiply(0);
                    }
                    //otherwise,  move normally
                    toMove = temp;
                }
                
        }

        //finally, after adjusting the velocity, apply it to our actual position
        position.apply(velocity);
        
        //deal with other entity collisions
    }

    @Override
    protected void setMovement(GameState gameState) {
        Input input = gameState.getInput();
        if(input.isPressed(KeyEvent.VK_A)) {
            left = true;
            moveOrder.newMove("left");
            moveOrder.cancel("left");
        }
        else {
            if(left == true){
                left = false;   
                moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }

        }
        if(input.isPressed(KeyEvent.VK_D)) {
            right = true;
            moveOrder.newMove("right");
            moveOrder.cancel("right");
        }
        else {
            if(right == true){
                right = false;
                moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }
        }
        if(input.isPressed(KeyEvent.VK_W)) {
            up = true;
            moveOrder.newMove("up");
            moveOrder.cancel("up");
        }
        else {
            if(up == true){
                up = false;
                moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }
            
        }
        if(input.isPressed(KeyEvent.VK_S)) {
            down = true;
            moveOrder.newMove("down");
            moveOrder.cancel("down");
        }
        else {
            if(down == true){
                down = false;
                moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }
            
        }
       
        if(!(input.isPressed(KeyEvent.VK_W) || input.isPressed(KeyEvent.VK_S) || input.isPressed(KeyEvent.VK_D) || input.isPressed(KeyEvent.VK_A))) moveOrder.newMove("null");
    }

    @Override
    public Hitbox getHitbox(){
        return new Hitbox(new Rectangle(position.intX() + size.getWidth()/4, position.intY() + size.getHeight() - (int)(size.getHeight()/2.4), size.getWidth()/2, (int)(size.getHeight()/2.4)));
    }

    @Override
    public void setSpriteSheet() {
        animationSheet = setup("/player/Dante");
    }





    
}
