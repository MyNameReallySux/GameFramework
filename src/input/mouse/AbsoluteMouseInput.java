package input.mouse;

import game.GameFramework;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Created by Shorty on 8/8/2014.
 */
public class AbsoluteMouseInput extends MouseController {
    public AbsoluteMouseInput(GameFramework game){
        super(game);
    }

    public Point getPosition(){
        x = mousePos.x;
        y = mousePos.y;
        return mousePos;
    }

    public int getNotches(){
        return polledNotches;
    }

    public synchronized boolean buttonDown(int button){
        return polled[button] > 0;
    }
    public synchronized boolean buttonDownOnce(int button){
        return polled[button] <= SINGLE_CLICK_THRESHHOLD && polled[button] > 0;
    }
    public synchronized boolean buttonDoubleClick(int button){
        return delay[button] >= 0 && buttonDown(button);

    }

    public void poll(){
        mousePos = new Point(currentPos);
        polledNotches = notches;
        notches = 0;

        for(int i = 0; i < buttons.length; i++){
            if (delay[i] >= 0) delay[i]--;
            if(buttons[i])
                polled[i]++;
            else
                polled[i] = 0;
        }
    }

    public synchronized void mousePressed(MouseEvent e) {
        int button = e.getButton() - 1;
        if(button >= 0 && button < buttons.length)
            buttons[button] = true;
    }
    public synchronized void mouseReleased(MouseEvent e) {
        int button = e.getButton() - 1;
        if (button >= 0 && button < buttons.length)
            buttons[button] = false;
        if (polled[button] <= SINGLE_CLICK_THRESHHOLD && polled[button] > 0)
            delay[button] = DOUBLE_CLICK_THRESHHOLD;
    }
    public synchronized void mouseMoved(MouseEvent e) {
        currentPos = e.getPoint();
    }
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {
        notches += e.getWheelRotation();
    }
}
