package game;

import input.keyboard.KeyboardInput;
import input.mouse.AbsoluteMouseInput;
import modules.tests.CannonTest;
import util.ShutDownListener;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Game Framework
 * Created by MyNameReallySux on 8/20/2014.
 * Copyright 2014©
 */

public class Game extends GameFramework {
    public static final String TITLE = "Moon Wizard";
    public static final String VERSION = "0.0.0.01";
    public static final String LOG = "Game";

    @Override
    public void addInputListeners(){
        setKeyboardController(new KeyboardInput());
        setMouseController(new AbsoluteMouseInput(this));
        addKeyListener(new ShutDownListener(this));
    }

    @Override
    public void addModules(){
        //addMod(new TransformationTest(this));
        //addMod(new InputConsole(this));
        //addMod(new DebuggingModule(this));
        //addMod(new RelativeMouseTest(this));
        //addMod(new PolarCoordinateTest(this));
        //addMod(new DrawingTest(this));
        //addMod(new MatrixTransformationTest(this));
        //addMod(new DeltaTest(this));
        //addMod(new ScreenMappingTest(this));
        addMod(new CannonTest(this));
    }

    @Override
    protected void initialize(){
        log(TITLE + " Version " + VERSION);
        super.initialize();
        screen.setWorldSize(2f, 2f);
    }

    @Override
    protected void input(double delta) {
        if (keyboard.keyDownOnce(KeyEvent.VK_F)){
            game.getDisplay().toggleFullScreen();
        }
    }

    @Override
    protected void update(double delta) {
        window.setTitle(GameFramework.TITLE + " v:" + GameFramework.VERSION + " | " + frameRate.getFrameRate());
    }

    @Override
    protected void render(Graphics g) {
        debugRender(g);
    }

    private void debugRender(Graphics g){
        frameRate.calculate();
        g.setColor(Color.GREEN);
        g.drawString(frameRate.getFrameRate(), 30, 30);

        g.setColor(Color.WHITE);
        g.drawString("Press 'F' to toggle fullscreen mode", 30, getHeight() - 40);
        g.drawString("Press 'ESC' to exit...", getWidth() - 150, getHeight() - 40);
    }
}
