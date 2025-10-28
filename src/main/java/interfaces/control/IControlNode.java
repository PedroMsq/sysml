package interfaces.control;

import interfaces.nodes.INode;

public interface IControlNode extends INode {
	// analisar os sinais booleanos de acordo com cada tipo de Control Node
	boolean isInitialNode();

	boolean isFlowFinalNode();

	boolean isFinalNode();

	boolean isForkNode();

	boolean isJoinNode();

	boolean isDecisionNode();
	
	boolean isMergeNode();
	
}
