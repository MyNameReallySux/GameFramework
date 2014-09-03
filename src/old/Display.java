package old;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Shorty on 8/2/2014.
 */
public class Display extends JFrame {
    private JComboBox displayModes;
    private GraphicsDevice graphicsDevice;
    private DisplayMode currentDisplayMode;
    private DisplayMode selectedDisplayMode;
    private Game game;
    public boolean fullScreen;

    public Display(Game game){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        currentDisplayMode = graphicsDevice.getDisplayMode();
        this.game = game;
    }

    private JPanel getMainPanel(){
        JPanel p = new JPanel();
        displayModes = new JComboBox(listDisplayModes());
        p.add(displayModes);

        JButton enterButton = new JButton("Run In Full Screen");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDisplayMode = getSelectedMode();
                getMainWindow();
                setFullScreen(true);
                dispose();
                game.start();
            }
        });
        p.add(enterButton);

        JButton exitButton = new JButton("Run In Windowed");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDisplayMode = getSelectedMode();
                getMainWindow();
                dispose();
                game.start();
            }
        });
        p.add(exitButton);
        return p;
    }

    private DisplayModeWrapper[] listDisplayModes(){
        ArrayList<DisplayModeWrapper> list = new ArrayList<DisplayModeWrapper>();
        for(DisplayMode mode : graphicsDevice.getDisplayModes()){
            DisplayModeWrapper wrap = new DisplayModeWrapper(mode);
            if(!list.contains(wrap)){
                list.add(wrap);
            }
        }
        return list.toArray(new DisplayModeWrapper[list.size()]);
    }

    public void createAndShowGUI(){
        Container canvas = getContentPane();
        canvas.add(getMainPanel());
        canvas.setIgnoreRepaint(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Display Modes");
        pack();
        setVisible(true);
    }

    public void setFullScreen(boolean fullScreen){
        if(this.fullScreen != fullScreen){
            this.fullScreen = fullScreen;
            if(fullScreen){
                onEnterFullScreen();
            } else {
                onExitFullScreen();
            }
        }

    }

    protected void getMainWindow(){
        DisplayMode dm = selectedDisplayMode;
        Dimension dimension = new Dimension(dm.getWidth(), dm.getHeight());

        game.initializeGUI(dimension);
        game.setVisible(true);

        game.createBufferStrategy();
        dispose();
    }

    protected void onEnterFullScreen(){
        if(graphicsDevice.isFullScreenSupported()){
            currentDisplayMode = graphicsDevice.getDisplayMode();
            game.setVisible(false);
            game.dispose();
            game.setUndecorated(true);
            graphicsDevice.setFullScreenWindow(game);
            graphicsDevice.setDisplayMode(selectedDisplayMode);
            game.setVisible(true);
            fullScreen = true;
        } else {
            System.err.println("Error: FullScreen mode not supported.");
            fullScreen = false;
        }
    }

    protected void onExitFullScreen(){
        graphicsDevice.setDisplayMode(currentDisplayMode);
        game.setVisible(false);
        game.dispose();
        game.setUndecorated(false);
        graphicsDevice.setFullScreenWindow(null);
        Dimension dimension = new Dimension(selectedDisplayMode.getWidth(), selectedDisplayMode.getHeight());
        game.setSize(dimension);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        fullScreen = false;
    }

    protected DisplayMode getSelectedMode(){
        DisplayModeWrapper wrapper = (DisplayModeWrapper)displayModes.getSelectedItem();
        DisplayMode dm = wrapper.dm;
        int width = dm.getWidth();
        int height = dm.getHeight();
        int bit = dm.getBitDepth();
        int refresh = DisplayMode.REFRESH_RATE_UNKNOWN;
        return new DisplayMode(width, height, bit, refresh);
    }

    public class DisplayModeWrapper {
        private DisplayMode dm;

        public DisplayModeWrapper(DisplayMode dm){
            this.dm = dm;
        }

        public boolean equals(Object obj) {
            if(!(obj instanceof DisplayModeWrapper || obj instanceof DisplayMode)){
                return false;
            } else {
                DisplayModeWrapper other = (DisplayModeWrapper)obj;
                return !(dm.getWidth() != other.dm.getWidth() || dm.getHeight() != other.dm.getHeight());
            }
        }

        public String toString(){
            return "" + dm.getWidth() + "X" + dm.getHeight()
                    + "    " + dm.getBitDepth() + " bit    "
                    + dm.getRefreshRate() + " Hz";
        }
    }
}
