package adapters.control;

import java.util.ArrayList;
import java.util.List;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.DecisionNode;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.Expression;

import interfaces.control.IDecisionNode;
import interfaces.actions.ISuccession;

public class DecisionNodeAdapter implements IDecisionNode {
	
	private final DecisionNode decisionNode;
	private final Namespace actionNamespace;
	
	public DecisionNodeAdapter(DecisionNode decisionNode, Namespace actionNamespace) {
		this.decisionNode = decisionNode;
		this.actionNamespace = actionNamespace;
	}
	
	@Override
	public String getName() {
		return decisionNode.getDeclaredName() != null ? decisionNode.getDeclaredName() : "<no-name>";	
	}
	
	@Override
	public boolean isInitialNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFlowFinalNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinalNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isForkNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isJoinNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDecisionNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMergeNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ISuccession[] getIncomings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISuccession[] getOutgoings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// Retorna o elemento único que chega ao DecisionNode (target = decisionNode)
	/*
	@Override
	public List<String> getInputNames() {
		List<String> inputs = new ArrayList<>();
		for(Element elem : actionNamespace.getOwnedMember()) {
			if(!(elem instanceof SuccessionAsUsage su)) continue;
			
			for(Element tgt : su.getTarget()) {
				if(getName().equals(tgt.getDeclaredName())) {
					for(Element src : su.getSource()) {
						inputs.add(src.getDeclaredName() != null ? src.getDeclaredName() : "<no-name>");
		
					}
				}
			}
		}
		return inputs;
	}
	
	// Retorna todos os elementos que partem do DecisionNode
	@Override
	public List<String> getOutputNames() {
		List<String> outputs = new ArrayList<>();
		for(Element elem : actionNamespace.getOwnedMember()) {
			if(!(elem instanceof SuccessionAsUsage su)) continue;
			
			for(Element src : su.getSource()) {
				if(getName().equals(src.getDeclaredName())) {
					for(Element tgt : su.getTarget()) {
						outputs.add(tgt.getDeclaredName() != null ? tgt.getDeclaredName() : "<no-name>");
					}
				}
			}
		}
		return outputs;
	}
	
	// TESTAR
	@Override
	public List<String> getGuards() {
		List<String> guards = new ArrayList<>();
		for(Element elem : actionNamespace.getOwnedMember()) {
			if(!(elem instanceof SuccessionAsUsage su)) continue;
			
			// Verificar se o DecisionNode atual eh a origem do fluxo
			for(Element src : su.getSource()) {
				if(getName().equals(src.getDeclaredName())) {
					// Obter uma expressao guarda
					for(Element owned : su.getOwnedMember()) {
						if(owned instanceof Expression expr) {
							// Imprimir expressoes dado que nao possuem um nome
							String text = expr.toString();
							guards.add(text != null ? text : "<empty-expression>");
						}
					}
				}
			}
		}
		return guards;
	}
	
	@Override
	public String describe() {
		StringBuilder sb = new StringBuilder();
		sb.append("DecisionNode '").append(getName()).append("'\n");
		sb.append("  Entradas:\n");
		for(String in : getInputNames()) {
			sb.append("   - ").append(in).append("\n");
		}
		sb.append("  Saídas:\n");;
		for(String out : getOutputNames()) {
			sb.append("   - ").append(out).append("\n");
		}
		return sb.toString();
	}
	*/

}
