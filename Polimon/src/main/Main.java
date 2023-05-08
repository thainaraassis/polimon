package main;

import javax.swing.JFrame;
//teste
public class Main {
        public static void main(String[] args) {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  /*fecha quando clicka o X na tela */
            window.setResizable(false);
            window.setTitle("Pólimon");

            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);

            window.pack();

            window.setLocationRelativeTo(null);
            window.setVisible(true);

            gamePanel.startGameThread();
        }
}
