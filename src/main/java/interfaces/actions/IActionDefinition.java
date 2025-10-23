package interfaces.actions;

import java.util.List;

import interfaces.nodes.INode;
import interfaces.parts.IPartUsage;

public interface IActionDefinition {

	void setActionDefinition(IActionDefinition actionDefinition);

	INode[] getNodes();

	void setName(String nameAD);

	List<IPartUsage> getPartUsages();
    // Retorna o nome da ActionDefinition
    String getName();

    // Retorna os nomes dos parâmetros de entrada e saída da ação
    List<String> getParameters();

    // Retorna os nomes das Flows internas da ação
    List<String> getFlows();

    // Retorna os nomes das Successions internas da ação
    List<String> getSuccessions();

    
    //TO-DO
    List<String> getIncomingFlows();
    List<String> getOutgoingFlows();
    List<String> getIncomingSuccessions();
    List<String> getOutgoingSuccessions();
}