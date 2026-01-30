package adapters.nodes;

import java.util.ArrayList;
import java.util.Objects;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.TransitionUsage;

import adapters.actions.SuccessionAdapter;
import adapters.utils.AdapterUtils;
import adapters.utils.NamedElementAdapter;
import interfaces.actions.ISuccession;
import interfaces.nodes.INode;

public class NodeAdapter extends NamedElementAdapter implements INode {

    protected final Element nodeElement; // o nó em si
    private final ISuccession[] incomings;
    private final ISuccession[] outgoings;

    public NodeAdapter(Element nodeElement) {
        super(nodeElement);
        Namespace containerNamespace = (Namespace) nodeElement.getOwner();
        this.nodeElement = nodeElement;

        ArrayList<ISuccession> incomingList = new ArrayList<>();
        ArrayList<ISuccession> outgoingList = new ArrayList<>();
        // node.getName equals start, containerNamespace equals Action -> extrair start
        for (Element elem : containerNamespace.getOwnedMember()) {

            // Caso 1: SuccessionAsUsage direto
            if (elem instanceof SuccessionAsUsage su) {

                for (Element tgt : su.getTarget()) {
                	if (nodeElement.getElementId() != null) {
                		if (nodeElement.getElementId().equals(tgt.getElementId())) {
                            incomingList.add(
                                AdapterUtils.setSuccession(
                                    su,
                                    "target",
                                    this
                                )
                            );
                        }
                	}
                }
                for (Element src : su.getSource()) {
                	if (nodeElement.getElementId() != null) {
                		if (nodeElement.getElementId().equals(src.getElementId())) {
                            outgoingList.add(
                                AdapterUtils.setSuccession(
                                    su,
                                    "source",
                                    this
                                )
                            );
                        }
                	}
                }
            }

            // Caso 2: SuccessionAsUsage dentro de TransitionUsage
            if (elem instanceof TransitionUsage tu) {
            	
                for (Element sub : tu.getOwnedMember()) {
                    if (!(sub instanceof SuccessionAsUsage su)) continue;

                    for (Element tgt : su.getTarget()) {
                    	if (nodeElement.getElementId() != null) {
                    		if (nodeElement.getElementId().equals(tgt.getElementId())) {
                                incomingList.add(
                                    AdapterUtils.setSuccession(
                                        su,
                                        "target",
                                        this
                                    )
                                );
                            }
                    	}
                    }
                    for (Element src : su.getSource()) {
                    	if (nodeElement.getElementId() != null) {
                    		if (nodeElement.getElementId().equals(src.getElementId())) {
                                outgoingList.add(
                                    AdapterUtils.setSuccession(
                                        su,
                                        "source",
                                        this
                                    )
                                );
                            }
                    	}
                    }
                }
            }
        }

        this.incomings = incomingList.toArray(new ISuccession[0]);
        this.outgoings = outgoingList.toArray(new ISuccession[0]);
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
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeAdapter that = (NodeAdapter) o;
        return this.getID().equals(that.getID());
	}

}