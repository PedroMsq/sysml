package interfaces.actions;

import java.util.List;

import interfaces.nodes.INode;
import interfaces.parts.IPartUsage;

public interface IActionDefinition {

	public void setActionDefinition(IActionDefinition actionDefinition);

	public INode[] getNodes();

	public List<IPartUsage> getPartUsages();
    // Retorna o nome da ActionDefinition
	public String getName();

    // Retorna os nomes dos parâmetros de entrada e saída da ação
	public List<String> getParameters();
    
    //parameter - getDirection
    //getInputs
    //getOutpus

    // Retorna os nomes das Flows internas da ação
	public List<String> getFlows(); //Owned

    // Retorna os nomes das Successions internas da ação
	public List<String> getSuccessions();

}