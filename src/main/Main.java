package main;
import java.awt.EventQueue;
import java.util.Random;

import javax.swing.JFrame;

public class Main {

    public static JFrame window;
    public static void main(String[] args) throws Exception {
        // Initialize the global thread-pool
		ThreadPool.init();
		
		// Show the game menu ...
			//if no seed is selected
			long seed = new Random().nextLong();
		
		// After the player clicks 'PLAY' ...
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameFrame frame = new GameFrame("Game Title");
				frame.setLocationRelativeTo(null); // put frame at center of screen
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.initBufferStrategy();
				// Create and execute the game-loop
				GameLoop game = new GameLoop(frame);
				game.init(seed);
				ThreadPool.execute(game);
				// and the game starts ...
			}
		});
    }
}
