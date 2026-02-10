package br.ufrpe.dc.sysml.gamine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.Namespace;

import br.ufrpe.dc.sysml.SysMLV2Spec;
import gamine.SysMLV2ActionSemantics;
import obp3.Sequencer;
import obp3.sli.core.operators.SemanticRelation2RootedGraph;
import obp3.sli.core.operators.ToDetermistic;
import obp3.traversal.dfs.DepthFirstTraversal;

class SysMLV2SemanticsTest {

	private static SysMLV2Spec spec;
	private static Namespace rootNamespace;

	@BeforeAll
	static void init() {

	}

	@Test
	void test() {
		spec = new SysMLV2Spec();
		spec.parseFile("behavior/SimpleSuccession.sysml");
		
		System.out.println("SimpleSuccession.sysml");
		rootNamespace = (Namespace) spec.getRootNamespace();
		org.omg.sysml.lang.sysml.Element first = rootNamespace.getOwnedMember().getFirst();

		ActionDefinition actDef = (ActionDefinition) first.getOwnedElement().getFirst();

		SysMLV2ActionSemantics semantics = new SysMLV2ActionSemantics(actDef);

		var rootedGraph = new SemanticRelation2RootedGraph<>(semantics);
		var dfs = new DepthFirstTraversal<>(rootedGraph);

		var result = dfs.runAlone();
		System.out.println(result);

//		var dfs1 = new DepthFirstTraversal<>(rootedGraph);
//
//		var result1 = dfs1.runAlone();
//		System.out.println(result1);
	}
	
	@Test
	void test2() {
		spec = new SysMLV2Spec();
		spec.parseFile("behavior/SimpleForkSuccession.sysml");
		rootNamespace = (Namespace) spec.getRootNamespace();
		System.out.println("SimpleForkSuccession.sysml");
		org.omg.sysml.lang.sysml.Element first = rootNamespace.getOwnedMember().getFirst();

		ActionDefinition actDef = (ActionDefinition) first.getOwnedElement().getFirst();

		SysMLV2ActionSemantics semantics = new SysMLV2ActionSemantics(actDef);

		var rootedGraph = new SemanticRelation2RootedGraph<>(semantics);
		var dfs = new DepthFirstTraversal<>(rootedGraph);

		var result = dfs.runAlone();
		System.out.println(result);

//		var dfs1 = new DepthFirstTraversal<>(rootedGraph);
//
//		var result1 = dfs1.runAlone();
//		System.out.println(result1);
	}
	
	@Test
	void test3() {
		spec = new SysMLV2Spec();
		spec.parseFile("behavior/SimpleForkSuccession.sysml");
		rootNamespace = (Namespace) spec.getRootNamespace();
		System.out.println("SimpleForkSuccession.sysml");
		org.omg.sysml.lang.sysml.Element first = rootNamespace.getOwnedMember().getFirst();

		ActionDefinition actDef = (ActionDefinition) first.getOwnedElement().getFirst();

		SysMLV2ActionSemantics semantics = new SysMLV2ActionSemantics(actDef);

		var deterministic = ToDetermistic.randomPolicy(semantics, System.nanoTime());
        var sequencer = new Sequencer<>(deterministic);

        int[] count = new int[]{10};

        var result = sequencer.runAlone();

//		var dfs1 = new DepthFirstTraversal<>(rootedGraph);
//
//		var result1 = dfs1.runAlone();
//		System.out.println(result1);
	}

}
