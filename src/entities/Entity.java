package entities;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    public int x, y, speed, direction, width, height;
    public Hitbox hb;

    public BufferedImage sprite;

    private final int LEFT = 0;
    private final int RIGHT = 1;
    private final int UP = 2;
    private final int DOWN = 3;

    public Entity(){

        direction = -1;
        x = 100;
        y = 100;
        speed = 2;

        setSprites();
        width = sprite.getWidth();
        height = sprite.getHeight();

    }

    public void update(GamePanel gp){
        if(direction == LEFT){
            x -= speed;
        }
        if(direction == RIGHT){
            x += speed;
        }
        if(direction == DOWN){
            y -= speed;
        }
        if(direction == UP){
            y += speed;
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(sprite, x, y, null);
    }

    public void setSprites(){
        sprite = UtilityTool.loadSprite("defaultSprite-1");
    }

}
