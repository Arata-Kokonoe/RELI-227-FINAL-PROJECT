package room;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GameLoop;
import main.GameState;
import main.UtilityTool;

public class TileManager {

    GameState gameState;
    public Tile[] tile;
    public int tileMap[][];
    public BufferedImage tileset;

    public TileManager(GameState state){

        this.gameState = state;
        
        tile = new Tile[10]; //array holds different types of tiles (aka. tile[0] = grass tile)
        tileMap = new int[GameLoop.MAX_COL][GameLoop.MAX_ROW];

        getTileImage();
        loadMap("/res/rooms/room01"); 
    }

    public void getTileImage(){

        try{
            BufferedImage tileset = UtilityTool.loadSprite("tiles/limbo_tileset");
            tile[0] = new Tile();
            tile[0].image = UtilityTool.loadSprite("tiles/blank_tile");
            tile[1] = new Tile();
            tile[1].image = tileset.getSubimage(0, 0, 48, 48);
            tile[2] = new Tile();
            tile[2].image = tileset.getSubimage(48, 0, 48, 48);
            tile[3] = new Tile();
            tile[3].image = tileset.getSubimage(96, 0, 48, 48);
            tile[4] = new Tile();
            tile[4].image = tileset.getSubimage(144, 0, 48, 48);
            tile[5] = new Tile();
            tile[5].image = tileset.getSubimage(192, 0, 48, 48);
            tile[6] = new Tile();
            tile[6].image = tileset.getSubimage(0, 48, 48, 48);
            tile[7] = new Tile();
            tile[7].image = tileset.getSubimage(48, 48, 48, 48);
            tile[8] = new Tile();
            tile[8].image = tileset.getSubimage(96, 48, 48, 48);
            tile[9] = new Tile();
            tile[9].image = tileset.getSubimage(144, 48, 48, 48);
            tile[10] = new Tile();
            tile[10].image = tileset.getSubimage(192, 48, 48, 48);
            tile[11] = new Tile();
            tile[11].image = tileset.getSubimage(0, 96, 48, 48);
            tile[12] = new Tile();
            tile[12].image = tileset.getSubimage(48, 96, 48, 48);
            tile[13] = new Tile();
            tile[13].image = tileset.getSubimage(96, 96, 48, 48);
            tile[14] = new Tile();
            tile[14].image = tileset.getSubimage(144, 96, 48, 48);
            tile[15] = new Tile();
            tile[15].image = tileset.getSubimage(192, 96, 48, 48);
            tile[16] = new Tile();
            tile[16].image = tileset.getSubimage(0, 144, 48, 48);

        } catch(Exception e){

        }


    }

    /*
    public void setup(int index, String imageName, boolean collision){

        try {
            tile[index] = new Tile();
            tile[index].image = UtilityTool.loadSprite("tiles/" + imageName);
            tile[index].image = UtilityTool.scaleImage(tile[index].image, GameLoop.TILE_SIZE, GameLoop.TILE_SIZE);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;

            for (int i = 0; i < row; i++){
                br.readLine();
            }

            while(col < GameLoop.MAX_COL && row < GameLoop.MAX_ROW){
                
                String line = br.readLine();
                
                while (col < GameLoop.MAX_COL) {
                    
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    tileMap[col][row] = num;
                    col++;
                }

                if (col == GameLoop.MAX_COL) {
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

        while(roomCol < GameLoop.MAX_COL && roomRow < GameLoop.MAX_ROW){
            
            int tileNum = tileMap[roomCol][roomRow];

            int screenX = roomCol * GameLoop.TILE_SIZE;    //calculate worldX by multiplying tileSize * worldCol
            int screenY = roomRow * GameLoop.TILE_SIZE;    //same with worldY

            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
           
            roomCol++;

            if(roomCol == GameLoop.MAX_COL){
                roomCol = 0;
                roomRow++;
            }
        }
    }
}
