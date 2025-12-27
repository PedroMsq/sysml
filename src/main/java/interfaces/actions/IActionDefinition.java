package interfaces.actions;

import interfaces.nodes.IFlow;
import interfaces.nodes.INode;
import interfaces.parts.IPartUsage;
import interfaces.utils.IParameter;

public interface IActionDefinition {

	public INode[] getNodes();

	// public IPartUsage[] getPartUsages();

    // Retorna os nomes dos parâmetros de entrada e saída da ação
	public IParameter[] getParameters();
    
    //parameter - getDirection
    //getInputs
    //getOutpus

    // Retorna os nomes das Flows internas da ação
	public IFlow[] getFlows(); //Owned

    // Retorna os nomes das Successions internas da ação
	// public ISuccession[] getSuccessions();

}