package interfaces.actions;

import interfaces.nodes.IFlow;
import interfaces.nodes.INode;
import java.util.List;

import org.omg.sysml.lang.sysml.ActionDefinition;

import interfaces.utils.IParameter;
import interfaces.structure.IActionDefinition;

public interface IActionUsage extends INode {

	public INode[] getNodes();
	
	public IParameter[] getParameters();
	
	public IFlow[] getFlows();
	
	public ActionDefinition getActionDefinition(); // receber quem definiu a Action Usage
	
	// public boolean isCallBehaviorAction();

	// public boolean isSendSignalAction();

	// public boolean isAcceptEventAction();
	
}