package adapters.utils;

import org.omg.sysml.lang.sysml.Element;

import interfaces.utils.INamedElement;

public class NamedElementAdapter implements INamedElement {
	
	Element namedElement;
	
	public NamedElementAdapter(Element namedElement) {
		this.namedElement = namedElement;
	}

	@Override
	public String getName() {
		return namedElement.getDeclaredName() != null ? namedElement.getDeclaredName() : "<no-name>";
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

}
