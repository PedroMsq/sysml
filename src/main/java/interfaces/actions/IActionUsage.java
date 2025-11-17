package interfaces.actions;

import interfaces.nodes.INode;
import java.util.List;
import interfaces.utils.IFeature;
import interfaces.utils.IParameter;
import interfaces.structure.IActionDefinition;

public interface IActionUsage extends INode {

	public INode[] getNodes();
	
	public IParameter[] getParameters(); // ao invés de IParameter getDirection(); ser implementado aqui?
	
	public IActionDefinition getActionDefinition(); // receber quem definiu a Action Usage
	
	// public boolean isCallBehaviorAction();

	// public boolean isSendSignalAction();

	// public boolean isAcceptEventAction();
	
}