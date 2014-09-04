package render;

/**
 * Game Framework
 * Created by MyNameReallySux on 8/20/2014.
 * Copyright 2014©
 */

public class DisplayChangeNotSupportedException extends Exception {
    String msg = "Display Change is not supported, reverting to initial display mode.";

    @Override
    public String getMessage(){
        return msg;
    }
}
