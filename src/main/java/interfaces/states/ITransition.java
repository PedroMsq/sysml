package interfaces.states;

//import interfaces.actions.IActionUsage;

public interface ITransition {
    String getSourceName();
    String getTargetName();
    IGuard   getGuard();
    ITrigger getTrigger();
    IEffect  getEffect();
}