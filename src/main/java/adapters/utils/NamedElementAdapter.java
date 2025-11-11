package adapters.utils;

import org.omg.sysml.lang.sysml.Element;
import interfaces.utils.INamedElement;

public class NamedElementAdapter extends BaseAdapter implements INamedElement {
	
    protected final Element namedElement;
	
    public NamedElementAdapter(Element namedElement) {
        super(namedElement);
        this.namedElement = namedElement;
    }

    @Override
    public String getDeclaredName() {
        return namedElement != null && namedElement.getDeclaredName() != null
                ? namedElement.getDeclaredName()
                : "<no-declared-name>";
    }

    @Override
    public String getName() {
        return getDeclaredName();
    }
}
