package tile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class RoomManager {

    GamePanel gp;
    public Tile[] tile;
    public int tileMap[][];

    public RoomManager(GamePanel gp){

        this.gp = gp;
        
        tile = new Tile[10]; //array holds different types of tiles (aka. tile[0] = grass tile)
        tileMap = new int[GamePanel.MAX_COL][GamePanel.MAX_ROW];

        getTileImage();
        loadMap("/res/Dante-1"); 
    }

    public void getTileImage(){

        setup(0, "limbo_center", true);

    }

    public void setup(int index, String imageName, boolean collision){

        try {
            tile[index] = new Tile();
            tile[index].image = UtilityTool.loadSprite("tiles/" + imageName);
            tile[index].image = UtilityTool.scaleImage(tile[index].image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try {
            InputStream is = main.Main.class.getResourceAsStream(filePath);
            BufferedImage test = UtilityTool.loadSprite("Dante-1");

            System.out.println(is == null);
            System.out.println(test == null);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 6;

            for (int i = 0; i < row; i++){
                br.readLine();
            }

            while(col < GamePanel.MAX_COL && row < GamePanel.MAX_ROW){
                
                String line = br.readLine();
                
                while (col < GamePanel.MAX_COL) {
                    
                    String numbers[] = line.split(",");

                    int num = Integer.parseInt(numbers[col]);

                    tileMap[col][row] = num;
                    col++;
                }

                if (col == GamePanel.MAX_COL) {
                    col = 0;
                    row++;
                }

            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        
        int roomCol = 0;
        int roomRow = 0;

        while(roomCol < GamePanel.MAX_COL && roomRow < GamePanel.MAX_ROW){
            
            int tileNum = tileMap[roomCol][roomRow];

            int screenX = roomCol * GamePanel.TILE_SIZE;    //calculate worldX by multiplying tileSize * worldCol
            int screenY = roomRow * GamePanel.TILE_SIZE;    //same with worldY

            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
           
            roomCol++;

            if(roomCol == gp.MAX_COL){
                roomCol = 0;
                roomRow++;
            }
        }
    }
}
