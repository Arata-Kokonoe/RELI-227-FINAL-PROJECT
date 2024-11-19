package main;
public class GameLoop implements Runnable{

    public static final int FPS = 60;

    private GameFrame frame;
    private GameState state;

    public GameLoop(GameFrame f){
        frame = f;
    }

    public void init(){
        state = new GameState();
        frame.addKeyListener(state.getKeyListener());
    }

    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(state != null){
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                frame.draw(state);
                state.update();
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
