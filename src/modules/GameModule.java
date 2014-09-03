package modules;

import game.GameFramework;
import util.Log;

import java.awt.*;

/**
 * Created by Shorty on 8/9/2014.
 */
public abstract class GameModule implements Log {
    protected GameFramework game;
    protected String key;
    protected boolean initialized;

    public GameModule(GameFramework game, String key){
        this(game, key, true);
    }
    public GameModule(GameFramework game, String key, boolean initialize){
        this.game = game;
        this.key = key;
        if(initialize && initialize()){
            setInitialized(true);
        }
    }

    public String getKey(){
        return key;
    }

    public boolean isInitialized(){
        return initialized;
    }
    public void setInitialized(boolean initialized){
        this.initialized = initialized;
    }

    public void setGame(GameFramework game){
        this.game = game;
    }

    public abstract boolean initialize();
    public abstract void input();
    public abstract void update(double delta);
    public abstract void render(Graphics g);
}
