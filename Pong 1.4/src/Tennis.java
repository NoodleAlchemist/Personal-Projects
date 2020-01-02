import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tennis extends Applet implements Runnable, KeyListener {

    final int WIDTH = 700, HEIGHT = 500;
    Thread thread;
    PlayerPaddle p1, p2;
    Ball ball;
    boolean gameStart;
    Graphics gfx;
    Image img;

    public void init(){
        this.resize(WIDTH, HEIGHT);
        gameStart = false;
        this.addKeyListener(this);
        p1 = new PlayerPaddle(1);
        p2 = new PlayerPaddle(2);
        ball = new Ball();
        img = createImage(WIDTH, HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics g){
        gfx.setColor(Color.black);
        gfx.fillRect(0,0, WIDTH, HEIGHT);
        if(ball.getX() < -10 || ball.getX() > 710){
            gfx.setColor(Color.MAGENTA);
            gfx.drawString("GAME OVER", 340, 250);
        }
        else {
            p1.draw(gfx);
            p2.draw(gfx);
            ball.draw(gfx);
        }
        if (!gameStart){
            gfx.setColor(Color.BLUE);
            gfx.drawString("P I N G_P O N G", 300, 100);
            gfx.drawString("P R E S S_S P A C E_T O_B E G I N", 300, 130);
        }
        g.drawImage(img, 0,0, this);
    }

    public void update(Graphics g){
        paint(g);
    }

    public void run() {
        while (true) {

            if (gameStart) {
                p1.move();
                p2.move();
                ball.move();
                ball.checkCollison(p1, p2);
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            gameStart = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_W){
            p1.setUpAccel(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S){
            p1.setDownAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP){
            p2.setUpAccel(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            p2.setDownAccel(true);
        }

    }


    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            p1.setUpAccel(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S){
            p1.setDownAccel(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            p2.setUpAccel(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            p2.setDownAccel(false);
        }

    }
}
