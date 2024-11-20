package tile;

import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class Tile {

    public BufferedImage image;
    public boolean collision;

    public Tile(){
        
        image = UtilityTool.scaleImage(UtilityTool.loadSprite("/Dante-1"), GamePanel.TILE_SIZE, GamePanel.TILE_SIZE*2);
        collision = false;

    }

}
