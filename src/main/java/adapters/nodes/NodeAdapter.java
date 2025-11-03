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

	private final Namespace nodeNamespace; // Representa o elemento SysML do nó
	private ISuccession[] incomings;
	private ISuccession[] outgoings;

	public NodeAdapter(Namespace nodeNamespace) {
		this.nodeNamespace = nodeNamespace;
		EList<Element> ownerNamespace = nodeNamespace.getOwnedMember();
		ArrayList<ISuccession> incomingSuccessions = new ArrayList<ISuccession>();
		ArrayList<ISuccession> outgoingSuccessions = new ArrayList<ISuccession>();

		if (ownerNamespace != null) {
			for (Element elem : nodeNamespace.getOwnedMember()) {
				if (elem instanceof SuccessionAsUsage su) {
					for (Element tgt : su.getTarget()) {
						if (getName().equals(tgt.getDeclaredName())) {
							incomingSuccessions.add(new SuccessionAdapter(su, nodeNamespace));
						}
					}
				}
				if (elem instanceof TransitionUsage tu) {
					for (Element sub : tu.getOwnedMember()) {
						if (!(sub instanceof SuccessionAsUsage su))
							continue;
						for (Element tgt : su.getTarget()) {
							if (getName().equals(tgt.getDeclaredName())) {
								incomingSuccessions.add(new SuccessionAdapter(su, nodeNamespace));
							}
						}
					}
				}
			}
		}

		for (Element elem : nodeNamespace.getOwnedMember()) {
			if (elem instanceof SuccessionAsUsage su) {
				for (Element src : su.getSource()) {
					if (getName().equals(src.getDeclaredName())) {
						outgoingSuccessions.add(new SuccessionAdapter(su, nodeNamespace));
					}
				}
			}
			if (elem instanceof TransitionUsage tu) {
				for (Element sub : tu.getOwnedMember()) {
					if (!(sub instanceof SuccessionAsUsage su))
						continue;
					for (Element src : su.getSource()) {
						if (getName().equals(src.getDeclaredName())) {
							outgoingSuccessions.add(new SuccessionAdapter(su, nodeNamespace));
						}
					}
				}
			}
		}

		this.incomings = incomingSuccessions.toArray(new ISuccession[0]);
		this.outgoings = outgoingSuccessions.toArray(new ISuccession[0]);
	}

	@Override
	public String getName() {
		return nodeNamespace.getDeclaredName();
	}

	@Override
	public String getDeclaredName() {
		return nodeNamespace.getDeclaredName();
	}

	@Override
	public ISuccession[] getIncomings() {
		return this.incomings;
	}

	@Override
	public ISuccession[] getOutgoings() {
		return this.outgoings;
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
