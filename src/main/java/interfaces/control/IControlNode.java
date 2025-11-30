package interfaces.control;

import interfaces.nodes.INode;

public interface IControlNode extends INode {
	// analisar os sinais booleanos de acordo com cada tipo de Control Node
	public boolean isForkNode();

	public boolean isJoinNode();

	public boolean isDecisionNode();
	
	public boolean isMergeNode();
	
}
