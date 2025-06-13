package interfaces.actions;

import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.Behavior;

public abstract class IActionUsage {

	abstract EList<Behavior> getActionDefinition();
	// unsure
}

