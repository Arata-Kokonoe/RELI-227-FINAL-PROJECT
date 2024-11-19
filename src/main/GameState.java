package main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import entities.Entity;
import entities.Player;

public class GameState{

    private KeyHandler keyHandler;
    //private MouseHandler mousehandler; 

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    Player player;
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    private int state;
    public final int TITLE_STATE = 0;
    public final int GAME_STATE = 1;
    public final int GAME_OVER_STATE = -1;

    public GameState(){
        keyHandler = new KeyHandler();
        //mouseHandler = new MouseHandler();

        initGameState();
    }

    private void initGameState(){
        state = TITLE_STATE;
        player = new Player(this);
        entityList.add(player);
    }

    public void update(){
        if(state == TITLE_STATE){

        }
        else if(state == GAME_STATE){
            player.update(this);
        }
    }

    class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            if(state == GAME_STATE){
                if(code == KeyEvent.VK_W){
                    upPressed = true;
                }
                if(code == KeyEvent.VK_S){
                    downPressed = true;
                }
                if(code == KeyEvent.VK_A){
                    leftPressed = true;
                }
                if(code == KeyEvent.VK_D){
                    rightPressed = true;
                }
            }

            else if (state == TITLE_STATE){
                if(code == KeyEvent.VK_SPACE){
                    state = GAME_STATE;
                }
            }
        }

		@Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();

            if(state == GAME_STATE){
                if(code == KeyEvent.VK_W){
                    upPressed = false;
                }
                if(code == KeyEvent.VK_S){
                    downPressed = false;
                }
                if(code == KeyEvent.VK_A){
                    leftPressed = false;
                }
                if(code == KeyEvent.VK_D){
                    rightPressed = false;
                }
            }
        
            else if (state == TITLE_STATE){

            }

        }

	}

    public KeyListener getKeyListener(){
        return keyHandler;
    }

    public int getState(){
        return state;
    }

    

}
