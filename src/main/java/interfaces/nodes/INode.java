package interfaces.nodes;

import interfaces.actions.ISuccession;
import interfaces.utils.INamedElement;

public interface INode extends INamedElement {

	public ISuccession[] getIncomings();
	
	public ISuccession[] getOutgoings();
	
}
