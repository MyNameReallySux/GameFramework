package modules;

import game.GameFramework;
import modules.tests.DebugMenu;

import java.awt.*;

/**
 * Created by Shorty on 8/17/2014.
 */
public class DebuggingModule extends GameModule {
    DebugMenu menu;

    public DebuggingModule(GameFramework game) {
        super(game, "Debugging Module");
    }

    @Override
    public boolean initialize() {
        menu = new DebugMenu(game);
        game.getWindow().setJMenuBar(menu);
        menu.revalidate();
        return true;
    }

    @Override
    public void input(double delta) {

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {

    }
}
