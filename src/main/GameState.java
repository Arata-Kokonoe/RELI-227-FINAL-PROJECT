package main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import core.Position;
import entities.Entity;
import entities.Hitbox;
import entities.Player;
import room.Room;
import room.RoomManager;

public class GameState {

    //SYSTEM
    private Input input;
    private Camera camera;
    public Random rng;
    public boolean fullscreen, settingsChanged;
    private Sound BGM;
    
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
        this.BGM = new Sound();

        //PLAYER
        this.player = new Player();
        this.entities.add(player);
        camera.focusOn(player);

        //STATUS
        status = PLAY_STATUS;
        playBGM();

        //ROOM & FLOOR
        roomManager = new RoomManager("limbo2");
        player.getPosition().set(roomManager.getCurrentRoom().getSpawn());
    }

    public void update() {
        if(status == PLAY_STATUS){
            sortObjectsByPosition();
            entities.forEach(entity -> entity.update(this));
            camera.update(this);
            checkControls();
        }
        else{}
    }

    private void sortObjectsByPosition() {
        entities.sort(Comparator.comparing(entity -> entity.getPosition().getY()));
    }

    public void checkControls(){
        //PLAY STATUS
        if(input.isPressed(KeyEvent.VK_F)){
            if(fullscreen == true) fullscreen = false;
            else fullscreen = true;
            settingsChanged = true;
            input.unPress(KeyEvent.VK_F);
        }
    }

    public void playBGM(){
        BGM.setFile(0);
        BGM.play();
        BGM.loop();
    }

    public void stopMusic(){
        BGM.stop();
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

