import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.*;

public class GamePanel extends JPanel implements Runnable{

    final int screenWidth = 1000;
    final int screenHeight = 750;

    final int FPS = 60;
    
    GameState keyH = new GameState();
    Thread gameThread;

    Player player;
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);   //already default in jpanel
        this.addKeyListener(keyH);
        this.setFocusable(true);    //already default in jpanel or more specifically in Component class
    }

    public void update(){

        for (Entity e : entityList) {
            e.update();
        }

    }

    public void paintComponent (Graphics g){
    
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (Entity e : entityList) {
            e.draw(g2);
        }

        g2.dispose();
    }

    public void setupGame(){
        state = TITLESTATE;
    }
}
