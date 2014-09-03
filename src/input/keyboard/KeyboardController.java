package input.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Shorty on 8/8/2014.
 */
public abstract class KeyboardController implements KeyListener {
    protected final int SINGLE_TAP_THRESHHOLD = 5;
    protected final int DOUBLE_TAP_THRESHHOLD = 10;

    protected boolean[] keys;
    protected int[] polled;
    protected int[] delay;

    public KeyboardController(){
        keys = new boolean[256];
        polled = new int[256];
        delay = new int[256];
    }

    public abstract boolean keyDown(int keyCode);
    public abstract boolean keyDownOnce(int keyCode);
    public abstract boolean keyHeldDown (int keyCode);
    public abstract boolean keyDoubleTap(int keyCode);

    public abstract void poll();


    public synchronized String keyState(int keyCode){
        if(keyDoubleTap(keyCode)){
            return keyCodeToString(keyCode) + ": DOUBLE TAPPED";
        }
        else if(keyDownOnce(keyCode) || keyDown(keyCode) && polled[keyCode] < SINGLE_TAP_THRESHHOLD) {
            return keyCodeToString(keyCode) + ": PRESSED";
        }
        else if(keyHeldDown(keyCode)) {
            return keyCodeToString(keyCode) + ": DOWN " + (polled[keyCode]/60 + 1);
        } else {
            return keyCodeToString(keyCode) + ":";
        }
    }
    public synchronized String keyCodeToString(int keyCode){
        switch (keyCode) {
            case 37:
                return "LEFT";
            case 38:
                return "UP";
            case 39:
                return "RIGHT";
            case 40:
                return "DOWN";
            case 49:case 50:case 51:case 52:case 53:case 54:case 55:case 56:case 57:case 58:
                return Character.toString((char)(keyCode));
            case 65:case 66:case 67:case 68:case 69:case 70:case 71:case 72:case 73:case 74:
            case 75:case 76:case 77:case 78:case 79:case 80:case 81:case 82:case 83:case 84:
            case 85:case 86:case 87:case 88:case 89:case 90:case 91:
                return Character.toString((char)(keyCode));
            default:
                return "No String Value For Code " + keyCode;
        }
    }
}
