import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("change name later");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);  // adds the jpanel to the jframe
        
        window.pack();  //causes window to be sized to fit preferred size and layouts of its subcompoenents (i.e. GamePanel)

        window.setLocationRelativeTo(null); //causes window to be centered
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
