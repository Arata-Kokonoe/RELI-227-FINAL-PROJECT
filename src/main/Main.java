package main;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {

        ThreadPool.init();

        EventQueue.invokeLater(new Runnable(){
            @Override
			public void run() {
				GameFrame frame = new GameFrame("Game Title");
				frame.setLocationRelativeTo(null); // put frame at center of screen
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.initBufferStrategy();

				// Create and execute the game-loop
				GameLoop game = new GameLoop(frame);
				game.init();
				ThreadPool.execute(game);

				// and the game starts ...
            }
        });
    }
}
