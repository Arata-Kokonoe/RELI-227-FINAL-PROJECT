package room;

import core.Size;
import main.Camera;
import core.Position;

public class Room {
    private Room left, right;
    private Size size;
    public int value;
    private TileManager tileManager;

    public Room(int v){
        left = null;
        right = null;
        value = v;
        setupRoom();
    }

    private void setupRoom(){

        if(value == -1){
            
        }

    }

    public int getWidth(){
        return size.getWidth();
    }

    public int getHeight(){
        return size.getHeight();
    }


    public Position getRandomPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRandomPosition'");
    }

    public Position getViewableEndingGridPosition(Camera camera) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getViewableEndingGridPosition'");
    }

    public Tile[][] getTileArr() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTileArr'");
    }
    
}
