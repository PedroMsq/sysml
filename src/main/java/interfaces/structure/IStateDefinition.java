package interfaces.structure;

import interfaces.states.IStateUsage;
import interfaces.states.ITransition;
import interfaces.utils.INamedElement;

public interface IStateDefinition extends INamedElement {
    
	public ITransition[] getTransitions();
    
	public IStateUsage[] getStates();
    
}
