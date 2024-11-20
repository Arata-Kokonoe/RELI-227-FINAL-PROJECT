package main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

public class KeyHandler extends KeyAdapter{

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GamePanel gp;

    public KeyHandler(GamePanel panel){
        gp = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.state == GamePanel.GAME_STATE){
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

        else if (gp.state == GamePanel.TITLE_STATE){
            if(code == KeyEvent.VK_SPACE){
                gp.state = GamePanel.GAME_STATE;
            }
        }

        if(code == KeyEvent.VK_ESCAPE){
            Main.window.dispatchEvent(new WindowEvent(Main.window, WindowEvent.WINDOW_CLOSING));
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.state == GamePanel.GAME_STATE){
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
        
        else if (gp.state == GamePanel.TITLE_STATE){

        }

    }

}
