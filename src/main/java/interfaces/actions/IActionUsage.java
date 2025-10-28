package interfaces.actions;

import interfaces.nodes.INode;
import java.util.List;
import interfaces.utils.IFeature;
import interfaces.utils.IParameter;
import interfaces.structure.IActionDefinition;

public interface IActionUsage extends INode {

	INode[] getNodes();
	
	IParameter[] getParameters(); // ao invés de IParameter getDirection(); ser implementado aqui?
	
	IActionDefinition getActionDefinition(); // receber quem definiu a Action Usage
	
	boolean isCallBehaviorAction();

	boolean isSendSignalAction();

	boolean isAcceptEventAction();
	
}