package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import core.MoveOrder;
import core.Position;
import core.Size;
import core.TileCoords;
import core.Vector2D;
import main.Camera;
import main.GameFrame;
import main.GameState;
import room.Room;

public class PlayerSoul extends MovingEntity{

    public PlayerSoul(int purity){
        position = new Position(GameFrame.GAME_WIDTH/2, GameFrame.GAME_HEIGHT/2 + GameFrame.ORIGINAL_TILE_SIZE);
        size = new Size(GameFrame.ORIGINAL_TILE_SIZE/2, GameFrame.ORIGINAL_TILE_SIZE/2);
        speed = 2;
        sprite = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        moveOrder = new MoveOrder();
        setSpriteSheet(purity);
    }

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

        handleCollisions(gameState);
        
        if(gameState.attackPressed) {
            gameState.currentEnemy.damage();
            gameState.attackPressed = false;
        }
        if(gameState.prayPressed) {
            gameState.currentEnemy.save();
            gameState.prayPressed = false;
        }
    }
    
    @Override
    protected void handleCollisions(GameState gameState){

        if(velocity.getY() > 0){
            Hitbox toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
            System.out.println("toMove.botY() = " + toMove.botY());
            System.out.println("battleWindow.botY() = " + gameState.getUI().battleWindow.botY());
            if(toMove.botY() >= gameState.getUI().battleWindow.botY()){
                //went out of bounds of room
                velocity.multiplyY(0);
                position.setY(gameState.getUI().battleWindow.botY() - getHitbox().getBounds().getHeight() - 1);
            }
        }
        else if(velocity.getY() < 0){
            Hitbox toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
            System.out.println("toMove.topY() = " + toMove.topY());
            System.out.println("battleWindow.topY() = " + gameState.getUI().battleWindow.topY());
            if(toMove.topY() <= gameState.getUI().battleWindow.topY()){
                //went out of bounds of room
                velocity.multiplyY(0);
                position.setY(gameState.getUI().battleWindow.topY() + 1);
            }
        }

        if(velocity.getX() < 0){
            Hitbox toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
            if(toMove.leftX() <= gameState.getUI().battleWindow.leftX()){
                //went out of bounds of room
                velocity.multiplyX(0);
                position.setX(gameState.getUI().battleWindow.leftX() + 1);
            }
        }
        else if(velocity.getX() > 0){
            Hitbox toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
            if(toMove.rightX() >= gameState.getUI().battleWindow.rightX()){
                //went out of bounds of room
                velocity.multiplyX(0);
                position.setX(gameState.getUI().battleWindow.rightX() - getHitbox().getBounds().getWidth() - 1);
            }
        }

        position.apply(velocity);
    }

    @Override
    public void draw(Graphics2D g2, Camera camera) {
            g2.drawImage(
                sprite,
                this.getPosition().intX() - (int)(this.getPosition().getX() + this.getSize().getWidth() - this.getHitbox().rightX())/2,
                this.getPosition().intY() - (int)(this.getPosition().getY() + this.getSize().getHeight() - this.getHitbox().botY()),
                null
                ); 
            //drawToMove(g2);
            //drawHitbox(g2);
        }


    @Override
    public void setSpriteSheet(){
    }

    public void setSpriteSheet(int purity){
        if(purity == 0) sprite = setup("/player/NeutralSoul");
        else if (purity < 0) sprite = setup("/player/ImpureSoul");
        else sprite = setup("/player/PureSoul"); 
    }

    @Override
    protected void setMovement(GameState gameState) {
        if(gameState.leftPressed) {
            left = true;
            moveOrder.newMove("left");
            moveOrder.cancel("left");
        }
        else {
            if(left == true){
                left = false;   
                //moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }

        }
        if(gameState.rightPressed) {
            right = true;
            moveOrder.newMove("right");
            moveOrder.cancel("right");
        }
        else {
            if(right == true){
                right = false;
                //moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }
        }
        if(gameState.upPressed){
            up = true;
            moveOrder.newMove("up");
            moveOrder.cancel("up");
        }
        else {
            if(up == true){
                up = false;
                //moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }
        }
        if(gameState.downPressed){
            down = true;
            moveOrder.newMove("down");
            moveOrder.cancel("down");
        }
        else {
            if(down == true){
                down = false;
                //moveOrder.setCurrent("null");
                moveOrder.uncancelAll();
            }
        }
    }

    @Override
    public Hitbox getHitbox(){
        return new Hitbox(new Rectangle(position.intX(), position.intY(), size.getWidth(), size.getHeight()), this);
    }

}
