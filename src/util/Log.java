package util;

import game.GameFramework;

/**
 * Created by Shorty on 8/17/2014.
 */
public interface Log {
    public default void log(String msg){
        if(GameFramework.DEBUG){
            System.out.println(msg);
        }
    }

    public default void log(String log, String msg){
        if(GameFramework.DEBUG){
            System.out.println(log + " >> " + msg);
        }
    }

    public default void err(String log, String msg){
        if(GameFramework.DEBUG){
            System.err.println("ERROR: " + log + " >> " + msg);
        }
    }

    public default void err(String log, Exception e){
        if(GameFramework.DEBUG){
            System.err.println("ERROR: " + log + " =======================");
            e.printStackTrace();
        }
    }

    public static void print(String msg){
        if(GameFramework.DEBUG){
            System.out.println(msg);
        }
    }

    public static void error(String msg){
        if(GameFramework.DEBUG){
            System.err.println("ERROR: " + "Main" + " >> " + msg);
        }
    }

    public static void error(Exception e){
        if(GameFramework.DEBUG){
            System.err.println("ERROR: " + "Main" + " =======================");
            e.printStackTrace();
        }
    }
}
