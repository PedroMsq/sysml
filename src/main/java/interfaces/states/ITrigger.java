package interfaces.states;

import org.omg.sysml.lang.sysml.Expression;
//import org.omg.sysml.lang.sysml.TriggerInvocationExpression;


public interface ITrigger {
	String getTriggerType(); //update later
    Expression getTriggerArgument();
	/** helper que retorna só o nome (p.ex. "s") */
	String getArgumentName();
    
}