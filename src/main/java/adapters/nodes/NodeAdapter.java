package adapters.nodes;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.TransitionUsage;

import adapters.actions.SuccessionAdapter;
import interfaces.actions.ISuccession;
import interfaces.nodes.INode;

public class NodeAdapter implements INode {

    private final Element nodeElement; // o nó em si
    private final ISuccession[] incomings;
    private final ISuccession[] outgoings;

    public NodeAdapter(Element nodeElement, Namespace containerNamespace) {
        this.nodeElement = nodeElement;

        ArrayList<ISuccession> incomingList = new ArrayList<>();
        ArrayList<ISuccession> outgoingList = new ArrayList<>();

        // varre todos os elementos do namespace
        for (Element elem : containerNamespace.getOwnedMember()) { //TODO- alterar para UUID

            if (elem instanceof SuccessionAsUsage su) {
                // se o nó atual é target da succession (target)
                for (Element tgt : su.getTarget()) {
                	if (namesEqual(nodeElement.getDeclaredName(), tgt.getDeclaredName())) {
                        incomingList.add(new SuccessionAdapter(su, containerNamespace));
                    }
                }
                // se o nó atual é origem (source)
                for (Element src : su.getSource()) {
                	if (namesEqual(nodeElement.getDeclaredName(), src.getDeclaredName())) {
                        outgoingList.add(new SuccessionAdapter(su, containerNamespace));
                    }
                }
            }

            // suporte a Transitions que contenham Successions
            if (elem instanceof TransitionUsage tu) {
                for (Element sub : tu.getOwnedMember()) {
                    if (!(sub instanceof SuccessionAsUsage su)) continue;
                    for (Element tgt : su.getTarget()) {
                    	if (namesEqual(nodeElement.getDeclaredName(), tgt.getDeclaredName())) {
                    	    incomingList.add(new SuccessionAdapter(su, containerNamespace));
                    	}
                    }
                    for (Element src : su.getSource()) {
                    	if (namesEqual(nodeElement.getDeclaredName(), src.getDeclaredName())) {
                            outgoingList.add(new SuccessionAdapter(su, containerNamespace));
                        }
                    }
                }
            }
        }

        this.incomings = incomingList.toArray(new ISuccession[0]);
        this.outgoings = outgoingList.toArray(new ISuccession[0]);
    }

    @Override
    public String getDeclaredName() {
        return nodeElement.getDeclaredName();
    }

    @Override
    public ISuccession[] getIncomings() {
        return incomings;
    }

    @Override
    public ISuccession[] getOutgoings() {
        return outgoings;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
	    
	    String name = (nodeElement.getDeclaredName() != null)
	            ? nodeElement.getDeclaredName()
	            : nodeElement.getClass().getSimpleName();

	    StringBuilder sb = new StringBuilder();
	    sb.append("\n=== NODE: ").append(name).append(" ===\n");

	    if (incomings.length > 0) {
	        sb.append("\n--- INCOMING SUCCESSIONS ---\n");
	        for (ISuccession inc : incomings) {
	            String src = inc.getSource() != null ? inc.getSource().getDeclaredName() : "<anon>";
	            String tgt = inc.getTarget() != null ? inc.getTarget().getDeclaredName() : "<anon>";
	            sb.append("De: ").append(src).append(" | Para: ").append(tgt).append("\n");
	        }
	    }

	    if (outgoings.length > 0) {
	        sb.append("\n--- OUTGOING SUCCESSIONS ---\n");
	        for (ISuccession out : outgoings) {
	            String src = out.getSource() != null ? out.getSource().getDeclaredName() : "<anon>";
	            String tgt = out.getTarget() != null ? out.getTarget().getDeclaredName() : "<anon>";
	            sb.append("De: ").append(src)
	              .append(" | Para: ").append(tgt)
	              .append(" | Guarda: <sem guarda>\n");
	        }
	    }
	    // ignora nodes sem conexões e sem nome
//	    if (incomings.length == 0 && outgoings.length == 0
//	            && (nodeElement.getDeclaredName() == null || nodeElement.getDeclaredName().isBlank())) {
//	        return "";
//	    }
	    if (incomings.length == 0 && outgoings.length == 0) {
	        sb.append("\n(sem conexões - nó isolado)\n");
	    }

	    return sb.toString();
	}


	
	// MÉTODO UTILITÁRIO - verificar lógica dos nomes
	private static boolean namesEqual(String a, String b) {
	    if (a == null && b == null) return true;
	    if (a == null || b == null) return false;
	    return a.equals(b);
	}

}