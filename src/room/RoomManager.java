package room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RoomManager {
    private final int[] ROOM_TYPES_THAT_CAN_HAVE_EAST_ROOM = {-1};
    private final int[] ROOM_TYPES_THAT_CAN_HAVE_WEST_ROOM = {-1};
    private final int[] ROOM_TYPES_THAT_CAN_HAVE_NORTH_ROOM = {};
    private final int[] ROOM_TYPES_THAT_CAN_HAVE_SOUTH_ROOM = {};

    private Room currentRoom;
    private String currentFloor;
    private ArrayList<Room> roomList;
    private Random rng;

    public RoomManager(String floor, Random rng){
        this.rng = rng; 
        roomList = new ArrayList<Room>();
        floorSwitch(floor);
    }  

    public void floorSwitch(String floor){
        currentFloor = floor;
        switch (currentFloor) {
            case "limbo":
                createLimbo();
                break;
        
            default:
                break;
        }
    }

    public void createLimbo(){
        Room startRoom = new Room(0, currentFloor);
        currentRoom = startRoom;
        roomList.add(startRoom);

        Room room1 = new Room(1, currentFloor);
        roomList.get(0).setEastRoom(room1);
        room1.setWestRoom(startRoom);
        roomList.add(room1);
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void setCurrentRoom(Room newRoom){
        currentRoom = newRoom;
    }

}
