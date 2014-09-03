package modules;

import java.util.*;

/**
 * Created by Shorty on 8/11/2014.
 */
public class ModuleList extends HashMap<String, GameModule> {
    public void addMod(GameModule module){
        if(containsKey(module.getKey()) || containsValue(module))
            throw new DuplicateModuleException(module);
        else {
            put(module.getKey(), module);
        }
    }
}
