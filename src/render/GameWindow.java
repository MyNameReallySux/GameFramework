package render;

import game.GameFramework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Game Framework
 * Created by MyNameReallySux on 8/20/2014.
 * Copyright 2014©
 */

public class GameWindow extends JFrame {
    protected GameFramework game;
    protected Display display;
    protected volatile boolean disableCursor;

    public ComponentAdapter onResizeListener;


    public GameWindow(GameFramework game){
        this.game = game;
        this.display = game.getDisplay();
        initialize();
        game.createBufferStrategy(2);
        addWindowCloseListener();
        addWindowResizeListener();
    }

    public void initialize(){
        setUndecorated(false);
        setBackground(Color.BLACK);
        add(game);
        setWindowSize();
        setVisible(true);
        game.requestFocus();
    }

    public void setWindowSize(){
        game.setSize(display.getSize());
        pack();
    }

    @Override
    public void dispose(){
        setVisible(false);
        super.dispose();
    }

    public void dispose(boolean undecorated){
        setVisible(false);
        super.dispose();
        setUndecorated(undecorated);
    }

    public void addWindowCloseListener(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.onShutDown();
            }
        });

    }
    public void addWindowResizeListener(){
        onResizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                game.getScreen().onResize();
            }
        };
        addComponentListener(onResizeListener);
    }

    public void removeWindowResizeListener(){
        removeComponentListener(onResizeListener);
    }

    public Point getCenter(){
        return new Point(game.getWidth() / 2, game.getHeight() / 2);
    }
    public boolean isCursorDisabled(){
        return disableCursor;
    }
    public void setDisableCursor(boolean disableCursor){
        this.disableCursor = disableCursor;
        if(disableCursor){
            disableCursor();
        } else {
            setCursor();
        }
    }

    public void setCursor(){
        game.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void setCursor(Image image){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Point point = new Point(0, 0);
        String name = "Custom Cursor";
        Cursor cursor = tk.createCustomCursor(image, point, name);
        game.setCursor(cursor);
    }
    public void setCursor(Cursor cursor){
        game.setCursor(cursor);
    }
    public void disableCursor(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        setCursor(tk.createImage(""));
    }
}
