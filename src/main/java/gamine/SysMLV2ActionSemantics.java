package gamine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import adapters.behavior.actions.nodes.NodeAdapter;
import gamine.domain.SysMLV2Configuration;
import interfaces.behavior.actions.ISuccession;
import obp3.runtime.sli.SemanticRelation;

public class SysMLV2ActionSemantics implements SemanticRelation<Element,SysMLV2Configuration> {
    
	ActionDefinition act;
	
	public SysMLV2ActionSemantics(ActionDefinition def) {
		act = def;
	}
	
	// Obter InitialNode
	@Override
    public List<SysMLV2Configuration> initial() {
		
		List<SuccessionAsUsage> initSucc = new ArrayList<SuccessionAsUsage>();
		// Navega pelos elements pertencentes a ActionDefinition
		for (Element elem : act.getOwnedElement()) {
			if (elem instanceof SuccessionAsUsage) {
				// Dado que se trata de uma SuccessionAsUsage, realiza um cast e busca o source de cada element
				SuccessionAsUsage s = (SuccessionAsUsage) elem;
				EList<Element> source = s.getSource();
				for (Element node : source) {
					// Procura pelo start para que seja adicionado na lista
					if (node.getDeclaredName().equals("start")) {
						initSucc.add(s);
					}
				}
			}
		}
		// Retorna uma lista com uma nova configuração baseada no InitialNode encontrado
		return List.of(new SysMLV2Configuration(initSucc));
    }

	// Dado uma configuration, retorna um lista de elementos coletados a partir das successions.
    @Override
    public List<Element> actions(SysMLV2Configuration configuration) {
    	List<SuccessionAsUsage> successions = configuration.successions;
    	
    	// Verifica se existe apenas um target
    	List<Element> elems = new ArrayList<Element>();
    	for (SuccessionAsUsage successionAsUsage : successions) {
			assert (successionAsUsage.getTarget().size() ==  1);
			
			// Após verificar, recebe o target e sumona checkIncomingSuccession
			Element first = successionAsUsage.getTarget().getFirst();
			if (checkIncomingSuccessions(first, successions)) {
				elems.add(successionAsUsage.getTarget().getFirst());
			}    
  
		}
    	// Retorna os elementos obtidos
        return elems;
    }

    // Recebe o target de uma succession e lista de successions e confirma se existem incoming successions
    private boolean checkIncomingSuccessions(Element first, List<SuccessionAsUsage> successions) {
		//TODO Add code to check if the succession guards are true to consider them traversable
    	NodeAdapter nodeAd = new NodeAdapter(first);
		ISuccession[] incomings = nodeAd.getIncomings();
		int count = 0;
		for (ISuccession iSuccession : incomings) {
			for (SuccessionAsUsage iSuccession2 : successions) {
				if (iSuccession.getID().equals(iSuccession2.getElementId())) {
					count++;
				}
			}
		}
		if (incomings.length == count) {
			return true;
		}
		return false;
	}

	@Override
    public List<SysMLV2Configuration> execute(Element node, SysMLV2Configuration configuration) {
		configuration = configuration.clone();
		System.out.println("Node name: " + node.getDeclaredName());
		
		NodeAdapter nodeAd = new NodeAdapter(node);
		
		ISuccession[] incomings = nodeAd.getIncomings();
		List<SuccessionAsUsage> newList = new ArrayList<SuccessionAsUsage>();
		for (ISuccession iSuccession : incomings) {
			for (SuccessionAsUsage type: configuration.successions) {
				if (!iSuccession.getID().equals(type.getElementId())) {
					newList.add(type);
				}
			}
		}
		
		configuration.successions = newList;
		for (Element elem : act.getOwnedElement()) {
			if (elem instanceof SuccessionAsUsage) {
				SuccessionAsUsage s = (SuccessionAsUsage) elem;
				Element source = s.getSource().getFirst();
				if (source == node) {
					configuration.successions.add(s);
				}
			}
		}
		
    	return List.of(configuration);
    }
}
