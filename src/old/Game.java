package old;

import util.FrameRate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by Shorty on 8/2/2014.
 */
public class Game extends JFrame implements Runnable {
    private volatile boolean running;
    private Thread gameThread;
    private FrameRate frameRate;
    public BufferStrategy bs;
    private Display display;
    public Canvas canvas;

    public Game(){
       frameRate = new FrameRate();
       display = new Display(this);
    }

    public void initialize(){
        frameRate.initialize();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeGUI(new Dimension(display.getWidth(), display.getHeight()));
                createBufferStrategy();
            }
        });
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                display.createAndShowGUI();
            }
        });
    }

    public void initKeyInput() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE && display.fullScreen)
                    display.setFullScreen(false);
                if (e.getKeyCode() == KeyEvent.VK_F && !display.fullScreen)
                    display.setFullScreen(true);
            }
        });
    }

    public void initializeGUI(Dimension dimension){
        initCanvas(dimension);
        initKeyInput();

        setTitle("Game Framework");
        setIgnoreRepaint(true);
        pack();
    }

    public void initCanvas(Dimension dimension){
        canvas = new Canvas();
        canvas.setSize(dimension);
        canvas.setBackground(Color.BLACK);
        getContentPane().add(canvas);
    }

    public void createBufferStrategy(){
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
    }

    public void start(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        running = true;
        while(running){
            gameLoop();
        }
    }

    public void gameLoop(){
        do{
            do{
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    render(g);
                } catch(IllegalStateException e){
                    e.printStackTrace();
                } finally {
                    if (g != null){
                        g.dispose();
                    }
                }
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    public void render(Graphics g) {
        frameRate.calculate();
        g.setColor(Color.GREEN);
        g.drawString(frameRate.getFrameRate(), 30, 30);

        g.setColor(Color.WHITE);
        g.drawString("Esc)      Exit FullScreen", 30, 60) ;
        g.drawString("F-Key)    Enter FullScreen", 30, 90);
    }

    public void sleep(long duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutDown(){
        try {
            System.out.print("Stopping game thread... ");
            running = false;
            gameThread.join();
            System.out.println("Stopped!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
