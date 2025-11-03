package interfaces.states;

import org.omg.sysml.lang.sysml.Expression;

import interfaces.expressions.IExpression;

public interface IGuard {

	public IExpression getExpression();
    
}