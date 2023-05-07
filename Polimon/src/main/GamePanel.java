package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    /* SCREEN SETTINGS */
    final int originalTileSize = 16;   //size = 16x16 -> tamanho dos NPCs e do player etc
    final int scale = 3;               //reescala para melhorar a resolucao

    final int tileSize = originalTileSize * scale; // size = 48x48 tile
    final int vertical = 32;
    final int horizontal = 18;
    final int tamanho_vertical = tileSize * vertical; //1536 pixels
    final int tamanho_horizontal = tileSize * horizontal; // 1536 pixels

    /*EM JAVA, o INICIO DA TELA SE DÁ NO CANTO SUPERIOR DIREITO, LÁ É O (0,0) */

    //FPS
    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    /*Posição inicial do Player */

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;



    public GamePanel(){
        this.setPreferredSize(new Dimension (tamanho_vertical,tamanho_horizontal));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);  /* melhora performance de renderização */
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double  drawInterval = 1000000000/FPS;  /*1 000 000 000 é um segundo em nanosegundos. 
        Dividindo 1 000 000 000 / FPS podemos fazer as atualizações de tela acontecerem a cada 1 000 000 000 / 60 = 16 666 666 nanosegundos ou 0.016 segundos */

        double nextDrawTime = System.nanoTime() + drawInterval;
 

        while(gameThread != null) {

            update();   /* Atualiza o movimento do player */
            repaint();  /*recria o mapa de acordo com o movimento do player */

            //DRAW THE SCREEN
           

            try {
                 double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if(keyH.upPressed == true){
            playerY -= playerSpeed; 
        }
        else if (keyH.downPressed == true){
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed == true){
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed == true){
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g) {            /*Usado para criar o mapa */
        super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D)g; 
        
        g2.setColor(Color.white);
        g2.fillRect(playerX,playerY,tileSize,tileSize); /*Character com o tamanho de um quadrado */
        g2.dispose();
    }
}   
