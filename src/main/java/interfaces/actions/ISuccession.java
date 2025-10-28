package interfaces.actions;


import interfaces.nodes.INode;
import interfaces.states.IGuard;
import interfaces.utils.INamedElement;

public interface ISuccession extends INamedElement{

	INode getTarget();

	INode getSource();

	IGuard getGuard();
	
	// Precisaria de um NamedElement genérico?
}