package render;

/**
 * Created by Shorty on 8/20/2014.
 */
public class DisplayChangeNotSupportedException extends Exception {
    String msg = "Display Change is not supported, reverting to initial display mode.";

    @Override
    public String getMessage(){
        return msg;
    }
}
