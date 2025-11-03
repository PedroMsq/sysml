package interfaces.states;

public interface IStateDef {
	public String getName();
	
    public ITransition[] getTransitions();
    
    public IStateUsage[] getStates();
    
}
