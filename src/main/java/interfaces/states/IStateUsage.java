package interfaces.states;
import org.omg.sysml.lang.sysml.ActionUsage;

import interfaces.actions.IAcceptAction;

public interface IStateUsage {
    String getName();
    
    public ActionUsage getEntry();
    public ActionUsage getDoActivity();
    public ActionUsage getExit();
    
    //TO-DO isParallel
    
    public IStateUsage[] getSubstates();

	IAcceptAction[] getAcceptActions();

	ITransition[] getTransitions();
   
}