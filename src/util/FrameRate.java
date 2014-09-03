package util;

/**
 * Created by Shorty on 8/2/2014.
 */
public class FrameRate {
    private String frameRate;
    private long lastTime, delta;
    private int frameCount;

    public void initialize(){
        /*
        Set's the initial parameters for the FrameRate class. sets "lastTime" to the system current time, in order
        to compare this time to the next frame and to calculate the "delta" value.
         */
        lastTime = System.currentTimeMillis();
        frameRate = "FPS: 0";
    }

    public void calculate(){
        /*
        Calculates the frame rate. Compares the current time "current" with the previous time "lastTime" and then sets
        "lastTime" to "current". Each frame, the "lastTime" value will be the last time the "calculate()" method was
        executed.
         */
        long current = System.currentTimeMillis();
        delta += current - lastTime;
        lastTime = current;
        frameCount++;
        if(delta > 1000){
            delta -= 1000;
            frameRate = String.format("FPS %s", frameCount);
            frameCount = 0;
        }
    }

    public String getFrameRate(){
        return frameRate;
    }
}
