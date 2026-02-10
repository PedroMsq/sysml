package interfaces.behavior;

import interfaces.structures.expressions.IExpression;
import interfaces.utils.INamedElement;
import interfaces.utils.IParameter;

public interface IItemUsage extends INamedElement {
	
	// public INamedElement getType(); -> EList<Structure> getItemDefinition();
	
	public INamedElement getOwner(); // flowend ou port de onde vem o item
	
	public IExpression getPayloadExpression(); // expressão do payload
	
	public boolean isPortItem(); // parte interna de um port
	
	public boolean isPartItem();
	
}
