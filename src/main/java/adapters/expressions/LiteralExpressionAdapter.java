package adapters.expressions;

import org.omg.sysml.lang.sysml.Expression;
import org.omg.sysml.lang.sysml.LiteralBoolean;
import org.omg.sysml.lang.sysml.LiteralInfinity;
import org.omg.sysml.lang.sysml.LiteralInteger;
import org.omg.sysml.lang.sysml.LiteralRational;
import org.omg.sysml.lang.sysml.LiteralString;

import interfaces.expressions.ILiteralExpression;

//Adapter para LiteralExpressions (booleano, inteiro, real, string, infinito)
public class LiteralExpressionAdapter extends ExpressionAdapter implements ILiteralExpression {

    public LiteralExpressionAdapter(Expression expr) {
        super(expr);
    }

    @Override
    public String getLiteralType() {
        return expr.getClass().getSimpleName();
    }

    @Override
    public Object getValue() {
        if (expr instanceof LiteralString ls) return ls.getValue();
        if (expr instanceof LiteralInteger li) return li.getValue();
        if (expr instanceof LiteralBoolean lb) return lb.isValue();
        if (expr instanceof LiteralRational lr) return lr.getValue();
        if (expr instanceof LiteralInfinity inf) return "Infinity";
        return null;
    }

    @Override
    public String asText() {
        Object v = getValue();
        return v != null ? v.toString() : "<unknown-literal>";
    }
}