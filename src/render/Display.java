package render;

import game.GameFramework;
import util.Log;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Shorty on 8/20/2014.
 */
public class Display implements Log {
    private static final DisplayMode[] DISPLAY_MODES = new DisplayMode[]{
            new DisplayMode(640, 480, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(800, 600, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1280, 800, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1280, 1024, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1440, 900, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1600, 900, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1680, 1050, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
    };

    private static final String LOG = "Display";

    protected GameFramework game;
    protected GraphicsDevice graphicsDevice;
    protected DisplayMode startingDisplayMode, selectedDisplayMode;
    private volatile boolean fullScreen;

    public Display(GameFramework game){
        this.game = game;
        initialize();
    }

    protected void initialize(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        startingDisplayMode = graphicsDevice.getDisplayMode();
        selectedDisplayMode = getInitialDisplayMode();
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    }

    public Dimension getSize(){
        if(selectedDisplayMode == null) return getSize(startingDisplayMode);
        else return getSize(selectedDisplayMode);
    }

    public Dimension getSize(DisplayMode displayMode){
        return new Dimension(displayMode.getWidth(), displayMode.getHeight());
    }

    public DisplayMode getInitialDisplayMode(){
        DisplayModeWrapper[] modes = listDisplayModes();
        for(DisplayMode mode: DISPLAY_MODES){
            for(DisplayModeWrapper supported: modes){
                DisplayModeWrapper wrap = new DisplayModeWrapper(mode);
                if(supported.equals(wrap)) return mode;
            }
        }
        return null;
    }
    public DisplayModeWrapper getCurrentDisplayMode(){
        return new DisplayModeWrapper(selectedDisplayMode);
    }

    public DisplayModeWrapper[] listDisplayModes(){
        ArrayList<DisplayModeWrapper> list = new ArrayList<>();
        for(DisplayMode mode : graphicsDevice.getDisplayModes()){
            for(DisplayMode mode2 : DISPLAY_MODES){
                DisplayModeWrapper supported = new DisplayModeWrapper(mode2);
                if(supported.equals(mode)){
                    DisplayModeWrapper wrap = new DisplayModeWrapper(mode);
                    if(!list.contains(wrap)){
                        list.add(wrap);
                    }
                }
            }
        }
        return list.toArray(new DisplayModeWrapper[list.size()]);
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void toggleFullScreen(){
        if(isFullScreen()){
            exitFullScreen();
            game.getWindow().initialize();
        } else {
            enterFullScreen();
        }
    }
    public void enterFullScreen(){
        try{
            if(!graphicsDevice.isFullScreenSupported()) throw new FullScreenNotSupportedException();
            game.getWindow().dispose(true);
            graphicsDevice.setFullScreenWindow(game.getWindow());
            try{
                if(!graphicsDevice.isDisplayChangeSupported()) throw new DisplayChangeNotSupportedException();
                graphicsDevice.setDisplayMode(selectedDisplayMode);
            } catch (DisplayChangeNotSupportedException e){
                err(LOG, e.getMessage());
            }
            game.requestFocus();
            fullScreen = true;
        } catch (FullScreenNotSupportedException e){
            err(LOG, e.getMessage());
        }
    }
    public void exitFullScreen(){
        try {
            if(!graphicsDevice.isDisplayChangeSupported()) throw new DisplayChangeNotSupportedException();
            graphicsDevice.setDisplayMode(startingDisplayMode);
        } catch (DisplayChangeNotSupportedException e){
            err(LOG, e.getMessage());
        }

        graphicsDevice.setFullScreenWindow(null);
        game.getWindow().dispose(false);
        fullScreen = false;
    }

    public void changeResolution(DisplayMode newResolution){
        if(newResolution == null) return;
        if(isFullScreen()){
            exitFullScreen();
            selectedDisplayMode = newResolution;
            enterFullScreen();
        } else {
            selectedDisplayMode = newResolution;
            if(game.getWindow() != null){
                game.getWindow().setWindowSize();
            } else {
                log(LOG, "Window is null");
            }
        }

    }

    public class DisplayModeWrapper {
        public DisplayMode dm;
        public DisplayModeWrapper(DisplayMode dm){
            this.dm = dm;
        }

        public Dimension getSize(){
            return new Dimension(dm.getWidth(), dm.getHeight());
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof DisplayMode){
                DisplayModeWrapper other = new DisplayModeWrapper((DisplayMode)obj);
                return !(dm.getWidth() != other.dm.getWidth() || dm.getHeight() != other.dm.getHeight());
            }
            if(obj instanceof DisplayModeWrapper){
                DisplayModeWrapper other = (DisplayModeWrapper)obj;
                return !(dm.getWidth() != other.dm.getWidth() || dm.getHeight() != other.dm.getHeight());
            } else {
                return false;
            }
        }

        public String toString(){
            return "" + dm.getWidth() + "X" + dm.getHeight()
                    + "  " + dm.getBitDepth() + " bit  "
                    + dm.getRefreshRate() + " Hz";
        }
    }

}