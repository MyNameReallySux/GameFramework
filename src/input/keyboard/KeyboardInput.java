package input.keyboard;

import java.awt.event.KeyEvent;

/**
 * Created by Shorty on 8/8/2014.
 */
public class KeyboardInput extends KeyboardController {
    public KeyboardInput(){
        super();
    }

    public synchronized boolean keyDown(int keyCode){
        return polled[keyCode] > 0;
    }
    public synchronized boolean keyDownOnce(int keyCode){
        return polled[keyCode] == 1;
    }
    public synchronized boolean keyHeldDown (int keyCode){
        return polled[keyCode] > SINGLE_TAP_THRESHHOLD;
    }
    public synchronized boolean keyDoubleTap(int keyCode){
        return delay[keyCode] >= 0 && keyDown(keyCode);
    }

    public synchronized void poll(){
        for(int i = 0; i < keys.length; i++){
            if (delay[i] >= 0) delay[i]--;
            if(keys[i])
                polled[i]++;
            else
                polled[i] = 0;
        }
    }

    @Override
    public synchronized void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < keys.length);
        keys[keyCode] = true;
    }

    @Override
    public synchronized void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < keys.length);
        keys[keyCode] = false;
        if(polled[keyCode] <= SINGLE_TAP_THRESHHOLD && polled[keyCode] > 0)
            delay[keyCode] = DOUBLE_TAP_THRESHHOLD;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
