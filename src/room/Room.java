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
        size = new Size(GameFrame.MAX_COL * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.MAX_ROW * GameFrame.ORIGINAL_TILE_SIZE);
        setupRoom();
    }

    private void setupRoom(){

        if(value == -1){
            loadTileArr("/res/rooms/room_-1");
        }
        if(value == -2){
            tileArr = new Tile[GameFrame.MAX_COL*2][GameFrame.MAX_ROW*2];
            size = new Size(GameFrame.MAX_COL * 2 * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.MAX_ROW * 2 * GameFrame.ORIGINAL_TILE_SIZE);
            loadTileArr("/res/rooms/room_-2");
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

            while(col < tileArr.length && row < tileArr[col].length){
                
                String line = br.readLine();
                
                while (col < tileArr.length) {
                    
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    tileArr[col][row] = tileManager.getTile(num);
                    col++;
                }

                if (col == tileArr.length) {
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
        //System.out.println("start of visible tileArr = " + Math.max(0, camera.getPosition().getX() / GameFrame.ORIGINAL_TILE_SIZE - BUFFER) + ", " + Math.max(0, camera.getPosition().getY() / GameFrame.ORIGINAL_TILE_SIZE - BUFFER));
        return new Position(
                Math.max(0, camera.getPosition().getX() / GameFrame.ORIGINAL_TILE_SIZE - BUFFER),
                Math.max(0, camera.getPosition().getY() / GameFrame.ORIGINAL_TILE_SIZE - BUFFER)
        );
    }

    public Position getViewableEndingGridPosition(Camera camera) {
        //System.out.println("end of visible tileArr = " + Math.min(tileArr.length, camera.getPosition().getX() / GameFrame.ORIGINAL_TILE_SIZE + camera.getSize().getWidth() / GameFrame.ORIGINAL_TILE_SIZE + BUFFER) + ", " + Math.min(tileArr[0].length, camera.getPosition().getY() / GameFrame.ORIGINAL_TILE_SIZE + camera.getSize().getHeight() / GameFrame.ORIGINAL_TILE_SIZE + BUFFER));
        return new Position(
                Math.min(tileArr.length, camera.getPosition().getX() / GameFrame.ORIGINAL_TILE_SIZE + camera.getSize().getWidth() / GameFrame.ORIGINAL_TILE_SIZE + BUFFER),
                Math.min(tileArr[0].length, camera.getPosition().getY() / GameFrame.ORIGINAL_TILE_SIZE + camera.getSize().getHeight() / GameFrame.ORIGINAL_TILE_SIZE + BUFFER)
        );
    }


    public Tile[][] getTileArr() {
        return tileArr;
    }
    
    
}
