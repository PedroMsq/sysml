package gamine.domain;

import java.util.Objects;

import org.omg.sysml.lang.sysml.Element;

public class SysMLV2BehaviourAction {

    private final Element element;

    public SysMLV2BehaviourAction(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public String getId() {
        return element.getElementId();
    }

    public String getName() {
        return element.getDeclaredName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMLV2BehaviourAction that = (SysMLV2BehaviourAction) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Action[" + getName() + "]";
    }
}
