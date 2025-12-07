
package adapters.states;

import org.omg.sysml.lang.sysml.StateDefinition;
import org.omg.sysml.lang.sysml.StateUsage;

import interfaces.states.IStateUsage;
import interfaces.states.ITransition;
import interfaces.structure.IStateDefinition;

import java.util.ArrayList;
import java.util.List;

public class StateDefAdapter implements IStateDefinition {
    private StateDefinition stateDefinition;

    public StateDefAdapter(StateDefinition stateDefinition) {
        this.stateDefinition = stateDefinition;
    }

    @Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ITransition[] getTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStateUsage[] getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeclaredName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}
    
    /*
    @Override
    public String getName() {
        return stateDefinition.getName();
    }

    @Override
    public ITransition[] getTransitions() {
        // criar funcao utilitaria para conversao
        List<ITransition> transitions = new ArrayList<>();
        // adicionar a logica de conversão
        return transitions.toArray(new ITransition[0]);
    }

    @Override
    public IStateUsage[] getStates() {
        List<IStateUsage> states = new ArrayList<>();
        for (StateUsage stateUsage : stateDefinition.getOwnedState()) {
            states.add(new StateUsageAdapter(stateUsage));
        }
        return states.toArray(new IStateUsage[0]);
    }

    // TO-DO: boolean isParallel
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StateDef: ").append(getName()).append("\n");
        for (IStateUsage state : getStates()) {
            sb.append(state.toString()).append("\n");
        }
        for (ITransition transition : getTransitions()) {
            sb.append(transition.toString()).append("\n");
        }
        return sb.toString();
    }
    
    */
}