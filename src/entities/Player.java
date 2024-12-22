package entities;

import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import core.MoveOrder;
import core.Position;
import core.Size;
import core.TileCoords;
import core.Vector2D;
import entities.enemies.StandingEnemy;
import main.GameFrame;
import main.GameState;
import main.Input;
import room.Room;

public class Player extends MovingEntity {

    public final int MAX_HEALTH = 20;

    public int currentHealth;
    public int purity;

    public Player(){
        currentHealth = 20;
        position = new Position(0, 0);
        size = new Size(GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE*2);
        speed = 2;
        purity = 0;
        animationSheet = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        setSpriteSheet();
        animationManager = new AnimationManager(this);
        moveOrder = new MoveOrder();
    }

    @Override
    public void update(GameState gameState){
        super.update(gameState);
        checkInteraction(gameState);
    }

    public void checkInteraction(GameState gameState){
        Hitbox toCheck = getHitbox();
        if(gameState.interactPressed){
            if(getMoveOrder().getCurrent() != null){
                switch (getMoveOrder().getCurrent()) {
                    case "left":
                        toCheck = getHitbox().apply(new Vector2D(-GameFrame.ORIGINAL_TILE_SIZE, 0));
                        break;
                    case "right":
                        toCheck = getHitbox().apply(new Vector2D(GameFrame.ORIGINAL_TILE_SIZE, 0));
                        break;
                    case "up":
                        toCheck = getHitbox().apply(new Vector2D(0, -GameFrame.ORIGINAL_TILE_SIZE));
                        break;
                    case "down":
                        toCheck = getHitbox().apply(new Vector2D(0, GameFrame.ORIGINAL_TILE_SIZE));
                        break;
                    case "null":
                        switch (getMoveOrder().getPrev()) {
                            case "left":
                                toCheck = getHitbox().apply(new Vector2D(-GameFrame.ORIGINAL_TILE_SIZE, 0));
                                break;
                            case "right":
                                toCheck = getHitbox().apply(new Vector2D(GameFrame.ORIGINAL_TILE_SIZE, 0));
                                break;
                            case "up":
                                toCheck = getHitbox().apply(new Vector2D(0, -GameFrame.ORIGINAL_TILE_SIZE));
                                break;
                            case "down":
                                toCheck = getHitbox().apply(new Vector2D(0, GameFrame.ORIGINAL_TILE_SIZE));
                                break;       
                        }  
                    default:
                        break;
                }
    
                List<Entity> entitiesToCheck = gameState.getCollidingGameObjects(toCheck);
                for (Entity entity : entitiesToCheck) {
                    if(entity instanceof StandingEnemy && ((StandingEnemy)entity).interacted == false) ((StandingEnemy)entity).interaction();
                }
            }
        }
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
       
        //if(!(gameState.upPressed || gameState.downPressed || gameState.leftPressed || gameState.rightPressed)) moveOrder.newMove("null");
    }

    @Override
    public Hitbox getHitbox(){
        return new Hitbox(new Rectangle(position.intX(), position.intY(), size.getWidth()/2, (int)(size.getHeight()/2.2)), this);
    }

    @Override
    public void setSpriteSheet() {
        animationSheet = setup("/player/Dante");
    }





    
}
