package entities;

import main.GameState;

public class Player extends Entity {
    public Player(GameState s){
        super(s);
    }

    public void update(GameState s){
        if(s.upPressed == true){
            y -= speed;
        }
        if(s.downPressed == true){
            y += speed;
        }
        if(s.leftPressed == true){
            x -= speed;
        }
        if(s.rightPressed == true){
            x += speed;
        }
    }
    
}
