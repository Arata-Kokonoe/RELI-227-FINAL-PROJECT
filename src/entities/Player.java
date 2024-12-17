package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.GlyphMetrics;
import java.awt.image.BufferedImage;
import java.util.List;

import core.Position;
import core.Size;
import main.Camera;
import main.GameFrame;
import main.GameLoop;
import main.GameState;
import main.Input;
import main.UtilityTool;

public class Player extends MovingEntity {

    public Player(){
        position = new Position(50, 50);
        size = new Size(GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE*2);
        animationSheet = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        setSpriteSheet();
        animationManager = new AnimationManager(this);
    }

    @Override
    protected void handleCollisions(Hitbox toMove, List<Entity> collided) {
        //deal with tile collisions
        //deal with other entity collisions
    }

    @Override
    protected void setMovement(GameState gameState) {
        Input input = gameState.getInput();
        if(input.isPressed(KeyEvent.VK_A)) {
            left = true;
        }
        else left = false;
        if(input.isPressed(KeyEvent.VK_D)) {
            right = true;
        }
        else right = false;
        if(input.isPressed(KeyEvent.VK_W)) {
            up = true;
        }
        else up = false;
        if(input.isPressed(KeyEvent.VK_S)) {
            down = true;
        }
        else down = false;
    }

    @Override
    public Hitbox getHitbox(){
        return new Hitbox(new Rectangle((int)position.getX(), (int)position.getY(), size.getWidth()/2, size.getHeight()/2));
    }

    @Override
    public void setSpriteSheet() {
        animationSheet = setup("/player/Dante");
    }





    
}
