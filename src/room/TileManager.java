package room;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GameFrame;

public class TileManager {

    private Tile[] tile;
    private BufferedImage tileset;

    public TileManager(String floor){
        
        tile = new Tile[32]; //array holds different types of tiles (aka. tile[0] = grass tile)

        getTileSet(floor);
    }

    //=============================================================================================================
        protected BufferedImage setup(String imagePath){

            BufferedImage image = null;

            try {
                image = ImageIO.read(getClass().getResourceAsStream("/res" + imagePath + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return image;
        } // setup(String imagePath)
    //  Reads an image from specified image path in res folder and returns it.
    //=============================================================================================================

    public void getTileSet(String floor){

        try{
            tileset = setup("/tiles/" + floor + "_tileset");

            //tiles 1-9 should not be used, only use double digits
            for(int i = 0; i < 11; i++){
                tile[i] = new Tile();
                tile[i].setSprite(setup("/tiles/blank16_tile"));
                tile[i].setCollision(true);
            }
            //tile[10] is a blank tile

            tile[11] = new Tile();
            tile[11].setSprite(setup("/tiles/limboWall_tileset").getSubimage(0, 0, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));
            //tile[11] is the normal floor tile
            int count = 12;
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 4; j++){
                    tile[count] = new Tile();
                    tile[count].setSprite(tileset.getSubimage(i * GameFrame.ORIGINAL_TILE_SIZE, j * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));
                    count++;
                }
            }
            tile[27] = new Tile();
            tile[27].setSprite(setup("/tiles/limboWall_tileset").getSubimage(GameFrame.ORIGINAL_TILE_SIZE, 0, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));
            //tile[27] is the normal wall tile
            
            tile[28] = new Tile();
            tile[28].setSprite(setup("/tiles/limboWall_tileset").getSubimage(0, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));
            //tile[28] is the vined wall tile

            tile[29] = new Tile();
            tile[29].setSprite(setup("/tiles/limboWall_tileset").getSubimage(GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));

            tile[30] = new Tile();
            tile[30].setSprite(setup("/tiles/limboWall_tileset").getSubimage(0, GameFrame.ORIGINAL_TILE_SIZE*2, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));

            tile[31] = new Tile();
            tile[31].setSprite(setup("/tiles/limboWall_tileset").getSubimage(GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE*2, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));

            for(int i = 12; i < tile.length; i++){
                tile[i].setCollision(true);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public Tile getTile(int num){
        return tile[num];
    }
}
