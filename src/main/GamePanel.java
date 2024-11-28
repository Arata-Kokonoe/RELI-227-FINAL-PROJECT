package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Entity;
import entities.Player;
import tile.RoomManager;

public class GamePanel extends JPanel implements Runnable{
    
    //SCREEN SETTINGS
    public static final int TILE_SIZE = 48;
    public static final int MAX_COL = 20;
    public static final int MAX_ROW = 12;
    public int screenWidth = TILE_SIZE * MAX_COL;
    public int screenHeight = TILE_SIZE * MAX_ROW;

    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;

    //FPS
    final int FPS = 60;
    
    //SYSTEM
    public KeyHandler keyH = new KeyHandler(this);
    public RoomManager rm = new RoomManager(this);
    private Thread gameThread;
    
    //ENTITIES
    public Player player;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> drawList = new ArrayList<Entity>();

    //STATE
    public int state;
    public static final int TITLE_STATE = 0;
    public static final int GAME_STATE = 1;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);   //already default in jpanel
        this.addKeyListener(keyH);
        this.setFocusable(true);    //already default in jpanel or more specifically in Component class
    }

    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                drawToTempScreen();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update(){

        if(state == GAME_STATE){
            for (Entity e : entities) {
                e.update(this);
            }    
        }
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (tempScreen != null) {
           g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        }

     }
  

    private void drawToTempScreen(){

        Graphics2D g2 = tempScreen.createGraphics();
        g2.clearRect(0, 0, screenWidth, screenHeight);

        if(state == GAME_STATE){
            for (Entity e : entities) {
                drawList.add(e);
            }
            
            for (Entity e : drawList) {
                e.draw(g2);
            }

            rm.draw(g2);

            drawList.clear();
        }

        g2.dispose();
        repaint();
        
    }

    public void setupGame(){
        player = new Player();
        entities.add(player);

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public void setFullscreen(){
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULLSCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        //setFullscreen();
    }
}