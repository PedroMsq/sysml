package interfaces.actions;


import interfaces.nodes.INode;

public interface ISuccession {

	INode getTarget();

	INode getSource();

	String getGuard();

	void setSource(INode node);

	void setTarget(INode node);
	// Precisaria de um NamedElement genérico?
}