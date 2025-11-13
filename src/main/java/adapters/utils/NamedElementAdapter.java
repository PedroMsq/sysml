package adapters.utils;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

import interfaces.utils.INamedElement;

public class NamedElementAdapter implements INamedElement {
	
	private final Element namedElement;
	
	public NamedElementAdapter(Element namedElement) {
		this.namedElement = namedElement;
	}

	@Override
	public String getName() {
		return namedElement.getDeclaredName() != null ? namedElement.getDeclaredName() : "<no-name>";
	}

	@Override
	public String getDeclaredName() {
		// TODO Auto-generated method stub
		return null;
	}

}
