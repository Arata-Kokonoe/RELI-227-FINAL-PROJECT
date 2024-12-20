package room;

import core.Size;
import core.TileCoords;
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
    private String[][] enemiesArr;
    private TileManager tileManager;
    private Position northSpawn, southSpawn, westSpawn, eastSpawn, mainSpawn;

    public Room(int v, String floor){
        value = v;
        tileManager = new TileManager(floor);
        tileArr = new Tile[GameFrame.MAX_COL][GameFrame.MAX_ROW];
        enemiesArr = new String[GameFrame.MAX_COL][GameFrame.MAX_ROW];
        size = new Size(GameFrame.MAX_COL * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.MAX_ROW * GameFrame.ORIGINAL_TILE_SIZE);
        setupRoom(floor);
    }

    private void setupRoom(String floor){

        if(value == -1){
            tileArr = new Tile[GameFrame.MAX_COL*2][GameFrame.MAX_ROW*2];
            size = new Size(GameFrame.MAX_COL * 2 * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.MAX_ROW * 2 * GameFrame.ORIGINAL_TILE_SIZE);
            loadTileArr("/res/floors/" + floor + "/rooms/room_" + value + ".txt");
        }
        else if(value == 0){
            tileArr = new Tile[GameFrame.MAX_COL][GameFrame.MAX_ROW];
            size = new Size(GameFrame.MAX_COL * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.MAX_ROW * GameFrame.ORIGINAL_TILE_SIZE);
            loadTileArr("/res/floors/" + floor + "/rooms/room_" + value + ".txt");

            mainSpawn = new Position(GameFrame.ORIGINAL_TILE_SIZE, 14 * GameFrame.ORIGINAL_TILE_SIZE);
            eastSpawn = new Position(getWidth() - GameFrame.ORIGINAL_TILE_SIZE, 14 * GameFrame.ORIGINAL_TILE_SIZE);
        }
        else if(value == 1){
            tileArr = new Tile[GameFrame.MAX_COL][GameFrame.MAX_ROW];
            enemiesArr = new String[GameFrame.MAX_COL][GameFrame.MAX_ROW];
            size = new Size(GameFrame.MAX_COL * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.MAX_ROW * GameFrame.ORIGINAL_TILE_SIZE);
            loadTileArr("/res/floors/" + floor + "/rooms/room_" + value + ".txt");

            enemiesArr[34][14] = "Vergil";
            westSpawn = new Position(GameFrame.ORIGINAL_TILE_SIZE, 14 * GameFrame.ORIGINAL_TILE_SIZE);
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

    public int getValue(){
        return value;
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

    public String[][] getEnemiesArr(){
        return enemiesArr;
    }
    
    public TileCoords positionToCoords(Position pos){
        return new TileCoords(pos.intX() / GameFrame.ORIGINAL_TILE_SIZE, pos.intY() / GameFrame.ORIGINAL_TILE_SIZE);
    }

    public Room getEastRoom(){
        return east;
    }

    public void setEastRoom(Room newRoom){
        east = newRoom;
    }

    public Room getNorthRoom(){
        return north;
    }

    public void setNorthRoom(Room newRoom){
        north = newRoom;
    }

    public Room getWestRoom(){
        return west;
    }

    public void setWestRoom(Room newRoom){
        west = newRoom;
    }

    public Room getSouthRoom(){
        return south;
    }

    public void setSouthRoom(Room newRoom){
        south = newRoom;
    }

    public Position getEastSpawn(){
        return eastSpawn;
    }
    public Position getWestSpawn(){
        return westSpawn;
    }
    public Position getNorthSpawn(){
        return northSpawn;
    }
    public Position getSouthSpawn(){
        return southSpawn;
    }
    public Position getMainSpawn(){
        return mainSpawn;
    }

}
