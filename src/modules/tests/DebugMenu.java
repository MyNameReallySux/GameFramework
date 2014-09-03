package modules.tests;

import game.GameFramework;
import render.Display;
import util.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Shorty on 8/17/2014.
 */
public class DebugMenu extends JMenuBar implements Log {
    public static final String LOG = "DebugMenu";

    GameFramework game;
    JMenu file, display, resolution;
    JMenuItem reset, exit, windowed, fullscreen;
    ArrayList<JMenuItem> resList;
    ArrayList<DisplayMode> modeList;

    public DebugMenu(GameFramework game){
        super();
        this.game = game;

        initialize();
        setMenuHotkeys();
        addMenuComponents();
    }

    public void initialize(){
        file = new JMenu("File");
        display = new JMenu("Display");
        resolution = new JMenu("Resolutions");

        reset = new JMenuItem("Reset");
        exit = new JMenuItem("Exit");
        windowed = new JMenuItem("Windowed Mode");
        fullscreen = new JMenuItem("FullScreen Mode");

        resList = new ArrayList<>();
        modeList = new ArrayList<>();

        Display.DisplayModeWrapper[] displayModeList = game.getDisplay().listDisplayModes();
        for(Display.DisplayModeWrapper displayMode : displayModeList){
            JMenuItem resMenuItem = new JMenuItem(displayMode.toString());
            resList.add(resMenuItem);
            modeList.add(displayMode.dm);
        }
    }

    public void setMenuHotkeys(){
        file.setMnemonic(KeyEvent.VK_F);
        display.setMnemonic(KeyEvent.VK_D);
    }

    public void addMenuComponents(){
        add(file);
        add(display);

        file.add(reset);
        file.add(exit);
        display.add(resolution);


        for(int i = 0; i < resList.size(); i++){
            final DisplayMode mode = modeList.get(i);
            final JMenuItem item = resList.get(i);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.getDisplay().changeResolution(mode);
                }
            });
            resolution.add(item);
        }
    }
}
