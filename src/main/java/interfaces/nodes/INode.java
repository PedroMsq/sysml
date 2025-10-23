package interfaces.nodes;

import adapters.actions.ISuccession;

public interface INode {
	
	ISuccession[] getIncomings();
	
	ISuccession[] getOutgoings();
}
