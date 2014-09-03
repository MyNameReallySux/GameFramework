package util;

/**
 * Created by Shorty on 8/25/2014.
 */
public class Clock {
    final double NANO_SECONDS_PER_SECOND = 1.0E9;

    long curTime, lastTime, sleep;
    double delta, nanoSecondsPerFrame;

    public Clock(){}
    public void initialize(){
        curTime = System.nanoTime();
        setSleep(10);
        setLastTime();
    }

    public double getDelta(){
        return delta;
    }

    public long getSleep(){
        return sleep;
    }

    public void setSleep(long sleep){
        this.sleep = sleep;
    }

    public void clockTick(){
        curTime = System.nanoTime();
        nanoSecondsPerFrame = curTime - lastTime;
        delta = nanoSecondsPerFrame / NANO_SECONDS_PER_SECOND;
    }

    public void setLastTime(){
        lastTime = curTime;
    }
}
