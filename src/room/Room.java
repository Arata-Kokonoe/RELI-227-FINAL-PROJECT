package room;

import core.Size;
import main.Camera;
import main.GameFrame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.Position;

public class Room {
    private final int BUFFER = 2;

    private Room north, south, east, west;
    private Size size;
    private int value;
    private Tile[][] tileArr;
    private TileManager tileManager;

    public Room(int v, String floor){
        value = v;
        tileManager = new TileManager(floor);
        tileArr = new Tile[GameFrame.MAX_COL][GameFrame.MAX_ROW];
        setupRoom();
    }

    private void setupRoom(){

        if(value == -1){
            loadTileArr("/res/rooms/room_-1");
        }

    }

    public void loadTileArr(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;

            for (int i = 0; i < row; i++){
                br.readLine();
            }

            while(col < GameFrame.MAX_COL && row < GameFrame.MAX_ROW){
                
                String line = br.readLine();
                
                while (col < GameFrame.MAX_COL) {
                    
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    tileArr[col][row] = tileManager.getTile(num);
                    col++;
                }

                if (col == GameFrame.MAX_COL) {
                    col = 0;
                    row++;
                }

            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWidth(){
        return size.getWidth();
    }

    public int getHeight(){
        return size.getHeight();
    }


    public Position getRandomPosition() {
        double x = Math.random() * tileArr.length * GameFrame.ORIGINAL_TILE_SIZE;
        double y = Math.random() * tileArr[0].length * GameFrame.ORIGINAL_TILE_SIZE;

        return new Position(x, y); 
    }

    public Position getViewableStartingGridPosition(Camera camera) {
        return new Position(
                Math.max(0, camera.getPosition().getX() / GameFrame.ORIGINAL_TILE_SIZE - BUFFER),
                Math.max(0, camera.getPosition().getY() / GameFrame.ORIGINAL_TILE_SIZE - BUFFER)
        );
    }

    public Position getViewableEndingGridPosition(Camera camera) {
        return new Position(
                Math.min(tileArr.length, camera.getPosition().getX() / GameFrame.ORIGINAL_TILE_SIZE + camera.getSize().getWidth() / GameFrame.ORIGINAL_TILE_SIZE + BUFFER),
                Math.min(tileArr[0].length, camera.getPosition().getY() / GameFrame.ORIGINAL_TILE_SIZE + camera.getSize().getHeight() / GameFrame.ORIGINAL_TILE_SIZE + BUFFER)
        );
    }


    public Tile[][] getTileArr() {
        return tileArr;
    }
    
    
}
