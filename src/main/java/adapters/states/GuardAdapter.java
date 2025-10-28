package adapters.states;

import org.omg.sysml.lang.sysml.Expression;
import org.omg.sysml.lang.sysml.OperatorExpression;

import interfaces.states.IGuard;

public class GuardAdapter implements IGuard {
    private final Expression guardExpression;

    public GuardAdapter(Expression guardExpression) {
        this.guardExpression = guardExpression;
    }

    /*
    @Override
    public String getCondition() {
        if (guardExpression instanceof OperatorExpression) {
            OperatorExpression opExpr = (OperatorExpression) guardExpression;
            return opExpr.getOperator();
        }
        return "Unknown Condition";
    }
    
    @Override
    public String toString() {
        return "Guard Condition: " + getCondition();
    }
    */

    @Override
    public Expression getExpression() {
        return guardExpression;
    }

}
