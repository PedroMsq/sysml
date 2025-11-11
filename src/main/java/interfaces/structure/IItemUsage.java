package interfaces.structure;

import interfaces.expressions.IExpression;
import interfaces.utils.INamedElement;
import interfaces.utils.IParameter;

public interface IItemUsage extends IParameter {
	
	public INamedElement getType(); // tipo do item
	
	public INamedElement getOwner(); // flowend ou port de onde vem o item
	
	public IExpression getPayloadExpression(); // expressÃ£o do payload
	
	public boolean isPortItem(); // parte interna de um port
	
	public boolean isPartItem();
	
}