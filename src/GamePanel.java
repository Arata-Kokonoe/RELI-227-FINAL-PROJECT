import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    final int screenWidth = 1000;
    final int screenHeight = 750;

    final int FPS = 60;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    int playerX;
    int playerY;
    int playerSpeed;

    public int state;
    private final int TITLESTATE = 0;
    private final int GAMESTATE = 1;

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
                repaint();
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

        if(keyH.upPressed == true){
            playerY -= playerSpeed;
        }
        if(keyH.downPressed == true){
            playerY += playerSpeed;
        }
        if(keyH.leftPressed == true){
            playerX -= playerSpeed;
        }
        if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }

    }

    public void paintComponent (Graphics g){
    
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, 100, 100);

        g2.dispose();
    }

    public void setupGame(){
        state = 0;
        playerX = 100;
        playerY = 100;
        playerSpeed = 4;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
}
