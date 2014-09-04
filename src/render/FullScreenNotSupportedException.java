package render;

/**
 * Game Framework
 * Created by MyNameReallySux on 8/20/2014.
 * Copyright 2014©
 */

public class FullScreenNotSupportedException extends Exception {
    String msg = "Full Screen Mode is not supported, reverting to windowed mode.";

    @Override
    public String getMessage(){
        return msg;
    }
}
