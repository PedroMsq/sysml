package adapters.actions;

import java.util.ArrayList;

import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.ControlNode;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.ReferenceUsage;

import adapters.nodes.ControlNodeAdapter;
import adapters.nodes.FlowUsageAdapter;
import adapters.nodes.NodeAdapter;
import adapters.utils.ParameterAdapter;
import interfaces.actions.IActionUsage;
import interfaces.nodes.IFlow;
import interfaces.nodes.INode;
import interfaces.structure.IActionDefinition;
import interfaces.utils.IParameter;

public class ActionUsageAdapter extends NodeAdapter implements IActionUsage {
	
	private ActionDefinition actionDefinition;
	private IParameter[] parameters;
	private INode[] nodes;
	private IFlow[] flows;

	public ActionUsageAdapter(ActionUsage actionUsage) {
		super(actionUsage);
		
		ArrayList<IParameter> parameterList = new ArrayList<>();
		ArrayList<INode> nodeList = new ArrayList<>();
		ArrayList<IFlow> flowList = new ArrayList<>();
		
		for (Element element : actionUsage.getOwnedMember()) {
        	if (element instanceof ActionUsage au) { // Subactions internas, continuar ActionUsageAdapter
        		//ActionUsageAdapter action = new ActionUsageAdapter(au);
        		// nodeList.add(action);
        		System.out.println("SUBACTION: " + au.getName());
        		
        	} else if (element instanceof ReferenceUsage ru) { // Parâmetros
        		//nodeList.add(new NodeAdapter(ru));
        		parameterList.add(new ParameterAdapter(ru));
        		
        	} else if (element instanceof ControlNode cn) {
        		nodeList.add(new ControlNodeAdapter(cn));
        	} else if (element instanceof FlowUsage fu) {
        		nodeList.add(new NodeAdapter(fu));
        		flowList.add(new FlowUsageAdapter(fu));
        	}
        }
		
		this.actionDefinition = (ActionDefinition) actionUsage.getOwner();

		
		this.parameters = parameterList.toArray(new IParameter[0]);
		this.nodes = nodeList.toArray(new INode[0]);
		this.flows = flowList.toArray(new IFlow[0]);
	}

	@Override
	public INode[] getNodes() {
		return this.nodes;
	}

	@Override
	public IParameter[] getParameters() {
		return this.parameters;
	}

	@Override
	public ActionDefinition getActionDefinition() {
		return this.actionDefinition;
	}

	@Override
	public IFlow[] getFlows() {
		return this.flows;
	}

}
