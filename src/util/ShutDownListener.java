package util;

import game.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Shorty on 8/10/2014.
 */
public class ShutDownListener extends KeyAdapter {
    Game game;
    int shutdownKey;

    public ShutDownListener(Game game){
        this(game, KeyEvent.VK_ESCAPE);
    }

    public ShutDownListener(Game game, int shutdownKey){
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
            if(Game.display().isFullScreen())
                Game.display().exitFullScreen();
            game.onShutDown();
        }
    }
}
