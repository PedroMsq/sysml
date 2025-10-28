package interfaces.control;

import interfaces.nodes.INode;
import interfaces.states.IGuard;
import interfaces.utils.INamedElement;

public interface ISuccession extends INamedElement { // caso cada sucessão tenha um nome
	
	INode getTarget();

	INode getSource();

	IGuard getGuard();
	
}
