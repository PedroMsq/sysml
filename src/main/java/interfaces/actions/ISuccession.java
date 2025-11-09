package interfaces.actions;


import interfaces.nodes.INode;
import interfaces.states.IGuard;
import interfaces.utils.INamedElement;

public interface ISuccession extends INamedElement{

	public INode getTarget();

	public INode getSource();

	public IGuard getGuard();
	
	public String toString();
	
	// Precisaria de um NamedElement genérico?
}