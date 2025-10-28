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
    
    //parameter - getDirection
    //getInputs
    //getOutpus

    // Retorna os nomes das Flows internas da ação
    List<String> getFlows(); //Owned

    // Retorna os nomes das Successions internas da ação
    List<String> getSuccessions();

}