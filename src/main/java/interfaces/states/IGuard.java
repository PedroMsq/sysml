package interfaces.states;

import org.omg.sysml.lang.sysml.Expression;

public interface IGuard {
    String getCondition();
    Expression getExpression();
}