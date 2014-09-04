package game;

import input.keyboard.KeyboardController;
import input.mouse.MouseController;
import modules.DuplicateModuleException;
import modules.GameModule;
import modules.ModuleList;
import render.Display;
import render.GameWindow;
import render.Screen;
import util.Clock;
import util.FrameRate;
import util.Log;
import util.physics.Matrix3x3f;

import java.awt.*;

/**
 * Game Framework
 * Created by MyNameReallySux on 8/20/2014.
 * Copyright 2014©
 */

public abstract class GameFramework extends Canvas implements Runnable, Log {
    public static final String TITLE = "Game Title";
    public static final String VERSION = "0.0.0.00";
    public static final String LOG = "GameFramework";
    public static final boolean DEBUG = true;

    protected ModuleList modules;

    protected volatile boolean running;
    protected volatile boolean resizing;

    protected static GameFramework game;

    protected Clock clock;
    protected FrameRate frameRate;
    protected Thread gameThread;
    protected Display display;
    protected Screen screen;
    protected GameWindow window;
    public volatile KeyboardController keyboard;
    public volatile MouseController mouse;

    /**
     * Class Constructor
     */
    public GameFramework(){
        super();
        game = this;
        this.display = new Display(game);
        this.screen = new Screen(game, 4f, 3f);
        this.window = new GameWindow(game);
        this.gameThread = new Thread(game);
        this.modules = new ModuleList();
        this.frameRate = new FrameRate();
        this.clock = new Clock();
        setIgnoreRepaint(true);
        setBackground(Color.BLACK);
    }

    /**
     * This method initializes the {@link render.Display}, which is required
     * to be initialized before the actual game components and
     * modules are initialized. This can be called immediately
     * after the GameFramework object is created if desired.
     * <p/>
     * This method calls the "run" method, by using the "start"
     * method of {@link java.lang.Thread}
     */
    public void start(){
        gameThread.start();
    }

    /**
     * Main game loop. As long as the boolean value "running"
     * is set to true,
     */
    @Override
    public void run(){
        initialize();
        while(running){
            clock.clockTick();
            gameLoop(clock.getDelta());
            clock.setLastTime();
        }
    }

    public void gameLoop(double delta){
        processModuleInput();
        input();
        processModuleUpdates(delta);
        update(delta);
        draw();
        sleep(clock.getSleep());
    }

    public Clock getClock(){
        return clock;
    }
    public Screen getScreen(){
        return screen;
    }
    public Matrix3x3f getViewport(){
        return getScreen().getViewport();
    }
    public Display getDisplay(){
        return display;
    }
    public GameWindow getWindow(){
        return window;
    }

    public static Clock Clock(){
        return game.getClock();
    }
    public static Screen Screen(){
        return  game.getScreen();
    }
    public static Matrix3x3f Viewport(){
        return  game.getScreen().getViewport();
    }
    public static Display Display(){
        return  game.getDisplay();
    }
    public static GameWindow Window(){
        return game.getWindow();
    }

    public boolean isRunning(){
        return running;
    }
    public boolean isResizing(){
        return resizing;
    }

    public void setResizing(boolean resizing) { this.resizing = resizing;
    }

    public abstract void addInputListeners();
    public abstract void addModules();

    protected void initialize(){
        log(LOG, "Initializing Game Loop");

        addInputListeners();
        addModules();

        if(game.isRunning() && game.isVisible()){
            for(GameModule module: modules.values()){
                if(!module.isInitialized() && module.initialize())
                    module.setInitialized(true);
            }
        }

        frameRate.initialize();
        clock.initialize();
        running = true;
    }

    public KeyboardController getKeyboard(){
        return keyboard;
    }

    public MouseController getMouse(){
        return mouse;
    }

    public void setKeyboardController(KeyboardController keyboard) {
        removeKeyListener(this.keyboard);
        addKeyListener(keyboard);
        this.keyboard = keyboard;
    }
    public void setMouseController(MouseController mouse) {
        removeMouseListener(this.mouse);
        removeMouseMotionListener(this.mouse);
        removeMouseWheelListener(this.mouse);

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);

        this.mouse = mouse;
    }

    public GameModule addMod(GameModule module){
        try{
            modules.addMod(module);
            log(LOG, ":: Adding Module: " + module.getKey());
            return module;
        } catch (DuplicateModuleException e){
            e.printStackTrace();
            return modules.get(module.getKey());
        }
    }

    protected void processModuleInput(){
        keyboard.poll();
        mouse.poll();
        for(GameModule module: modules.values()){
            if(module.isInitialized()) module.input();
        }
    }
    protected void processModuleUpdates(double delta){
        for(GameModule module: modules.values()){
            if(module.isInitialized()) module.update(delta);
        }
    }
    protected void processModuleGraphics(Graphics g){
        for(GameModule module: modules.values()){
            if(module.isInitialized()) module.render(g);
            reset(g);
        }
    }

    protected void draw(){
        if(window.isVisible() && !isResizing()){
            do{
                do{
                    Graphics g = null;
                    try {
                        g = getBufferStrategy().getDrawGraphics();
                        g.clearRect(0, 0, getWidth(), getHeight());
                        getScreen().resetViewport();
                        processModuleGraphics(g);
                        render(g);
                    } catch (IllegalStateException e){
                        createBufferStrategy(2);
                    } finally {
                        if (g != null) g.dispose();
                    }
                } while (getBufferStrategy().contentsRestored());
                getBufferStrategy().show();
            } while (getBufferStrategy().contentsLost());
        }
    }

    protected abstract void input();
    protected abstract void update(double delta);
    protected abstract void render(Graphics g);

    protected void reset(Graphics g){
        g.translate(0, 0);
        g.setFont(new Font("New Courier", Font.PLAIN, 12));
        g.setColor(Color.white);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
    }
    public void sleep(long duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onShutDown(){
        try {
            running = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
