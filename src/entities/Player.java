package entities;

import java.awt.font.GlyphMetrics;

import main.GamePanel;
import main.UtilityTool;

public class Player extends Entity {

    public Player(){
        super();
    }

    public void update(GamePanel gp){
        if(gp.keyH.leftPressed == true){
            x -= speed;
        }
        if(gp.keyH.rightPressed == true){
            x += speed;
        }
        if(gp.keyH.upPressed == true){
            y -= speed;
        }
        if(gp.keyH.downPressed == true){
            y += speed;
        }
    }

    public void setSprites(){
        sprite = UtilityTool.scaleImage(loadSprite("/Dante-1"), GamePanel.TILE_SIZE, GamePanel.TILE_SIZE*2);
    }
}
