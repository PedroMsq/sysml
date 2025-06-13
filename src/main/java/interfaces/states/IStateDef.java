package interfaces.states;

public interface IStateDef {
    String getName();
    ITransition[] getTransitions();
    IStateUsage[] getStates();
}
