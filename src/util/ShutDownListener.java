package util;

import game.GameFramework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Shorty on 8/10/2014.
 */
public class ShutDownListener extends KeyAdapter {
    GameFramework game;
    int shutdownKey;

    public ShutDownListener(GameFramework game){
        this(game, KeyEvent.VK_ESCAPE);
    }

    public ShutDownListener(GameFramework game, int shutdownKey){
        this.shutdownKey = shutdownKey;
        this.game = game;
    }

    public void setShutdownKey(int shutdownKey){
        this.shutdownKey = shutdownKey;
    }

    @Override
    public void keyPressed(KeyEvent e){
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && game.isRunning()){
            if(game.getDisplay().isFullScreen())
                game.getDisplay().exitFullScreen();
            game.onShutDown();
        }
    }
}
