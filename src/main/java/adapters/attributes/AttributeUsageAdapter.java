package adapters.attributes;

import java.util.Optional;

import org.omg.sysml.lang.sysml.AttributeUsage;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.Expression;
import org.omg.sysml.lang.sysml.FeatureValue;
import org.omg.sysml.lang.sysml.OperatorExpression;
import org.omg.sysml.lang.sysml.FeatureReferenceExpression;

import adapters.expressions.ExpressionAdapter;
import adapters.utils.NamedElementAdapter;
import interfaces.attributes.IAttributeUsage;
import interfaces.expressions.IExpression;

public class AttributeUsageAdapter extends NamedElementAdapter implements IAttributeUsage {

	private final AttributeUsage usage;

	public AttributeUsageAdapter(AttributeUsage usage) {
		super(usage);
		this.usage = usage;
	}

	//Retorna a Expression que representa o valor do atributo (caso exista) já adaptada para IExpression.
	@Override
	public Optional<IExpression> getDefaultValue() {
		Expression expr = extractExpression(usage);

		if (expr == null)
			return Optional.empty();

		// remove wrappers como Operator[], FeatureRef para obter o value
		Expression unwrapped = unwrap(expr);

		// adapta para IExpression
		return Optional.of(ExpressionAdapter.of(unwrapped));
	}

	
	// EXTRAÇÃO DE EXPRESSÕES 
	private Expression extractExpression(AttributeUsage au) {

		// Caso 1: FeatureValue está em OwnedFeature
		for (Feature f : au.getOwnedFeature()) {
			if (f instanceof FeatureValue fv && fv.getOwnedMemberElement() instanceof Expression expr) {
				return expr;
			}
		}

		// Caso 2: (PartDefinitions) FeatureValue em OwnedMember
		for (Element e : au.getOwnedMember()) {
			if (e instanceof FeatureValue fv && fv.getOwnedMemberElement() instanceof Expression expr) {
				return expr;
			}
		}

		// Caso 3: FeatureValue em OwnedRelationship
		for (Element rel : au.getOwnedRelationship()) {
			if (rel instanceof FeatureValue fv && fv.getOwnedMemberElement() instanceof Expression expr) {
				return expr;
			}
		}

		return null;
	}

	
    private Expression unwrap(Expression expr) {

        // Caso seja OperatorExpression com argumento único (ex: 500 [kg])
        if (expr instanceof OperatorExpression op) {

            // operador [] → expr[unit]
            if ("[]".equals(op.getOperator()) && !op.getArgument().isEmpty()) {
                return unwrap(op.getArgument().get(0)); // retorna o literal 500
            }

            // operador "=" (assignment)
            if ("=".equals(op.getOperator()) && !op.getArgument().isEmpty()) {
                return unwrap(op.getArgument().get(0));
            }
        }

        // Caso seja Reference ao tipo (ex: kg → ignore e retorne literal anterior)
        if (expr instanceof FeatureReferenceExpression ref) {
            return expr; // deixa ExpressionAdapter se virar com isso mais tarde
        }

        // Caso comum: já é literal
        return expr;
    }
	
	
	@Override
	public String getName() {
		return usage.getDeclaredName();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOutput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ParameterDirection getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOrdered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUnique() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isComposite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDeclaredName() {
		// TODO Auto-generated method stub
		return null;
	}
}
