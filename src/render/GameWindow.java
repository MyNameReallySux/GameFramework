package render;

import game.Game;

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
    public ComponentAdapter onResizeListener;
    protected Game game;
    protected volatile boolean disableCursor;


    public GameWindow(Game game){
        this.game = game;
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
        game.setSize(Game.display().getSize());
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
                Game.screen().onResize();
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
