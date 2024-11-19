public class GameLoop implements Runnable{

    public static final int FPS = 60;

    private GamePanel panel;
    private GameState state;
    private Thread gameThread;

    public GameLoop(GamePanel p){
        panel = p;
    }

    public void init(){
        state = new GameState();
        panel.addKeyListener(state.getKeyListener());
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
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
                panel.draw();
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
