package input.mouse;

import game.GameFramework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Shorty on 8/8/2014.
 */
public abstract class MouseController implements MouseListener, MouseMotionListener, MouseWheelListener {
    protected final int SINGLE_CLICK_THRESHHOLD = 5;
    protected final int DOUBLE_CLICK_THRESHHOLD = 10;
    protected final int BUTTON_COUNT = 3;

    protected Point mousePos, currentPos;
    protected boolean[] buttons;

    protected int[] polled;
    protected int[] delay;

    protected int notches;
    protected int polledNotches;

    public int x, y;

    protected Robot robot;
    protected GameFramework game;

    public MouseController(GameFramework game){
        this.game = game;
        initializeRobot();
        mousePos = new Point(0, 0);
        currentPos = new Point(0, 0);
        buttons = new boolean[BUTTON_COUNT];
        polled = new int[BUTTON_COUNT];
        delay = new int[BUTTON_COUNT];
    }

    public void initializeRobot(){
        try{
            this.robot = new Robot();
        } catch (AWTException e){
            e.printStackTrace();
        }
    }

    public abstract Point getPosition();
    public abstract int getNotches();

    public abstract boolean buttonDown(int button);
    public abstract boolean buttonDownOnce(int button);
    public abstract boolean buttonDoubleClick(int button);
    public abstract void poll();

    public synchronized void mouseClicked(MouseEvent e) {

    }
    public synchronized void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }
    public synchronized void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }
    public synchronized void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public synchronized String buttonState(int button){
        if(buttonDoubleClick(button)){
            return buttonToString(button) + ": DOUBLE CLICKED";
        }
        else if(buttonDownOnce(button)) {
            return buttonToString(button) + ": CLICKED";
        }
        else if(buttons[button]) {
            return buttonToString(button) + ": DOWN " + (polled[button]/60 + 1);
        } else {
            return buttonToString(button) + ":";
        }
    }
    public synchronized String buttonToString(int button){
        switch (button) {
            case 0:
                return "LEFT MOUSE";
            case 1:
                return "MOUSE WHEEL";
            case 2:
                return "RIGHT MOUSE";
            default:
                return "No String Value For Code " + button;
        }
    }
    public synchronized String getPositionAsString(){
        return "X: " + getPosition().getX() + " Y: " + getPosition().getY();
    }
    public synchronized String getNotchesAsString(){
        return "WHEEL NOTCHES: " + getNotches();
    }
}
