//  PACKAGE
package main;

//=================================================================================================================
//  IMPORTS
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Graphics2D;
    import java.awt.GraphicsDevice;
    import java.awt.GraphicsEnvironment;
    import java.awt.Toolkit;
    import java.awt.PageAttributes.OrientationRequestedType;
    import java.awt.image.BufferStrategy;
    import java.awt.image.BufferedImage;

    import javax.swing.JFrame;
    import core.Position;
import entities.Entity;
import room.Room;
//=================================================================================================================


//=================================================================================================================
public class GameFrame extends JFrame {
//=================================================================================================================

    //=============================================================================================================
    //  MEMBER VARIABLES

        // SCREEN SETTINGS
        public static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile size 
        public static final int MAX_COL = 40;
        public static final int MAX_ROW = 30;

        public static final int GAME_HEIGHT = ORIGINAL_TILE_SIZE * MAX_ROW;
        public static final int GAME_WIDTH = ORIGINAL_TILE_SIZE * MAX_COL;

        //  WINDOW SETTINGS
        private int windowWidth = GAME_WIDTH;
        private int windowHeight = GAME_HEIGHT;

        // BUFFER STRATEGY
        private BufferedImage tempScreen;
        private BufferStrategy bufferStrategy;

        //  GRAPHICS ENVIRONMENT & DEVICE
        private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        private GraphicsDevice gd = ge.getDefaultScreenDevice();
    //=============================================================================================================
	

    //=============================================================================================================
    //  CONSTRUCTOR
        public GameFrame(String title) {
            super(title);
            this.setResizable(false);
            this.setSize(GAME_WIDTH, GAME_HEIGHT);
            this.setBackground(Color.BLACK);
            tempScreen = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }
    //=============================================================================================================
	

    //=============================================================================================================
        public void initBufferStrategy() {
            // Triple-buffering
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy();
        } // initBufferStrategy()
    //  Adds a buffer strategy to the JFrame, allowing us to "flip" through screen buffers that we have already 
    //  drawn to instead of completely redrawing the objects onto the screen every time when we render, which 
    //  leads to screen tearing and flickering.
    //=============================================================================================================

	
    //=============================================================================================================
        public void render(GameState gameState) {
            // Get a new graphics context to render the current frame
            Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
            try {
                // Do the rendering
                doRendering(graphics, gameState);
            } finally {
                // Dispose the graphics, because it is no more needed
                graphics.dispose();
            }
            // Display the buffer
            bufferStrategy.show();

            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

        } // render(GameState gameState)
    //  Calls doRendering(Graphics2D g2d, GameState gameState) method to draw to screen buffer and then 
    //  shows the buffer.
    //=============================================================================================================
	

    //=============================================================================================================
        private void doRendering(Graphics2D g2d, GameState gameState) {
            //get graphics for the temporary image we draw to
            Graphics2D g2temp = (Graphics2D)tempScreen.getGraphics();
        
            //draw background
            g2temp.setColor(Color.WHITE);
            g2temp.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            
            //draw all elements to temp image
            renderRoom(g2temp, gameState);
            //for all entities in gameState, if in camera view, draw.
            Camera camera = gameState.getCamera();
            for (Entity e : gameState.getEntities()) {
                if(camera.isInView(e)) e.draw(g2temp, camera);
            }

            //dispose of the graphics of temp image
            g2temp.dispose();

            //check if fullscreen

            //draw to bufferStrategy
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, windowWidth, windowHeight);
            g2d.drawImage(tempScreen, 0, 0, windowWidth, windowHeight, null);
        } // doRendering(Graphics2D g2d, GameState gameState)
    //  Draws the game state to a temporary screen based on the window size and then draws that temporary image
    //  to the screen buffer.
    //==============================================================================================================


    //=============================================================================================================
        private void renderRoom(Graphics2D g2d, GameState gameState) {
            Room room = gameState.getCurrentRoom();
            Camera camera = gameState.getCamera();

            Position start = room.getViewableStartingGridPosition(camera);
            Position end = room.getViewableEndingGridPosition(camera);

            for(int x = start.intX(); x < end.intX(); x++) {
                for(int y = start.intY(); y < end.intY(); y++) {
                    g2d.drawImage(
                            room.getTileArr()[x][y].getSprite(),
                            x * ORIGINAL_TILE_SIZE - camera.getPosition().intX(),
                            y * ORIGINAL_TILE_SIZE - camera.getPosition().intY(),
                            null
                    );
                }
            }
        } // renderRoom(GameState gameState, Graphics2D graphics)
    //  Draws the tiles of the current room the user is in.
    //=============================================================================================================


    //=============================================================================================================
        public void setFullscreen(){
            //GET LOCAL SCREEN DEVICE
            
            setVisible(false);
            dispose();
            setUndecorated(true);
            gd.setFullScreenWindow(this);
            

            //GET FULLSCREEN WIDTH AND HEIGHT
            windowWidth = this.getWidth();
            windowHeight = this.getHeight();
            System.out.println(windowHeight);
        } // setFullscreen()
    //  Sets the window to fullscreen mode.
    //=============================================================================================================


    //=============================================================================================================
        public void setWindowed(){

            dispose();
            setUndecorated(false);
            gd.setFullScreenWindow(null);
            setSize(GAME_WIDTH, GAME_HEIGHT);
            setLocationRelativeTo(null);
            setVisible(true);

            windowWidth = this.getWidth();
            windowHeight = this.getHeight();
            System.out.println(windowHeight);
        } // setWindowed()
    //  Sets the window to windowed mode.
    //=============================================================================================================
}
