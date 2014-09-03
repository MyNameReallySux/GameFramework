package input.mouse;

import game.GameFramework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Shorty on 8/8/2014.
 */
public class RelativeMouseInput extends AbsoluteMouseInput {
    protected Point relativePos;
    public int dx, dy;

    public RelativeMouseInput(GameFramework game){
        super(game);
        this.relativePos = new Point(0, 0);
    }

    @Override
    public Point getPosition(){
        relativePos.x += mousePos.x;
        relativePos.y += mousePos.y;
        x = relativePos.x;
        y = relativePos.y;
        return relativePos;
    }

    @Override
    public synchronized void poll(){
        mousePos = new Point(dx, dy);
        dx = dy = 0;
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

    @Override
    public synchronized void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        Point center = game.getWindow().getCenter();
        dx += (currentPos.x - center.x);
        dy += (currentPos.y - center.y);
        centerMouse();
    }

    public void centerMouse(){
        if(robot != null && game.isShowing()){
            Point center = game.getWindow().getCenter();
            SwingUtilities.convertPointToScreen(center, game);
            robot.mouseMove(center.x, center.y);
        }
    }
}
