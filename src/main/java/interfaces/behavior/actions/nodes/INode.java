package interfaces.behavior.actions.nodes;

import interfaces.behavior.actions.ISuccession;
import interfaces.utils.INamedElement;

public interface INode extends INamedElement {

	public ISuccession[] getIncomings();
	
	public ISuccession[] getOutgoings();
	
}
