package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import core.Size;
import core.Position;
import entities.Entity;
import entities.Hitbox;
import entities.Player;
import room.Room;
import room.RoomManager;
import room.TileManager;

public class GameState {

    //SYSTEM
    private Input input;
    private Camera camera;
    private Thread gameThread;
    public Random rng;
    
    //ENTITIES
    public Player player;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> drawList = new ArrayList<Entity>();

    //STATUS
    public int status;
    public static final int TITLE_STATUS = 0;
    public static final int PLAY_STATUS = 1;

    //ROOM
    private RoomManager roomManager;

    public GameState(long seed){
        //SYSTEM
        this.rng = new Random(seed);
        this.input = new Input();
        this.entities = new ArrayList<>();
        this.camera = new Camera();

        //PLAYER
        this.player = new Player();
        this.entities.add(player);

        //STATUS
        status = PLAY_STATUS;

        //ROOM & FLOOR
        roomManager = new RoomManager("limbo");
        roomManager.add(0);
    }

    public void update() {
        sortObjectsByPosition();
        entities.forEach(entity -> entity.update(this));
        camera.update(this);
    }

    private void sortObjectsByPosition() {
        entities.sort(Comparator.comparing(entity -> entity.getPosition().getY()));
    }


    //=================================================================================================================
    // GETTERS
        public int getStatus(){
            return status;
        }

        public Input getInput(){
            return input;
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public Camera getCamera() {
            return camera;
        }

        public Room getCurrentRoom(){
            return roomManager.getCurrentRoom();
        }

        public Position getRandomPosition() {
            return roomManager.getCurrentRoom().getRandomPosition();
        }

        public List<Entity> getCollidingGameObjects(Hitbox hitbox) {
            ArrayList<Entity> collided = new ArrayList<Entity>();
            for (Entity e: entities) {
                if(hitbox.collidesWith(e.getHitbox())) collided.add(e);
            }
            return collided;
        }

    //=================================================================================================================

}

