package adapters.nodes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.Classifier;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FeatureReferenceExpression;
import org.omg.sysml.lang.sysml.FlowEnd;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.ItemUsage;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.Type;
import org.omg.sysml.lang.sysml.Redefinition;
import org.omg.sysml.lang.sysml.ReferenceSubsetting;
import org.omg.sysml.lang.sysml.Usage;

import interfaces.nodes.IFlow;
import interfaces.structure.IItemUsage;
import interfaces.utils.INamedElement;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Expression;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.PayloadFeature;

public class FlowAdapter implements IFlow {

    private final Element flowElement;   // referência direta ao FlowUsage
    private IItemUsage source;     // origem
    private IItemUsage target;     // destino
    private INamedElement payload; // payload (se definido)

    public FlowAdapter(Element flowElement, Namespace containerNamespace) {
        this.flowElement = flowElement;

        for (Element elem : containerNamespace.getOwnedMember()) {
        	if (elem instanceof FlowUsage fu) {
        		
        		for (Feature fa : fu.getRelatedFeature()) {
        			System.out.println("fu_name: " + fu.getName());
        		}
        		
        		for (Feature fb : fu.getOwnedFeature()) {
        			if (fb instanceof PayloadFeature pf) {
        				for (Feature fc : pf.getFeature()) {
        					System.out.println("payload_feature: " + fc.getName());
        				}
        			}
        			if (fb instanceof FlowEnd fe) {
        				
        				for(Feature features : fe.getOwnedFeature()) {
        					if (features instanceof Usage u) {
        						System.out.println("usage_owner: " + u.getOwner().getName());
        						System.out.println("usage: " + u.getName());
        					}
        					System.out.println("fe_feature: " + features.getName());
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
