package adapters.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.ControlNode;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FeatureDirectionKind;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.TransitionUsage;

import adapters.utils.AdapterUtils;
import adapters.utils.InitialNode;
import adapters.utils.NamedElementAdapter;
import adapters.nodes.NodeAdapter;
import adapters.utils.ParameterAdapter;
import adapters.nodes.ControlNodeAdapter;
import adapters.nodes.FlowUsageAdapter;
import adapters.actions.ActionUsageAdapter;
import interfaces.actions.IActionDefinition;
import interfaces.actions.IActionUsage;
import interfaces.actions.ISuccession;
import interfaces.control.IControlNode;
import interfaces.nodes.IFlow;
import interfaces.nodes.INode;
import interfaces.parts.IPartUsage;
import interfaces.utils.IParameter;

public class ActionDefinitionAdapter extends NamedElementAdapter implements IActionDefinition {

    private INode[] nodes;
    private IParameter[] parameters;
    private IFlow[] flows;
    private HashMap<String,String> owners;

    public ActionDefinitionAdapter(ActionDefinition actionDefinition) {
    	super(actionDefinition);
    	owners = new HashMap<>();
    	
    	Set<INode> nodeList = new HashSet<INode>();
        ArrayList<IParameter> parameterList = new ArrayList<>();
        ArrayList<IFlow> flowList = new ArrayList<>();
    	
        // Analisar ActionDefinition
        for (Element element : actionDefinition.getOwnedMember()) {
        	
        	// Caso 1: SuccessionAsUsage direto
            if (element instanceof SuccessionAsUsage su && su.getSource().getFirst().getDeclaredName().equals("start")) {
            	InitialNode init = new InitialNode();
            	init.setDeclaredName("start");
            	init.setOwner(actionDefinition);
            	ControlNodeAdapter initial = new ControlNodeAdapter(init);
            	nodeList.add(initial);
            }
        }

        
        // ActionUsage, ReferenceUsage (parameters) + owners ControlNode, FlowUsage
        for (Element element : actionDefinition.getOwnedUsage()) {
        	if (element instanceof ControlNode cn) {
        		nodeList.add(new ControlNodeAdapter(cn));
        	} else if (element instanceof FlowUsage fu) {
        		flowList.add(new FlowUsageAdapter(fu));
        	} else if (element instanceof ActionUsage au && !(element instanceof TransitionUsage)) {
        		nodeList.add(new ActionUsageAdapter(au)); // IMPRESSÃO DUPLA POIS DIVERSOS ELEMENTOS SÃO ActionUsage
        	} else if (element instanceof ReferenceUsage ru) { // Lidar com elementos que não sâo parâmetros
        		//nodeList.add(new NodeAdapter(ru));
        		parameterList.add(new ParameterAdapter(ru));
        		
        		if (ru.getOwner() instanceof ActionUsage owner) {
        			owners.put(new ParameterAdapter(ru).getID(), owner.getElementId());
        		}
        	}
        }
        
        // Ajustar (?)
        for (INode node : nodeList) {
        	if (node instanceof IParameter && owners.containsKey(node.getID())) {
        		
        		for (INode possibleOwner : nodeList) {
        			if (possibleOwner.getID().equals(owners.get(node.getID())) &&
        				possibleOwner instanceof ActionUsageAdapter) {
        				IParameter param = (IParameter) node;
        				ActionUsageAdapter action = (ActionUsageAdapter) possibleOwner;
        				
        				param.setActionDefinition((ActionUsage) action);
        			}
        		}
        	}
        }
    	
    	this.nodes = nodeList.toArray(new INode[0]);
        this.parameters = parameterList.toArray(new IParameter[0]);
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
    public IFlow[] getFlows() {
        return this.flows;
    }
    
}
