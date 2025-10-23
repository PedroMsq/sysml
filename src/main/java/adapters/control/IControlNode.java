package adapters.control;

import interfaces.nodes.INode;

public interface IControlNode extends INode {
	// Usar interfaces específicas com instanceof ao invés de funções booleanas?
	boolean isInitialNode();

	boolean isFlowFinalNode();

	boolean isFinalNode();

	boolean isForkNode();

	boolean isJoinNode();

	boolean isDecisionNode();

	boolean isMergeNode();
}
