package interfaces.states;
import org.omg.sysml.lang.sysml.ActionUsage;

import interfaces.actions.IActionUsage;

public interface IStateUsage {
	public String getName();
    
    public ActionUsage getEntry();
    public ActionUsage getDoActivity();
    public ActionUsage getExit();
    
    //TO-DO isParallel
    
    public IStateUsage[] getSubstates();

	// IActionUsage[] getAcceptActions();

    public ITransition[] getTransitions();
   
}