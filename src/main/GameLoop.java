package main;

import javax.swing.JPanel;

public class GameLoop extends JPanel implements Runnable{

    //FPS
    final int FPS = 60;

    ///Game State and Frame
    private GameState gameState;
    private GameFrame gameFrame;

    public GameLoop(GameFrame frame){
        gameFrame = frame;
    }

    public void init(long seed) {
        gameState = new GameState(seed);
        gameFrame.addKeyListener(gameState.getInput());
    }

    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(this != null){
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                gameState.update(); //first update the gameState
                gameFrame.render(gameState); //then draw to the window based on the gameState
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
}