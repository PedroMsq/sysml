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

        // varre todos os elementos do namespace (fluxos)
        for (Element elem : containerNamespace.getOwnedMember()) {

            if (elem instanceof SuccessionAsUsage su) {
                // se o nó atual é destino (target)
                for (Element tgt : su.getTarget()) {
                    if (nodeElement.getDeclaredName().equals(tgt.getDeclaredName())) {
                        incomingList.add(new SuccessionAdapter(su, containerNamespace));
                    }
                }
                // se o nó atual é origem (source)
                for (Element src : su.getSource()) {
                    if (nodeElement.getDeclaredName().equals(src.getDeclaredName())) {
                        outgoingList.add(new SuccessionAdapter(su, containerNamespace));
                    }
                }
            }

            // suporte a Transitions que contenham Successions
            if (elem instanceof TransitionUsage tu) {
                for (Element sub : tu.getOwnedMember()) {
                    if (!(sub instanceof SuccessionAsUsage su)) continue;
                    for (Element tgt : su.getTarget()) {
                        if (nodeElement.getDeclaredName().equals(tgt.getDeclaredName())) {
                            incomingList.add(new SuccessionAdapter(su, containerNamespace));
                        }
                    }
                    for (Element src : su.getSource()) {
                        if (nodeElement.getDeclaredName().equals(src.getDeclaredName())) {
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



	/*
	 * @Override public ISuccession getIncomings() { List<ISuccession> incomings =
	 * new ArrayList<>();
	 * 
	 * for (Element elem : actionNamespace.getOwnedMember()) { if (elem instanceof
	 * SuccessionAsUsage su) { for (Element tgt : su.getTarget()) { if
	 * (getName().equals(tgt.getDeclaredName())) { incomings.add(new
	 * SuccessionAdapter(su, actionNamespace)); } } }
	 * 
	 * if (elem instanceof TransitionUsage tu) { for (Element sub :
	 * tu.getOwnedMember()) { if (!(sub instanceof SuccessionAsUsage su)) continue;
	 * for (Element tgt : su.getTarget()) { if
	 * (getName().equals(tgt.getDeclaredName())) { incomings.add(new
	 * SuccessionAdapter(su, actionNamespace)); } } } } }
	 * 
	 * return incomings.toArray(new ISuccession[0]); }
	 */

	/*
	 * @Override public ISuccession[] getOutgoings() { List<ISuccession> outgoings =
	 * new ArrayList<>();
	 * 
	 * for (Element elem : actionNamespace.getOwnedMember()) { // pega
	 * SuccessionAsUsage diretas if (elem instanceof SuccessionAsUsage su) { for
	 * (Element src : su.getSource()) { if (getName().equals(src.getDeclaredName()))
	 * { outgoings.add(new SuccessionAdapter(su, actionNamespace)); } } }
	 * 
	 * // pega SuccessionAsUsage dentro de TransitionUsage if (elem instanceof
	 * TransitionUsage tu) { for (Element sub : tu.getOwnedMember()) { if (!(sub
	 * instanceof SuccessionAsUsage su)) continue; for (Element src :
	 * su.getSource()) { if (getName().equals(src.getDeclaredName())) {
	 * outgoings.add(new SuccessionAdapter(su, actionNamespace)); } } } } }
	 * 
	 * return outgoings.toArray(new ISuccession[0]); }
	 */

}