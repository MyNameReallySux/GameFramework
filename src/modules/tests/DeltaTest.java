package modules.tests;

import game.GameFramework;
import modules.GameModule;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Shorty on 8/25/2014.
 */
public class DeltaTest extends GameModule {
    private float angle, step;
    private long sleep;
    public DeltaTest(GameFramework game) {
        super(game, "DeltaTest");
    }

    @Override
    public boolean initialize() {
        angle = 0.0f;
        step = (float)Math.PI / 2f;
        sleep = game.getClock().getSleep();
        return true;
    }

    @Override
    public void input() {
        if(game.getKeyboard().keyDownOnce(KeyEvent.VK_UP))      sleep += 10;
        if(game.getKeyboard().keyDownOnce(KeyEvent.VK_DOWN))    sleep -= 10;
        if(sleep > 1000)                                        sleep = 1000;
        if(sleep < 0)                                           sleep = 0;
    }

    @Override
    public void update(double delta) {
        angle += (step * delta);
        if(angle > 2 * Math.PI) angle -= 2 * Math.PI;
        game.getClock().setSleep(sleep);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);

        g.drawString("Up arrow increases sleep time", 20, 50);
        g.drawString("Down arrow decreases sleep time", 20, 65);
        g.drawString("sleepTime (ms): " + sleep, 20, 80);


        int x = game.getWidth() / 4;
        int y = game.getHeight() / 4;
        int w = game.getWidth() / 2;
        int h = game.getHeight() / 2;
        g.drawOval(x, y, w, h);

        float rw = w / 2;
        float rh = h / 2;
        int rx = (int)(rw * Math.cos(angle));
        int ry = (int)(rh * Math.sin(angle));
        int cx = (int)(rx + w);
        int cy = (int)(ry + h);

        g.drawLine(w, h, cx, cy);

    }
}
