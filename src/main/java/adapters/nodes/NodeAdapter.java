package adapters.nodes;

import java.util.ArrayList;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.TransitionUsage;

import adapters.utils.AdapterUtils;
import adapters.utils.NamedElementAdapter;
import interfaces.actions.ISuccession;
import interfaces.nodes.INode;

public class NodeAdapter extends NamedElementAdapter implements INode {

    protected final Element nodeElement; // the node itself
    private final ISuccession[] incomings;
    private final ISuccession[] outgoings;

    public NodeAdapter(Element nodeElement) {
        super(nodeElement);
        Namespace containerNamespace = (Namespace) nodeElement.getOwner();
        this.nodeElement = nodeElement;

        ArrayList<ISuccession> incomingList = new ArrayList<>();
        ArrayList<ISuccession> outgoingList = new ArrayList<>();

        for (Element elem : containerNamespace.getOwnedMember()) {

            // Case 1: direct SuccessionAsUsage
            if (elem instanceof SuccessionAsUsage su) {

                for (Element tgt : su.getTarget()) {
                    if (nodeElement.getDeclaredName().equals(tgt.getDeclaredName())) {
                        incomingList.add(
                            AdapterUtils.setSuccession(
                                su,
                                "target",
                                this
                            )
                        );
                    }
                }
                for (Element src : su.getSource()) {
                    if (nodeElement.getDeclaredName().equals(src.getDeclaredName())) {
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

            // Caso 2: SuccessionAsUsage dentro de TransitionUsage
            if (elem instanceof TransitionUsage tu) {
            	
                for (Element sub : tu.getOwnedMember()) {
                    if (!(sub instanceof SuccessionAsUsage su)) continue;

                    for (Element tgt : su.getTarget()) {
                        if (nodeElement.getDeclaredName().equals(tgt.getDeclaredName())) {
                            incomingList.add(
                                AdapterUtils.setSuccession(
                                    su,
                                    "target",
                                    this
                                )
                            );
                        }
                    }
                    for (Element src : su.getSource()) {
                        if (nodeElement.getDeclaredName().equals(src.getDeclaredName())) {
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

}