package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.*;

public class GameFrame extends JFrame{

    public final int GAME_WIDTH = 1000;
    public final int GAME_HEIGHT = 750;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title){
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
    }

    public void initBufferStrategy(){
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    public void draw(GameState s){
        do{
            
            do{

                Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    //draw stuff
                    if(s.getState() == s.GAME_STATE){ //if we're in game

                        for (Entity e : s.entityList) {
                            e.draw(g2);
                        }

                    }
                    else if (s.getState() == s.TITLE_STATE){
                        g2.setColor(Color.GRAY);
                        g2.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                    }
                    
                } finally{
                    g2.dispose();
                }

            } while(bufferStrategy.contentsRestored());

        } while (bufferStrategy.contentsLost());
        
    }

}
