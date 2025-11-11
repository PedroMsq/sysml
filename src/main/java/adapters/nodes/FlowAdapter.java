package adapters.nodes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.Classifier;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FeatureChainExpression;
import org.omg.sysml.lang.sysml.FeatureReferenceExpression;
import org.omg.sysml.lang.sysml.FlowEnd;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.ItemUsage;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.Type;
import org.omg.sysml.lang.sysml.Redefinition;
import org.omg.sysml.lang.sysml.ReferenceSubsetting;
import org.omg.sysml.lang.sysml.Usage;

import adapters.utils.NamedElementAdapter;
import interfaces.nodes.IFlow;
import interfaces.structure.IItemUsage;
import interfaces.utils.INamedElement;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Expression;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.PayloadFeature;

public class FlowAdapter extends NamedElementAdapter implements IFlow {

	private final Element flowElement; // referência direta ao FlowUsage
	private IItemUsage source; // origem
	private IItemUsage target; // destino
	private INamedElement payload; // payload (se definido)

	public FlowAdapter(Element flowElement, Namespace containerNamespace) {
		super(flowElement);
		this.flowElement = flowElement;

		for (Element elem : containerNamespace.getOwnedMember()) {
			if (elem instanceof FlowUsage fu) {

				for (Feature fa : fu.getRelatedFeature()) {
					System.out.println("fu_name: " + fu.getName()); // flow1 ou flow2
				}

				for (Feature fb : fu.getOwnedFeature()) {
					if (fb instanceof PayloadFeature pf) {
						System.out.println("payload_feature: " + fb.getType().get(0).getName()); // Fuel
					}

					if (fb instanceof FlowEnd fe) {
						ReferenceSubsetting refsub = fe.getOwnedReferenceSubsetting();
						for (Feature fif : refsub.getReferencedFeature().getChainingFeature()) {
							System.out.println(fif.getName());
						}
						System.out.println(refsub.getReferencingFeature().getName()); // source ou target
						System.out.println(refsub.getOwningFeature().getName()); // source ou target
						for (Feature features : fe.getOwnedFeature()) {
							if (features instanceof Usage u) {
								System.out.println("usage_owner: " + u.getOwner().getName()); // source ou target
								System.out.println("usage: " + u.getName()); // fuelSupply ou fuelReturn
							}
							// System.out.println("fe_feature: " + features.getName()); fuelSupply ou fuelReturn
						}
					}

				}
			}
		}
	}

	@Override
	public IItemUsage getSource() {
		return source;
	}

	@Override
	public IItemUsage getTarget() {
		return target;
	}

	@Override
	public INamedElement getPayload() {
		return payload;
	}

	@Override
	public String getName() {
		return flowElement.getName();
	}

	@Override
	public String getDeclaredName() {
		return flowElement.getDeclaredName();
	}

}
