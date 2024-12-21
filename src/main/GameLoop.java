//  PACKAGE
package main;

//=================================================================================================================
//  IMPORTS
    import javax.swing.JPanel;
//=================================================================================================================


//=================================================================================================================
// The GameLoop class holds the run method that allows the program to update based on the FPS (which means if FPS
// is 60, the run method will update and draw to frame 60 times every second)
// This GameLoop uses delta time so even if frame rate lags or increases, the physics of the game will still run
// the same.
//=================================================================================================================


//=================================================================================================================
public class GameLoop extends JPanel implements Runnable{
//=================================================================================================================
    
    //=============================================================================================================
    //  MEMBER VARIABLES

        //  FPS
        final int FPS = 60;

        //  GAME STATE AND GAME FRAME
        private GameState gameState;
        private GameFrame gameFrame;
    //=============================================================================================================
    
    
    //=============================================================================================================
    //  CONSTRUCTOR
    public GameLoop(GameFrame frame){
        gameFrame = frame;
    }
    //=============================================================================================================


    //=============================================================================================================
    public void init(long seed) {
        gameState = new GameState(seed);
        gameFrame.addKeyListener(gameState.getInput());
    } // init(long seed)
    // Creates a new GameState and adds a KeyListener to GameFrame so it can listen to user input.
    //=============================================================================================================


    //=============================================================================================================
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

    } // run()
    // Controls the speed at which the game updates and draws to the GameFrame
    //=============================================================================================================


//=================================================================================================================
} // class GameLoop
//=================================================================================================================