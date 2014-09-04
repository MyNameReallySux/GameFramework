package modules.tests;

import game.GameFramework;
import modules.GameModule;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Shorty on 8/11/2014.
 */
public class InputConsole extends GameModule {
    private Point startingPosition;

    public InputConsole (GameFramework game){
        super(game, "InputConsole");
        this.startingPosition = new Point(0, 0);
    }

    @Override
    public boolean initialize() {
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
        g.setColor(Color.WHITE);
        g.translate(startingPosition.x, startingPosition.y);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_UP), 30, 80);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_DOWN), 30, 105);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_LEFT), 30, 130);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_RIGHT), 30, 155);

        g.drawString(game.keyboard.keyState(KeyEvent.VK_W), 200, 80);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_A), 200, 105);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_S), 200, 130);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_D), 200, 155);

        g.drawString(game.keyboard.keyState(KeyEvent.VK_1), 370, 80);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_2), 370, 105);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_3), 370, 130);
        g.drawString(game.keyboard.keyState(KeyEvent.VK_4), 370, 155);

        g.drawString(game.mouse.getPositionAsString(), 30, 200);
        g.drawString(game.mouse.getNotchesAsString(), 200, 200);
        g.drawString(game.mouse.buttonState(MouseEvent.BUTTON2 - 1), 370, 200);

        g.drawString(game.mouse.buttonState(MouseEvent.BUTTON1 - 1), 30, 225);
        g.drawString(game.mouse.buttonState(MouseEvent.BUTTON3 - 1), 250, 225);
    }
}
