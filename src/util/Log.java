package util;

import game.Game;

/**
 * Created by Shorty on 8/17/2014.
 */
public interface Log {
    public static void print(String msg){
        if(Game.DEBUG){
            System.out.println(msg);
        }
    }

    public static void error(String msg){
        if(Game.DEBUG){
            System.err.println("ERROR: " + "Main" + " >> " + msg);
        }
    }

    public static void error(Exception e){
        if(Game.DEBUG){
            System.err.println("ERROR: " + "Main" + " =======================");
            e.printStackTrace();
        }
    }

    public default void log(String msg){
        if(Game.DEBUG){
            System.out.println(msg);
        }
    }

    public default void log(String log, String msg){
        if(Game.DEBUG){
            System.out.println(log + " >> " + msg);
        }
    }

    public default void err(String log, String msg){
        if(Game.DEBUG){
            System.err.println("ERROR: " + log + " >> " + msg);
        }
    }

    public default void err(String log, Exception e){
        if(Game.DEBUG){
            System.err.println("ERROR: " + log + " =======================");
            e.printStackTrace();
        }
    }
}
