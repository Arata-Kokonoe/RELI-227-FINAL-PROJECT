package room;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class TileManager {

    private Tile[] tile;
    private BufferedImage tileset;

    public TileManager(String floor){
        
        tile = new Tile[17]; //array holds different types of tiles (aka. tile[0] = grass tile)

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
            tile[0] = new Tile();
            tile[0].image = setup("/tiles/blank_tile");
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
            for(int i = 1; i < 17; i++){
                tile[i].collision = true;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public Tile getTile(int num){
        return tile[num];
    }
}
