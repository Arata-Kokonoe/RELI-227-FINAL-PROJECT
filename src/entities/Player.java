package entities;

import main.GamePanel;

public class Player extends Entity {

    public void update(GamePanel gp){
        if(gp.keyH.leftPressed == true){
            x -= speed;
        }
        if(gp.keyH.rightPressed == true){
            x += speed;
        }
        if(gp.keyH.upPressed == true){
            y -= speed;
        }
        if(gp.keyH.downPressed == true){
            y += speed;
        }
    }
}
