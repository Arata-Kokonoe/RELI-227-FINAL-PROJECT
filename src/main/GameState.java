package main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import battles.Battle;
import core.Position;
import core.TileCoords;
import entities.Entity;
import entities.Hitbox;
import entities.Player;
import entities.PlayerSoul;
import entities.enemies.Enemy;
import room.Room;
import room.RoomManager;

public class GameState {

    //SYSTEM
    private Camera camera;
    public Random rng;
    private UI ui;

    //MUSIC & SOUND
    private Sound bgm;
    public static final int TITLE_MUSIC = 0;
    public static final int LIMBO_MUSIC = 1;
    public static final int BATTLE_MUSIC = 2;

    //CONTROLS
    private Input input;
    private boolean interactPressedPrev, prayPressedPrev, attackPressedPrev;
    private boolean upPressedPrev;
    private boolean downPressedPrev;
    private boolean menuPressedPrev;
    public boolean closeWindow;
    public boolean upPressed, leftPressed, rightPressed, downPressed, interactPressed, backPressed, prayPressed, attackPressed;
    public boolean drawAttack, drawPray;

    //COOLDOWNS
    public int actionTimer;

    //SETTINGS
    public boolean fullscreen, settingsChanged, resolutionChanged;
    public float volume;
    
    //ENTITIES
    private Player player;
    private PlayerSoul playerSoul;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> drawList = new ArrayList<Entity>();
    public ArrayList<Entity> toUpdate = new ArrayList<Entity>();
    public ArrayList<Entity> battleEntities = new ArrayList<Entity>();
    public Enemy currentEnemy;
    public Battle currentBattle;

    //STATUS
    public int status;
    public static final int GAMEOVER_STATUS = -1;
    public static final int TITLE_STATUS = 0;
    public static final int WALKING_STATUS = 1;
    public static final int BATTLE_STATUS = 2;
    public static final int DIALOGUE_STATUS = 3;
    public static final int MENU_STATUS = 4;
    public static final int SETTINGS_STATUS = 5;

    //ROOM
    private RoomManager roomManager;

    public GameState(long seed){
        //SYSTEM
        this.rng = new Random(seed);
        this.input = new Input();
        this.entities = new ArrayList<>();
        this.toUpdate = new ArrayList<>();
        this.camera = new Camera();
        this.bgm = new Sound();
        this.ui = new UI(this);

        //PLAYER
        this.player = new Player();
        this.entities.add(player);
        camera.focusOn(player);

        //STATUS
        status = TITLE_STATUS;
        playMusic(TITLE_MUSIC);
        
        //SETTINGS
        defaultSettings();

        //ROOM & FLOOR
        roomManager = new RoomManager("limbo", rng);
        player.getPosition().set(roomManager.getCurrentRoom().getMainSpawn());
    }

    public void update() {
        if(status == WALKING_STATUS){
            checkWalkingControls();
            for(int i = 0; i < entities.size(); i++){
                if(entities.get(i) != null) toUpdate.add(entities.get(i));
            }
            sortObjectsByPosition();
            toUpdate.forEach(entity -> entity.update(this));
            camera.update(this);
            toUpdate.clear();
        }
        else if(status == TITLE_STATUS){
            checkTitleControls();
        }
        else if (status == SETTINGS_STATUS){
            checkSettingsControls();
            if(settingsChanged) changeSettings();
        }
        else if(status == MENU_STATUS){
            checkMenuControls();
        }
        else if (status == DIALOGUE_STATUS){
            checkDialogueControls();
        }
        else if (status == BATTLE_STATUS){
            checkBattleControls();
            updateBattleEntities();
        }

    }

    public void updateBattleEntities(){
        if(currentEnemy.dead || currentEnemy.saved){
            if(currentEnemy.dead) player.purity--;
            else if(currentEnemy.saved) player.purity++;
            currentBattle.end(this);
        }
        else{
            for (Entity battleEntity : battleEntities) {
                battleEntity.update(this);
            }
        }
    }

    private void defaultSettings(){
        //fullscreen = true;
        volume = 1.0F;
        //resolutionChanged = true;
        settingsChanged = true;
    }

    private void sortObjectsByPosition() {
        toUpdate.sort(Comparator.comparing(entity -> entity.getPosition().getY()));
    }

    public void changeFullscreen(){
        if(fullscreen == true) fullscreen = false;
        else fullscreen = true;
        resolutionChanged = true;
    }

    public void changeSettings(){
        bgm.setVolume(volume);
    }

    public void checkBattleControls(){
        if (actionTimer > 0) actionTimer--;

        if(input.isPressed(KeyEvent.VK_LEFT)) {
            leftPressed = true;
        }
        else {
            leftPressed = false;
        }
        if(input.isPressed(KeyEvent.VK_RIGHT)) {
            rightPressed = true;
        }
        else {
            rightPressed = false;
        }
        if(input.isPressed(KeyEvent.VK_UP)) {
            upPressed = true;
        }
        else {
            upPressed = false;
        }
        if(input.isPressed(KeyEvent.VK_DOWN)){
            downPressed = true;
        }
        else {
            downPressed = false;
        }

        if(input.isPressed(KeyEvent.VK_Z)){
            if(attackPressedPrev == false) {
                if(actionTimer == 0){
                    attackPressed = true;
                    actionTimer = 300;
                    drawAttack = true; 
                }
                attackPressedPrev = true;
            }
        }
        else if (!input.isPressed(KeyEvent.VK_Z)) {
            attackPressedPrev = false;
            attackPressed = false;
        }

        if(input.isPressed(KeyEvent.VK_X)){
            if(prayPressedPrev == false) {
                if(actionTimer == 0) {
                    prayPressed = true;
                    actionTimer = 300;
                    drawPray = true;
                }
                prayPressedPrev = true;
            }
        }
        else if (!input.isPressed(KeyEvent.VK_X)) {
            prayPressedPrev = false;
            prayPressed = false;
        }

        if(actionTimer == 150) {
            drawAttack = false;
            drawPray = false;
        }
    }

    public void checkWalkingControls(){
        if(input.isPressed(KeyEvent.VK_LEFT)) {
            leftPressed = true;
        }
        else {
            leftPressed = false;
        }
        if(input.isPressed(KeyEvent.VK_RIGHT)) {
            rightPressed = true;
        }
        else {
            rightPressed = false;
        }
        if(input.isPressed(KeyEvent.VK_UP)) {
            upPressed = true;
        }
        else {
            upPressed = false;
        }
        if(input.isPressed(KeyEvent.VK_DOWN)){
            downPressed = true;
        }
        else {
            downPressed = false;
        }

        if(input.isPressed(KeyEvent.VK_M)){
            if(menuPressedPrev == false) {
                status = MENU_STATUS;
                menuPressedPrev = true;
            }
        }
        else if (!input.isPressed(KeyEvent.VK_M)) menuPressedPrev = false;

        if(input.isPressed(KeyEvent.VK_Z)){
            if(interactPressedPrev == false) {
                interactPressed = true;
                interactPressedPrev = true;
            }
        }
        else if (!input.isPressed(KeyEvent.VK_Z)) {
            interactPressed = false;
            interactPressedPrev = false;
        }

        if(input.isPressed(KeyEvent.VK_ESCAPE)){
            closeWindow = true;
        }

    }

    public void checkDialogueControls(){
        if(input.isPressed(KeyEvent.VK_Z)){
            if(interactPressedPrev == false) {
                interactPressedPrev = true;
                if(ui.dialogue.indexOf(ui.currentDialogue) == ui.dialogue.size()-1){
                    ui.dialogueOver = true;
                    status = WALKING_STATUS;
                }
                else{
                    ui.dialogueOver = false;
                    ui.currentDialogue = ui.dialogue.get(ui.dialogue.indexOf(ui.currentDialogue)+1);
                }
            }
        }
        else if (!input.isPressed(KeyEvent.VK_Z)) {
            interactPressedPrev = false;
        }

        if(input.isPressed(KeyEvent.VK_X)){
            status = WALKING_STATUS;
        }
    }

    public void checkMenuControls(){
        if(input.isPressed(KeyEvent.VK_M)){
            if(menuPressedPrev == false) {
                status = WALKING_STATUS;
                menuPressedPrev = true;
            }
        }
        else if (!input.isPressed(KeyEvent.VK_M)) menuPressedPrev = false;
    }

    public void checkSettingsControls(){

        if(input.isPressed(KeyEvent.VK_UP)){
            if(upPressedPrev == false) {
                ui.commandNum--;
                upPressedPrev = true;
                downPressedPrev = false;
            }
            if(ui.commandNum < 0) ui.commandNum = 1;
        }
        else if (!input.isPressed(KeyEvent.VK_UP)) upPressedPrev = false;

        if(input.isPressed(KeyEvent.VK_DOWN)){
            if(downPressedPrev == false) {
                ui.commandNum++;
                downPressedPrev = true;
                upPressedPrev = false;
            }
            if(ui.commandNum > 1) ui.commandNum = 0;
        }
        else if (!input.isPressed(KeyEvent.VK_DOWN)) downPressedPrev = false;

        if(ui.commandNum == 0){
            if(input.isPressed(KeyEvent.VK_LEFT)){
                if(volume > 0.02F) volume -=  0.01F;
                else volume = 0.0F;
                //else volume = 0.1F;
                System.out.println(volume);
                settingsChanged = true;
            }
    
            if(input.isPressed(KeyEvent.VK_RIGHT)){
                if(volume < 0.99F) volume += 0.01F;
                else volume = 1.0F;
                System.out.println(volume);
                settingsChanged = true;
            }    
        }

        if(ui.commandNum == 1){
            if(input.isPressed(KeyEvent.VK_Z)){
                changeFullscreen();
                input.unPress(KeyEvent.VK_Z);
            }
        }

        if(input.isPressed(KeyEvent.VK_X)){
            status = TITLE_STATUS;
        }
    }

    public void checkTitleControls(){
        //TITLE STATUS

        if(ui.titleScreenState == 0){
            if(input.isPressed(KeyEvent.VK_UP)){
                if(upPressedPrev == false) {
                    ui.commandNum--;
                    upPressedPrev = true;
                    downPressedPrev = false;
                }
                if(ui.commandNum < 0) ui.commandNum = 3;
            }
            else if (!input.isPressed(KeyEvent.VK_UP)) upPressedPrev = false;
    
            if(input.isPressed(KeyEvent.VK_DOWN)){
                if(downPressedPrev == false) {
                    ui.commandNum++;
                    downPressedPrev = true;
                    upPressedPrev = false;
                }
                if(ui.commandNum > 3) ui.commandNum = 0;
            }
            else if (!input.isPressed(KeyEvent.VK_DOWN)) downPressedPrev = false;
    
            if(input.isPressed(KeyEvent.VK_Z)){
                if(interactPressedPrev == false){
                    if(ui.commandNum == 0) {
                        status = WALKING_STATUS;
                        stopMusic();
                        playMusic(LIMBO_MUSIC);
                        changeSettings();
                    }
                    else if (ui.commandNum == 1) status = SETTINGS_STATUS;
                    else if (ui.commandNum == 2) ui.titleScreenState = 1;
                    else if (ui.commandNum == 3) closeWindow = true;
                    interactPressedPrev = true;
                }
                ui.commandNum = 0;
            } else if (!input.isPressed(KeyEvent.VK_Z)) interactPressedPrev = false;

            if(input.isPressed(KeyEvent.VK_ESCAPE)){
                closeWindow = true;
            }
        }

        else if (ui.titleScreenState == 1) {
            if(input.isPressed(KeyEvent.VK_X)){
                    ui.titleScreenState = 0;
            }
        }
    }

    public void playMusic(int musicNum){
        bgm.setFile(musicNum);
        bgm.play();
        bgm.loop();
    }

    public void stopMusic(){
        bgm.stop();
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
            for (Entity other: entities) {
                if(hitbox.collidesWith(other.getHitbox()) && !hitbox.equals(other.getHitbox())) collided.add(other);
            }
            return collided;
        }

        public UI getUI(){
            return ui;
        }
        
        public Sound getSound(){
            return bgm;
        }

        public ArrayList<Entity> getBattleEntities(){
            return battleEntities;
        }

        public PlayerSoul getPlayerSoul(){
            return playerSoul;
        }


    //=================================================================================================================


    public void setCurrentRoom(Room newRoom){
        roomManager.setCurrentRoom(newRoom);
        for (int i = 0; i < newRoom.getEnemiesArr().length; i++){
            for(int j = 0; j < newRoom.getEnemiesArr()[0].length; j++){
                if(newRoom.getEnemiesArr()[i][j] != null) entities.add(Enemy.createEnemy(newRoom.getEnemiesArr()[i][j], new Position(TileCoords.coordsToPosition(i, j).getX(), TileCoords.coordsToPosition(i, j).getY() - GameFrame.ORIGINAL_TILE_SIZE/2)));
            }
        }
    }

    public void setPlayerSoul(PlayerSoul ps){
        playerSoul = ps;
    }

}

