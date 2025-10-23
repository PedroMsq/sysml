package interfaces.nodes;

import interfaces.actions.ISuccession;

public interface INode {
	
	ISuccession[] getIncomings();
	
	ISuccession[] getOutgoings();
}
