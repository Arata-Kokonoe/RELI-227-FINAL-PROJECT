package room;

import java.util.ArrayList;

public class RoomManager {

    private Room currentRoom;
    private String currentFloor;
    private ArrayList<Room> roomList;

    public RoomManager(String floor){
        currentFloor = floor;
        currentRoom = new Room(-2, currentFloor);
        roomList = new ArrayList<Room>();
    }

    public void add(int v){
        roomList.add(new Room(v, currentFloor));
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

}
