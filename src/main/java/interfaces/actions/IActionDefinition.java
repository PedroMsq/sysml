package interfaces.actions;

import java.util.List;

public interface IActionDefinition {

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