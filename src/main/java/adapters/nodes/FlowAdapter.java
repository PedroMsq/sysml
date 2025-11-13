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
				System.out.print("flow " + (fu.getName() != null ? fu.getName() : "<unnamed>"));

				for (Feature fb : fu.getOwnedFeature()) {
					if (fb instanceof PayloadFeature pf) { // se não especificado, não detecta uma instância
						Element payloadf = (fb.getType().get(0)); // of (Fuel)
						NamedElementAdapter namedelementpayload = new NamedElementAdapter(payloadf);
						this.payload = namedelementpayload;
						System.out.print("of " + payload.getName());
					}

					if (fb instanceof FlowEnd fe) {
						ReferenceSubsetting refsub = fe.getOwnedReferenceSubsetting();
						if (refsub.getReferencingFeature().getName().equals("source")) { // (from | to)
							System.out.print("\nfrom ");
						} else {
							System.out.print("to ");
						};
						// duas possibilidades: ReferencedFeature ou ChainingFeature
						// detecta uma ActionUsage com declaredName (mas sem getName)
						if (refsub.getReferencedFeature().getDeclaredName() != null) { 
							System.out.print(refsub.getReferencedFeature().getDeclaredName() + ".");
						}
						// detecta uma ChainingFeature
						for (Feature fif : refsub.getReferencedFeature().getChainingFeature()) { // não detecta uma instância
							System.out.print(fif.getName() + "."); // (tankAssy.fuelTankPort. | eng.engineFuelPort.)
						}
						for (Feature features : fe.getOwnedFeature()) {
							if (features instanceof Usage u) {
								System.out.println(u.getName()); // (fuelSupply | fuelReturn)
							}
						}
					}
				}
				System.out.println(); // separar flows
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
