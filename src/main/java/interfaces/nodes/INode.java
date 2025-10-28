package interfaces.nodes;

import interfaces.control.ISuccession;
import interfaces.utils.INamedElement;

public interface INode extends INamedElement {

	ISuccession[] getIncomings();
	
	ISuccession[] getOutgoings();
	
}
