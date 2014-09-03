package modules;

/**
 * Created by Shorty on 8/11/2014.
 */
public class DuplicateModuleException extends RuntimeException {
    GameModule module;

    public DuplicateModuleException(GameModule module){
        this.module = module;
    }

    public String getMessage(){
        return module.getKey() + " already added to ModuleList. The original GameModule reference is being returned";
    }
}
