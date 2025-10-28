package interfaces.actions;


import interfaces.nodes.INode;

public interface ISuccession {

	INode getTarget();

	INode getSource();

	String getGuard(); //IGuard
	// Precisaria de um NamedElement genérico?
}