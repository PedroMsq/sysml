package interfaces.structure;

import java.util.List;

import interfaces.nodes.INode;
import interfaces.parts.IPartUsage;
import interfaces.utils.INamedElement;
import interfaces.utils.IParameter;

public interface IActionDefinition extends INamedElement {

	public INode[] getNodes();
	
	public IParameter[] getParameters(); // input e output parameters
	
	public IPartUsage[] getPartUsages();
	
}
