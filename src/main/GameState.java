package main;

import java.awt.event.KeyEvent;
import java.io.IOException;
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
    private UI ui;
    
    //ENTITIES
    private Player player;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> drawList = new ArrayList<Entity>();

    //STATUS
    public int status;
    public static final int GAMEOVER_STATUS = -1;
    public static final int TITLE_STATUS = 0;
    public static final int WALIKNG_STATUS = 1;
    public static final int BATTLE_STATUS = 2;
    public static final int DIALOGUE_STATUS = 2;
    public static final int PAUSE_STATUS = 3;

    //ROOM
    private RoomManager roomManager;

    public GameState(long seed){
        //SYSTEM
        this.rng = new Random(seed);
        this.input = new Input();
        this.entities = new ArrayList<>();
        this.camera = new Camera();
        this.BGM = new Sound();
        this.ui = new UI(this);

        //PLAYER
        this.player = new Player();
        this.entities.add(player);
        camera.focusOn(player);

        //STATUS
        status = TITLE_STATUS;
        playBGM();

        //ROOM & FLOOR
        roomManager = new RoomManager("limbo", rng);
        player.getPosition().set(roomManager.getCurrentRoom().getMainSpawn());
    }

    public void update() {
        if(status == WALIKNG_STATUS){
            sortObjectsByPosition();
            entities.forEach(entity -> entity.update(this));
            camera.update(this);
        }
        else if(status == TITLE_STATUS){
            checkTitleControls();
        }
        checkFullscreen();
    }

    private void sortObjectsByPosition() {
        entities.sort(Comparator.comparing(entity -> entity.getPosition().getY()));
    }

    public void checkFullscreen(){
        //PLAY STATUS
        if(input.isPressed(KeyEvent.VK_F)){
            if(fullscreen == true) fullscreen = false;
            else fullscreen = true;
            settingsChanged = true;
            input.unPress(KeyEvent.VK_F);
        }
    }

    public void checkTitleControls(){
        //TITLE STATUS
        boolean upPressed = false, downPressed = false;
        if(input.isPressed(KeyEvent.VK_UP) || input.isPressed(KeyEvent.VK_W)) upPressed = true;
        if(input.isPressed(KeyEvent.VK_DOWN) || input.isPressed(KeyEvent.VK_S)) downPressed = true;

        if(upPressed){
            ui.commandNum--;
            upPressed = false;
            if(ui.commandNum < 0) ui.commandNum = 3;
        }
        else if(downPressed){
            ui.commandNum++;
            downPressed = false;
            if(ui.commandNum > 3) ui.commandNum = 0;
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

        public Player getPlayer() {
            return player;
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

        public UI getUI(){
            return ui;
        }

    //=================================================================================================================


    public void setCurrentRoom(Room newRoom){
        roomManager.setCurrentRoom(newRoom);
    }

}

