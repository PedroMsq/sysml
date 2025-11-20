package interfaces.attributes;

import java.util.Optional;

import interfaces.expressions.IExpression;
import interfaces.utils.IFeature;
import interfaces.utils.IParameter;

public interface IAttributeUsage extends IParameter {
	public String getName();
	public String getType();        // Retorna o tipo: Real, Integer, etc.
	public String getUnit();        // Ex: SI::kg
	public String getValue();       // Ex: "1350"
	
	Optional<IExpression> getDefaultValue();
}