package interfaces.nodes;

import interfaces.actions.ISuccession;
import interfaces.utils.INamedElement;

public interface INode extends INamedElement {

	ISuccession[] getIncomings();
	
	ISuccession[] getOutgoings();
	
}
