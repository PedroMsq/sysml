package gamine;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.ControlNode;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.impl.ActionUsageImpl;
import org.omg.sysml.lang.sysml.ForkNode;
import org.omg.sysml.lang.sysml.JoinNode;
import org.omg.sysml.lang.sysml.DecisionNode;
import org.omg.sysml.lang.sysml.MergeNode;
import adapters.behavior.actions.nodes.NodeAdapter;
import gamine.domain.SysMLV2Configuration;
import interfaces.behavior.actions.ISuccession;
import obp3.runtime.sli.SemanticRelation;

public class SysMLV2ActionSemantics implements SemanticRelation<Element, SysMLV2Configuration> {
    
    ActionDefinition act;
    // ActionUsageAdapter actionUsageAdapter
    
    public SysMLV2ActionSemantics(ActionDefinition def) {
        act = def;
        // 
    }
    
    // recebe o actionUsageAdapter
    // acessar o ActionDefinitionAdapter desse ActionUsage com base no getActionDefinition() para em seguida executar o que existir
    @Override
    public List<SysMLV2Configuration> initial() {
        List<SuccessionAsUsage> initSucc = new ArrayList<>();
        for (Element elem : act.getOwnedElement()) {
            if (elem instanceof SuccessionAsUsage s) {
                EList<Element> source = s.getSource();
                for (Element node : source) {
                    if (node.getDeclaredName() != null && 
                        node.getDeclaredName().equals("start")) {
                        initSucc.add(s);
                    }
                }
            }
        }
        return List.of(new SysMLV2Configuration(initSucc));
    }
    
    // Conta quantas incoming successions estão na configuração atual
    private int countAvailableIncomings(ISuccession[] incomings, List<SuccessionAsUsage> successions) {
        int count = 0;
        for (ISuccession incoming : incomings) {
            for (SuccessionAsUsage succession : successions) {
                if (incoming.getID().equals(succession.getElementId())) {
                    count++;
                }
            }
        }
        return count;
    }

    // Verifica se um nó está habilitado para execução baseado em sua semântica
    private boolean isEnabled(Element node, List<SuccessionAsUsage> currentSuccessions) {
        NodeAdapter nodeAdapter = new NodeAdapter(node);
        ISuccession[] incomings = nodeAdapter.getIncomings();
        
        // Conta quantas incoming successions estão na configuração atual
        int availableIncomings = countAvailableIncomings(incomings, currentSuccessions);
        
        // Semântica baseada no tipo do nó
        if (node instanceof JoinNode) {
            // JoinNode: TODAS as incoming successions devem estar presentes
            return availableIncomings == incomings.length;
        } 
        else if (node instanceof MergeNode || 
                 node instanceof DecisionNode || 
                 (node.getClass().equals(ActionUsageImpl.class))) {
            // MergeNode, DecisionNode, ActionUsage 
            return availableIncomings >= 1;
        }
        else if (node instanceof ForkNode) {
            // ForkNode
            return availableIncomings >= 1;
        }
        
        // Default
        return availableIncomings == incomings.length;
    }
    
    @Override
    public List<Element> actions(SysMLV2Configuration configuration) {
        List<SuccessionAsUsage> successions = configuration.successions;
        List<Element> enabledActions = new ArrayList<>();
        
        for (SuccessionAsUsage succession : successions) {
            assert (succession.getTarget().size() == 1);
            Element target = succession.getTarget().getFirst();
            
            if (isEnabled(target, successions)) {
                if (!enabledActions.contains(target)) {
                    enabledActions.add(target);
                }
            }
        }
        return enabledActions;
    }

    // Remove as incoming successions que foram consumidas pela execução do nó
    private void removeConsumedSuccessions(Element node, SysMLV2Configuration configuration, NodeAdapter nodeAdapter) {
        ISuccession[] incomings = nodeAdapter.getIncomings();
        List<SuccessionAsUsage> newList = new ArrayList<>();
        
        for (SuccessionAsUsage succession : configuration.successions) {
            boolean shouldRemove = false;
            
            for (ISuccession incoming : incomings) {
                if (incoming.getID().equals(succession.getElementId())) {
                    shouldRemove = true;
                }
            }
            if (!shouldRemove) {
                newList.add(succession);
            }
        }
        configuration.successions = newList;
    }
    
    @Override
    public List<SysMLV2Configuration> execute(Element node, SysMLV2Configuration configuration) {
        configuration = configuration.clone();
        System.out.println("Node name: " + node.getDeclaredName());
        
        NodeAdapter nodeAdapter = new NodeAdapter(node);
        
        // Remove incoming successions consumidas
        removeConsumedSuccessions(node, configuration, nodeAdapter);
        
        // Adiciona outgoing successions baseado no tipo do nó
        addOutgoingSuccessions(node, configuration, nodeAdapter);
        
        return List.of(configuration);
    }
    
    // Adiciona outgoing successions baseado na semântica do nó
    private void addOutgoingSuccessions(Element node, SysMLV2Configuration configuration, NodeAdapter nodeAdapter) {
        if (node instanceof DecisionNode) {
            // DecisionNode: escolhe UMA outgoing succession baseado em condição
            addDecisionNodeOutgoing(node, configuration);
        } 
        else {
            // ForkNode, JoinNode, MergeNode, ActionUsage 
            // adiciona TODAS as outgoing successions
            addAllOutgoingSuccessions(node, configuration);
        }
    }

    // Adiciona todas as outgoing successions de um nó
    private void addAllOutgoingSuccessions(Element node, SysMLV2Configuration configuration) {
        for (Element elem : act.getOwnedElement()) {
            if (elem instanceof SuccessionAsUsage s) {
                Element source = s.getSource().getFirst();
                if (source == node) {
                    configuration.successions.add(s);
                }
            }
        }
    }
    
    // Para DecisionNode escolhe uma outgoing succession
    // TODO: Implementar lógica de avaliação de guards para escolher o branch correto
    private void addDecisionNodeOutgoing(Element node, SysMLV2Configuration configuration) {
        List<SuccessionAsUsage> outgoingSuccessions = new ArrayList<>();
        
        // Coleta todas as outgoing successions
        for (Element elem : act.getOwnedElement()) {
            if (elem instanceof SuccessionAsUsage s) {
                Element source = s.getSource().getFirst();
                if (source == node) {
                    outgoingSuccessions.add(s);
                }
            }
        }
        if (!outgoingSuccessions.isEmpty()) {
            SuccessionAsUsage chosen = outgoingSuccessions.get(0); // Escolhe a primeira
            configuration.successions.add(chosen);
            
            System.out.println("DecisionNode branch: " + 
                             chosen.getTarget().getFirst().getDeclaredName());
        }
    }
}