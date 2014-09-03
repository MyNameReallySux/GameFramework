package modules.tests;

import game.GameFramework;
import input.mouse.AbsoluteMouseInput;
import input.mouse.RelativeMouseInput;
import modules.GameModule;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Shorty on 8/11/2014.
 */
public class RelativeMouseTest extends GameModule {
    Point point;

    public RelativeMouseTest(GameFramework game) {
        super(game, "RelativeMouseTest");
    }

    @Override
    public boolean initialize() {
        point = new Point(0, 0);
        return true;
    }

    @Override
    public void input() {
        if(game.keyboard.keyDownOnce(KeyEvent.VK_R)){
            if(game.mouse instanceof RelativeMouseInput) game.setMouseController(new AbsoluteMouseInput(game));
            else game.setMouseController(new RelativeMouseInput(game));
        }

        if(game.keyboard.keyDownOnce(KeyEvent.VK_C)){
            game.getWindow().setDisableCursor(!game.getWindow().isCursorDisabled());
        }
    }

    @Override
    public void update(double delta) {
        point = game.mouse.getPosition();

        if(point.x + 25 < 0)
            point.x = game.getWidth() -1;
        else if(point.x > game.getWidth() -1)
            point.x = -25;
        if(point.y + 25 < 0)
            point.y = game.getHeight() -1;
        else if(point.y > game.getHeight() -1)
            point.y = -25;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(point.x, point.y, 25, 25);

        g.setColor(Color.WHITE);
        g.drawString("Press 'C' to toggle cursor", 30, 340);
        g.drawString("Press 'R' to toggle relative mouse movement", 30, 365);
    }
}
